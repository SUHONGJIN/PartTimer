package com.mfzj.parttimer.view.activity.postjob;

import android.Manifest;
import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.longsh.optionframelibrary.OptionBottomDialog;
import com.longsh.optionframelibrary.OptionCenterDialog;
import com.mfzj.parttimer.CitySelect.CityPickerActivity;
import com.mfzj.parttimer.R;
import com.mfzj.parttimer.base.BaseActivity;
import com.mfzj.parttimer.bean.JobSelection;
import com.mfzj.parttimer.bean.User;
import com.mfzj.parttimer.utils.AppConfig;
import com.mfzj.parttimer.utils.ToastUtils;
import com.mfzj.parttimer.view.activity.AuthenticationActivity;
import com.mfzj.parttimer.widget.WeiboDialogUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;

public class AddPartTimerActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.et_post_title)
    EditText et_post_title;
    @BindView(R.id.et_post_people)
    EditText et_post_people;
    @BindView(R.id.et_post_pay)
    EditText et_post_pay;
    @BindView(R.id.et_post_describe)
    EditText et_post_describe;
    @BindView(R.id.et_post_company)
    EditText et_post_company;
    @BindView(R.id.tv_post_type)
    TextView tv_post_type;
    @BindView(R.id.tv_post_time1)
    TextView tv_post_time1;
    @BindView(R.id.tv_post_time2)
    TextView tv_post_time2;
    @BindView(R.id.tv_post_city)
    TextView tv_post_city;
    @BindView(R.id.tv_post_pay_type)
    TextView tv_post_pay_type;
    @BindView(R.id.et_post_address)
    EditText et_post_address;
    @BindView(R.id.btn_post_job)
    Button btn_post_job;
    @BindView(R.id.iv_boss_pic)
    ImageView iv_boss_pic;

    private final int TIME_START = 0;
    private final int TIME_END = 1;
    public static final int REQUEST_INFO = 1;
    public static final int CAMERA_PERMISSION_CODE = 2;
    public static final int REQUEST_PERMISSION_CAMERA = 3;
    public static final int CROP_REQUEST_CODE = 4;
    public static final int REQUEST_PERMISSION_WRITE = 5;
    private String BOSS_IMAGE_URL = "";
    /**
     * 文件相关
     */
    private File captureFile;
    private File rootFile;
    private File cropFile;

    private Dialog mWeiboDialog;

    @Override
    public int getContentViewResId() {
        return R.layout.activity_add_part_timer;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        tv_title.setText("发布兼职");
        rootFile = new File(AppConfig.PIC_PATH);
        if (!rootFile.exists()) {
            rootFile.mkdirs();
        }
    }

    @Override
    public void initData() {
    }

    @OnClick({R.id.btn_post_job, R.id.iv_back, R.id.tv_post_pay_type, R.id.tv_post_type, R.id.tv_post_time1, R.id.tv_post_time2, R.id.tv_post_city, R.id.iv_boss_pic})
    public void Onclick(View view) {
        switch (view.getId()) {
            case R.id.btn_post_job:
                if (et_post_title.getText().length() == 0) {
                    ToastUtils.setOkToast(AddPartTimerActivity.this, "请填写兼职标题");
                } else if (et_post_people.getText().length() == 0) {
                    ToastUtils.setOkToast(AddPartTimerActivity.this, "请填写兼职招聘人数");
                } else if (et_post_pay.getText().length() == 0) {
                    ToastUtils.setOkToast(AddPartTimerActivity.this, "请填写兼职薪资");
                } else if (et_post_describe.getText().length() == 0) {
                    ToastUtils.setOkToast(AddPartTimerActivity.this, "请填写兼职描述");
                } else if (et_post_address.getText().length() == 0) {
                    ToastUtils.setOkToast(AddPartTimerActivity.this, "请填写详细的地址");
                } else if (et_post_company.getText().length() == 0) {
                    ToastUtils.setOkToast(AddPartTimerActivity.this, "请填写雇主名称");
                } else if (BOSS_IMAGE_URL.isEmpty()) {
                    ToastUtils.setOkToast(AddPartTimerActivity.this, "请选择雇主的商标");
                } else {
                    savePost();
                }
                break;
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_post_pay_type:
                setPayType();
                break;
            case R.id.tv_post_type:
                setType();
                break;
            case R.id.tv_post_time1:
                setTime(TIME_START);
                break;
            case R.id.tv_post_time2:
                setTime(TIME_END);
                break;
            case R.id.tv_post_city:
                Intent intent = new Intent(AddPartTimerActivity.this, CityPickerActivity.class);
                startActivityForResult(intent, 1);
                break;
            case R.id.iv_boss_pic:
                selectPic();
                break;

            default:
                break;
        }
    }

    /**
     * 设置工资的形式
     */
    private void setPayType() {
        final ArrayList<String> list = new ArrayList<>();
        list.add("小时");
        list.add("天");
        list.add("周");
        list.add("月");
        final OptionCenterDialog optionCenterDialog = new OptionCenterDialog();
        optionCenterDialog.show(AddPartTimerActivity.this, list);
        optionCenterDialog.setItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        tv_post_pay_type.setText("小时");
                        break;
                    case 1:
                        tv_post_pay_type.setText("天");
                        break;
                    case 2:
                        tv_post_pay_type.setText("周");
                        break;
                    case 3:
                        tv_post_pay_type.setText("月");
                        break;
                    default:
                        break;
                }
                optionCenterDialog.dismiss();
            }
        });
    }

    /**
     * 工资结算类型
     */
    private void setType() {
        final ArrayList<String> list = new ArrayList<>();
        list.add("日结");
        list.add("周结");
        list.add("月结");
        final OptionCenterDialog optionCenterDialog = new OptionCenterDialog();
        optionCenterDialog.show(AddPartTimerActivity.this, list);
        optionCenterDialog.setItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        tv_post_type.setText("日结");
                        break;
                    case 1:
                        tv_post_type.setText("周结");
                        break;
                    case 2:
                        tv_post_type.setText("月结");
                        break;
                    default:
                        break;
                }
                optionCenterDialog.dismiss();
            }
        });
    }

    /**
     * 工作开始时间和结束时间
     *
     * @param tag
     */
    protected void setTime(final int tag) {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(AddPartTimerActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                if (tag == TIME_START) {
                    tv_post_time1.setText(year + "年" + (monthOfYear + 1) + "月" + dayOfMonth + "日");
                }
                if (tag == TIME_END) {
                    tv_post_time2.setText(year + "年" + (monthOfYear + 1) + "月" + dayOfMonth + "日");
                }

            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    /**
     * 添当前用户发布兼职
     */
    private void savePost() {
        if (BmobUser.isLogin()) {
            String time1 = tv_post_time1.getText().toString();
            String time2 = tv_post_time2.getText().toString();
            String city = tv_post_city.getText().toString();
            String address = et_post_address.getText().toString();
            String pay = et_post_pay.getText().toString();
            String pay_type = tv_post_pay_type.getText().toString();

            JobSelection post = new JobSelection();
            post.setJob_title(et_post_title.getText().toString());
            post.setJob_people(et_post_people.getText().toString());
            post.setJob_pay(pay + "元/" + pay_type);
            post.setJob_type(tv_post_type.getText().toString());
            post.setJob_describe(et_post_describe.getText().toString());
            post.setJob_time(time1 + " - " + time2);
            post.setJob_address(city + address);
            post.setJob_company(et_post_company.getText().toString());
            post.setJob_logo(BOSS_IMAGE_URL);

            //添加一对一关联，用户关联帖子
            post.setBoss(BmobUser.getCurrentUser(User.class));
            post.save(new SaveListener<String>() {
                @Override
                public void done(String s, BmobException e) {
                    if (e == null) {
                        ToastUtils.setOkToast(AddPartTimerActivity.this, "发布成功！");
                        setResult(200);
                        finish();
                    } else {
                        ToastUtils.setOkToast(AddPartTimerActivity.this, "发布失败！");
                    }
                }
            });
        } else {
            ToastUtils.setOkToast(AddPartTimerActivity.this, "请先登录！");
        }
    }

    private void selectPic() {
        List<String> stringList = new ArrayList<String>();
        stringList.add("拍照");
        stringList.add("从相册选择");
        final OptionBottomDialog optionBottomDialog = new OptionBottomDialog(AddPartTimerActivity.this, stringList);
        optionBottomDialog.setItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    //Android6.0以上要获取动态权限
                    //先判断该页面是否已经授予拍照权限
                    if (ContextCompat.checkSelfPermission(AddPartTimerActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        //获取拍照权限
                        ActivityCompat.requestPermissions(AddPartTimerActivity.this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE);
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
            Uri contentUri = FileProvider.getUriForFile(AddPartTimerActivity.this, "com.mfzj.parttimer.fileprovider", captureFile);
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
                    BOSS_IMAGE_URL = bmobFile.getFileUrl();
                    Glide.with(AddPartTimerActivity.this).load(bmobFile.getFileUrl()).into(iv_boss_pic);
                    //关闭加载框
                    WeiboDialogUtils.closeDialog(mWeiboDialog);

                    Log.i("tag22", bmobFile.getFileUrl());
                } else {
                    ToastUtils.setOkToast(AddPartTimerActivity.this, "上传文件失败：" + e.getMessage());
                    WeiboDialogUtils.closeDialog(mWeiboDialog);
                }
            }

            @Override
            public void onProgress(Integer value) {
                super.onProgress(value);
                //mProgressBar.setProgress(value);
                Log.i("tag20", value + "===============");

            }

            @Override
            public void onStart() {
                super.onStart();
                mWeiboDialog = WeiboDialogUtils.createLoadingDialog(AddPartTimerActivity.this, "努力上传中...");
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
        cropFile = new File(rootFile, "logo.jpg");
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == 200) {
                if (data == null) {
                    return;
                }
                tv_post_city.setText(data.getStringExtra("cityname"));
            }
        }
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_PERMISSION_CAMERA:
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        Uri contentUri = FileProvider.getUriForFile(AddPartTimerActivity.this, "com.mfzj.parttimer.fileprovider", captureFile);
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
    }


}
