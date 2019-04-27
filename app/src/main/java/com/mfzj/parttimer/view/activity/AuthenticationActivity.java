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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
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

public class AuthenticationActivity extends BaseActivity {
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.et_au_name)
    EditText et_au_name;
    @BindView(R.id.et_id_number)
    EditText et_id_number;
    @BindView(R.id.iv_id_card_z)
    ImageView iv_id_card_z;
    @BindView(R.id.iv_id_card_f)
    ImageView iv_id_card_f;
    @BindView(R.id.btn_commit_audit)
    Button btn_commit_audit;
    @BindView(R.id.mProgressBar)
    ProgressBar mProgressBar;
    @BindView(R.id.ll_verify_init)
    LinearLayout ll_verify_init;
    @BindView(R.id.ll_verify_now)
    LinearLayout ll_verify_now;
    public static final int REQUEST_INFO = 1;
    public static final int CAMERA_PERMISSION_CODE = 2;
    public static final int REQUEST_PERMISSION_CAMERA = 3;
    public static final int CROP_REQUEST_CODE = 4;
    public static final int REQUEST_PERMISSION_WRITE = 5;
    int ID_CARD_LEFT = 1;
    int ID_CARD_RIGHT = 2;
    int IdCard = 0;

    /**
     * 文件相关
     */
    private File captureFile;
    private File rootFile;
    private File cropFile;

    private Dialog mWeiboDialog;

    private String ID_CARD_LEFT_URL = "";
    private String ID_CARD_RIGTH_URL = "";

    @Override
    public int getContentViewResId() {
        return R.layout.activity_authentication;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        tv_title.setText("实名认证");
        rootFile = new File(AppConfig.PIC_PATH);
        if (!rootFile.exists()) {
            rootFile.mkdirs();
        }
    }

    @Override
    public void initData() {
        User user = BmobUser.getCurrentUser(User.class);
        String verify=user.getIsverify();
        if (verify!=null){
            if (verify.equals("认证审核中")){
                ll_verify_init.setVisibility(View.GONE);
                ll_verify_now.setVisibility(View.VISIBLE);
            }
        }

    }

    @OnClick({R.id.iv_id_card_z, R.id.iv_id_card_f, R.id.btn_commit_audit, R.id.iv_back})
    public void OnClickAut(View view) {
        switch (view.getId()) {
            case R.id.iv_id_card_z:
                selectPic();
                IdCard = ID_CARD_LEFT;
                break;
            case R.id.iv_id_card_f:
                selectPic();
                IdCard = ID_CARD_RIGHT;
                break;
            case R.id.btn_commit_audit:
                if (et_au_name.getText().length()==0) {
                    ToastUtils.setOkToast(AuthenticationActivity.this, "请填写真实姓名");
                } else if (et_id_number.getText().length()==0) {
                    ToastUtils.setOkToast(AuthenticationActivity.this, "请填写身份证号码");
                }else if (et_id_number.getText().length()!=15 && et_id_number.getText().length()!=18) {
                    ToastUtils.setOkToast(AuthenticationActivity.this, "身份证号码为15/18位");
                } else if (ID_CARD_LEFT_URL.isEmpty()) {
                    ToastUtils.setOkToast(AuthenticationActivity.this, "请上传身份证正面照");
                } else if (ID_CARD_LEFT_URL.isEmpty()) {
                    ToastUtils.setOkToast(AuthenticationActivity.this, "请上传身份证反面照");
                } else {
                    commitAudit();
                }
                break;
            case R.id.iv_back:
                finish();
                break;
            default:
                break;
        }
    }

    private void selectPic() {
        List<String> stringList = new ArrayList<String>();
        stringList.add("拍照");
        stringList.add("从相册选择");
        final OptionBottomDialog optionBottomDialog = new OptionBottomDialog(AuthenticationActivity.this, stringList);
        optionBottomDialog.setItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    //Android6.0以上要获取动态权限
                    //先判断该页面是否已经授予拍照权限
                    if (ContextCompat.checkSelfPermission(AuthenticationActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        //获取拍照权限
                        ActivityCompat.requestPermissions(AuthenticationActivity.this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE);
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
            Uri contentUri = FileProvider.getUriForFile(AuthenticationActivity.this, "com.mfzj.parttimer.fileprovider", captureFile);
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
     * 提交认证审核
     */
    private void commitAudit() {
        BmobUser myUser = BmobUser.getCurrentUser(User.class);
        myUser.setValue("id_card_name", et_au_name.getText().toString());
        myUser.setValue("id_card_number", et_id_number.getText().toString());
        myUser.setValue("id_card_pic_z", ID_CARD_LEFT_URL);
        myUser.setValue("id_card_pic_f", ID_CARD_RIGTH_URL);
        myUser.setValue("isverify", "认证审核中");
        myUser.update(myUser.getObjectId(), new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    ToastUtils.setOkToast(AuthenticationActivity.this, "提交成功！");
                    ll_verify_now.setVisibility(View.VISIBLE);
                    ll_verify_init.setVisibility(View.GONE);
                } else {
                    ToastUtils.setOkToast(AuthenticationActivity.this, "提交失败，请检查格式是否有误！");
                }
            }
        });
    }

    /**
     * 上传图片到bmob后端云
     *
     * @param picPath
     */
    private void upload(String picPath) {

        final BmobFile bmobFile = new BmobFile(new File(picPath));

        bmobFile.uploadblock(new UploadFileListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {

                    //bmobFile.getFileUrl()--返回的上传文件的完整地址

                    if (IdCard == ID_CARD_LEFT) {
                        Glide.with(AuthenticationActivity.this).load(bmobFile.getFileUrl()).into(iv_id_card_z);
                        ID_CARD_LEFT_URL = bmobFile.getFileUrl();
                    } else if (IdCard == ID_CARD_RIGHT) {
                        Glide.with(AuthenticationActivity.this).load(bmobFile.getFileUrl()).into(iv_id_card_f);
                        ID_CARD_RIGTH_URL = bmobFile.getFileUrl();
                    }
                    //关闭加载框
                    WeiboDialogUtils.closeDialog(mWeiboDialog);

                    Log.i("tag22", bmobFile.getFileUrl());
                } else {
                    ToastUtils.setOkToast(AuthenticationActivity.this, "上传文件失败：" + e.getMessage());
                    WeiboDialogUtils.closeDialog(mWeiboDialog);
                }
            }

            @Override
            public void onProgress(Integer value) {
                super.onProgress(value);
                mProgressBar.setProgress(value);
                Log.i("tag20", value + "===============");

            }

            @Override
            public void onStart() {
                super.onStart();
                mWeiboDialog = WeiboDialogUtils.createLoadingDialog(AuthenticationActivity.this, "努力上传中...");
                Log.i("tag20", "开始" + "===============");
            }

            @Override
            public void onFinish() {
                super.onFinish();
                Log.i("tag20", "结束" + "===============");
            }
        });
    }

    //处理权限请求响应
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case CAMERA_PERMISSION_CODE:
                takePhoto();
                //ToastUtils.setOkToast(AuthenticationActivity.this, "相机权限已申请");
                break;
        }
    }

    /**
     * 裁剪图片
     */
    private void cropPhoto(Uri uri) {
        cropFile = new File(rootFile, "idcard.jpg");
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 3);
        intent.putExtra("aspectY", 2);
        intent.putExtra("outputX", 450);
        intent.putExtra("outputY", 300);
        intent.putExtra("return-data", false);//注意这里返回false,因为在部分手机上获取不到返回的数据
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(cropFile));
        intent.putExtra("outputFormat", Bitmap.CompressFormat.PNG.toString());
        intent.putExtra("noFaceDetection", true);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivityForResult(intent, CROP_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_INFO:
                if (resultCode == 200) {
                    //刷新数据
                    //showUserInfo();
                }
                break;
            default:
                break;
        }
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_PERMISSION_CAMERA:
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        Uri contentUri = FileProvider.getUriForFile(AuthenticationActivity.this, "com.mfzj.parttimer.fileprovider", captureFile);
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
