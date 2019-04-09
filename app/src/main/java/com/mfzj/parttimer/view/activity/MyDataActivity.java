package com.mfzj.parttimer.view.activity;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.longsh.optionframelibrary.OptionBottomDialog;
import com.mfzj.parttimer.R;
import com.mfzj.parttimer.base.BaseActivity;
import com.mfzj.parttimer.bean.User;
import com.mfzj.parttimer.utils.AppConfig;
import com.mfzj.parttimer.utils.ToastUtils;
import com.mfzj.parttimer.widget.WeiboDialogUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;
import de.hdodenhof.circleimageview.CircleImageView;

public class MyDataActivity extends BaseActivity {
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.civ_user_head)
    CircleImageView civ_user_head;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tv_username)
    TextView tv_username;
    @BindView(R.id.tv_nick)
    TextView tv_nick;
    @BindView(R.id.tv_motto)
    TextView tv_motto;
    @BindView(R.id.rl_modify_user_head)
    RelativeLayout rl_modify_user_head;
    @BindView(R.id.rl_id)
    RelativeLayout rl_id;
    @BindView(R.id.rl_modify_user_nick)
    RelativeLayout rl_modify_user_nick;
    @BindView(R.id.rl_modify_user_motto)
    RelativeLayout rl_modify_user_motto;
    @BindView(R.id.rl_to_mydata)
    RelativeLayout rl_to_mydata;


    public static final int REQUEST_INFO = 4;
    public static final int CAMERA_PERMISSION_CODE = 5;
    public static final int REQUEST_PERMISSION_CAMERA = 6;
    public static final int CROP_REQUEST_CODE = 7;
    public static final int REQUEST_PERMISSION_WRITE = 8;

    /**
     * 文件相关
     */
    private File captureFile;
    private File rootFile;
    private File cropFile;

    private Dialog mWeiboDialog;

    @Override
    public int getContentViewResId() {
        return R.layout.activity_my_data;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        tv_title.setText("我的资料");
        showUserAvatar();
        rootFile = new File(AppConfig.PIC_PATH);
        if (!rootFile.exists()) {
            rootFile.mkdirs();
        }
    }

    @Override
    public void initData() {
        showUserInfo();
    }

    @OnClick({R.id.rl_modify_user_head, R.id.rl_id, R.id.rl_modify_user_nick, R.id.rl_modify_user_motto, R.id.rl_to_mydata, R.id.iv_back})
    public void OnClickDataItemView(View view) {
        switch (view.getId()) {
            case R.id.rl_modify_user_head:
                List<String> stringList = new ArrayList<String>();
                stringList.add("拍照");
                stringList.add("从相册选择");
                final OptionBottomDialog optionBottomDialog = new OptionBottomDialog(MyDataActivity.this, stringList);
                optionBottomDialog.setItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        if (position == 0) {
                            //Android6.0以上要获取动态权限
                            //先判断该页面是否已经授予拍照权限
                            if (ContextCompat.checkSelfPermission(MyDataActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                                //获取拍照权限
                                ActivityCompat.requestPermissions(MyDataActivity.this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE);
                            } else {
                                //拍照
                                takePhoto();
                            }
                        }
                        if (position == 1) {
                            //调用相册
                            choosePhoto();
                        }
                        optionBottomDialog.dismiss();
                    }
                });
                break;
            case R.id.rl_id:
                ToastUtils.setOkToast(MyDataActivity.this, "用户ID不支持修改(＾Ｕ＾)");
                break;
            case R.id.rl_modify_user_nick:
                Intent intent = new Intent(MyDataActivity.this, ModifyUserDataActivity.class);
                intent.putExtra("tag", "nick");
                startActivityForResult(intent, REQUEST_INFO);
                break;
            case R.id.rl_modify_user_motto:
                Intent intent1 = new Intent(MyDataActivity.this, ModifyUserDataActivity.class);
                intent1.putExtra("tag", "motto");
                startActivityForResult(intent1, REQUEST_INFO);
                break;
            case R.id.rl_to_mydata:
                startActivity(new Intent(MyDataActivity.this, MyResumeActivity.class));
                break;
            case R.id.iv_back:
                finish();
                break;
            default:
                break;
        }
    }

    //拍照
    private void takePhoto() {
        //用于保存调用相机拍照后所生成的文件
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return;
        }
        captureFile = new File(rootFile, "temp.jpg");
        //跳转到调用系统相机
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //判断版本 如果在Android7.0以上,使用FileProvider获取Uri
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(MyDataActivity.this, "com.mfzj.parttimer.fileprovider", captureFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, contentUri);
        } else {
            //否则使用Uri.fromFile(file)方法获取Uri
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(captureFile));
        }
        startActivityForResult(intent, REQUEST_PERMISSION_CAMERA);
    }

    //从相册选择
    private void choosePhoto() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, REQUEST_PERMISSION_WRITE);
    }

    /**
     * 裁剪图片
     */
    private void cropPhoto(Uri uri) {
        cropFile = new File(rootFile, "avatar.jpg");
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
        intent.putExtra("return-data", false);//注意这里返回false,因为在部分手机上获取不到返回的数据
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(cropFile));
        intent.putExtra("outputFormat", Bitmap.CompressFormat.PNG.toString());
        intent.putExtra("noFaceDetection", true);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivityForResult(intent, CROP_REQUEST_CODE);
    }

    /**
     * 显示用户资料
     */
    private void showUserInfo() {
        User user = BmobUser.getCurrentUser(User.class);
        if (user != null) {
            //获取用ID
            if (user.getUsername() != null) {
                tv_username.setText(user.getUsername());
            }
            //获取用昵称
            if (user.getNick() != null) {
                tv_nick.setText(user.getNick());
            }
            //获取用个性签名
            if (user.getMotto() != null) {
                tv_motto.setText(user.getMotto());
            }
        }
    }

    /**
     * 上传头像到bmob后端云
     *
     * @param picPath
     */
    private void upload(String picPath) {

        final BmobFile bmobFile = new BmobFile(new File(picPath));

        bmobFile.uploadblock(new UploadFileListener() {

            @Override
            public void done(BmobException e) {
                if (e == null) {
                    //加载框
                    mWeiboDialog = WeiboDialogUtils.createLoadingDialog(MyDataActivity.this, "头像上传中...");
                    //bmobFile.getFileUrl()--返回的上传文件的完整地址
                    setAvatar(bmobFile.getFileUrl());
                } else {
                    ToastUtils.setOkToast(MyDataActivity.this, "上传文件失败：" + e.getMessage());
                }

            }

            @Override
            public void onProgress(Integer value) {
                // 返回的上传进度（百分比）
            }

        });
    }

    /**
     * 设置头像
     *
     * @param url
     */
    private void setAvatar(String url) {

        BmobUser myUser = BmobUser.getCurrentUser(User.class);
        myUser.setValue("avatar", url);
        myUser.update(myUser.getObjectId(), new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    //刷新头像
                    showUserAvatar();
                    //关闭加载框
                    WeiboDialogUtils.closeDialog(mWeiboDialog);
                    ToastUtils.setOkToast(MyDataActivity.this, "更新头像成功！");
                } else {
                    //关闭加载框
                    WeiboDialogUtils.closeDialog(mWeiboDialog);
                    ToastUtils.setOkToast(MyDataActivity.this, "更新头像失败！");
                    Log.i("tag12",e.getMessage());
                }
            }
        });
    }

    /**
     * 显示用户头像
     */
    private void showUserAvatar() {
        //获取头像地址
        User user = BmobUser.getCurrentUser(User.class);
        if (user.getAvatar() != null) {
            Glide.with(MyDataActivity.this)
                    .load(user.getAvatar())
                    .into(civ_user_head);
        }
    }

    //处理权限请求响应
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case CAMERA_PERMISSION_CODE:
                takePhoto();
                ToastUtils.setOkToast(MyDataActivity.this, "相机权限已申请");
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_INFO:
                if (resultCode == 200) {
                    //刷新数据
                    showUserInfo();
                }
                break;
            default:break;
        }
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_PERMISSION_CAMERA:
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        Uri contentUri = FileProvider.getUriForFile(MyDataActivity.this, "com.mfzj.parttimer.fileprovider", captureFile);
                        cropPhoto(contentUri);
                    } else {
                        cropPhoto(Uri.fromFile(captureFile));
                    }
                    break;
                case REQUEST_PERMISSION_WRITE:
                    cropPhoto(data.getData());
                    break;
                case CROP_REQUEST_CODE:
                    Log.i("tag2", cropFile.getAbsolutePath());
                    upload(cropFile.getAbsolutePath());
                    break;
                default:
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}
