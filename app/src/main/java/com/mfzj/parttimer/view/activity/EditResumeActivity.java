package com.mfzj.parttimer.view.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import com.longsh.optionframelibrary.OptionCenterDialog;
import com.mfzj.parttimer.CitySelect.CityPickerActivity;
import com.mfzj.parttimer.R;
import com.mfzj.parttimer.base.BaseActivity;
import com.mfzj.parttimer.bean.User;
import com.mfzj.parttimer.utils.ToastUtils;

import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

public class EditResumeActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.et_name)
    EditText et_name;
    @BindView(R.id.et_phone)
    EditText et_phone;
    @BindView(R.id.et_email)
    EditText et_email;
    @BindView(R.id.et_intro)
    EditText et_intro;
    @BindView(R.id.et_experience)
    EditText et_experience;
    @BindView(R.id.rb_men)
    RadioButton rb_men;
    @BindView(R.id.rb_women)
    RadioButton rb_women;
    @BindView(R.id.tv_show_identity)
    TextView tv_show_identity;
    @BindView(R.id.tv_show_birth)
    TextView tv_show_birth;
    @BindView(R.id.tv_show_sex)
    TextView tv_show_sex;
    @BindView(R.id.tv_show_city)
    TextView tv_show_city;
    @BindView(R.id.btn_commit)
    Button btn_commit;

    @Override
    public int getContentViewResId() {
        return R.layout.activity_edit_resume;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        tv_title.setText("编辑简历");
    }

    @Override
    public void initData() {
        showInfo();
    }

    /**
     * 显示用户信息
     */
    private void showInfo() {
        User user = BmobUser.getCurrentUser(User.class);
        if (user.getName()!=null){
            et_name.setText(user.getName());
        }
        if (user.getGender()!=null){
            tv_show_sex.setText(user.getGender());
            if (user.getGender().equals("男")){
                rb_men.setChecked(true);
            }else if (user.getGender().equals("女")){
                rb_women.setChecked(true);
            }
        }
        if (user.getBirth()!=null){
            tv_show_birth.setText(user.getBirth());
        }
        if (user.getIdentity()!=null){
            tv_show_identity.setText(user.getIdentity());
        }
        if (user.getCity()!=null){
            tv_show_city.setText(user.getCity());
        }
        if (user.getPhone()!=null){
            et_phone.setText(user.getPhone());
        }
        if (user.getMyemail()!=null){
            et_email.setText(user.getMyemail());
        }
        if (user.getIntro()!=null){
            et_intro.setText(user.getIntro());
        }
        if (user.getExperience()!=null){
            et_experience.setText(user.getExperience());
        }
    }

    @OnClick({R.id.iv_back,R.id.tv_show_identity,R.id.tv_show_birth,R.id.btn_commit,R.id.tv_show_city})
    public void OnClickE(View view){
        switch (view.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_show_identity:
                setIdentity();
                break;
            case R.id.tv_show_birth:
                showDatePickDlg();
                break;
            case R.id.btn_commit:
                if (et_name.getText().length()==0){
                    ToastUtils.setOkToast(EditResumeActivity.this,"请填写姓名");
                }else if (et_phone.getText().length()==0){
                    ToastUtils.setOkToast(EditResumeActivity.this,"请填写联系电话");
                }else if (et_email.getText().length()==0){
                    ToastUtils.setOkToast(EditResumeActivity.this,"请填写邮箱");
                }else if (et_intro.getText().length()==0){
                    ToastUtils.setOkToast(EditResumeActivity.this,"请填写简介");
                }else if (et_experience.getText().length()==0){
                    ToastUtils.setOkToast(EditResumeActivity.this,"请填写工作经验");
                } else {
                    commitResume();
                }
                break;
            case R.id.tv_show_city:
                Intent intent = new Intent(EditResumeActivity.this, CityPickerActivity.class);
                startActivityForResult(intent, 1);
                break;

                default:break;
        }
    }

    /**
     * 性别选中事件
     * @param view
     * @param ischanged **/
    @OnCheckedChanged({R.id.rb_men, R.id.rb_women})
    public void onRadioCheck(CompoundButton view, boolean ischanged) {
        switch (view.getId()) {
            case R.id.rb_men:
                if (ischanged) {
                    tv_show_sex.setText("男");
                }
                break;
            case R.id.rb_women:
                if (ischanged) {
                    tv_show_sex.setText("女");
                }
                break;
            default:
                break;
        }
    }
    /**
     * 选择身份
     */
    private void setIdentity() {

        final ArrayList<String> list = new ArrayList<>();
        list.add("在校学生");
        list.add("上班族");
        list.add("家庭主妇");
        list.add("自由职业");
        list.add("职业不限");
        final OptionCenterDialog optionCenterDialog = new OptionCenterDialog();
        optionCenterDialog.show(EditResumeActivity.this, list);
        optionCenterDialog.setItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        tv_show_identity.setText("在校学生");
                        break;
                    case 1:
                        tv_show_identity.setText("上班族");
                        break;
                    case 2:
                        tv_show_identity.setText("家庭主妇");
                        break;
                    case 3:
                        tv_show_identity.setText("自由职业");
                        break;
                    case 4:
                        tv_show_identity.setText("职业不限");
                        break;
                    default:break;
                }
                optionCenterDialog.dismiss();
            }
        });
    }

    /**
     *选择出生
     */
    protected void showDatePickDlg() {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(EditResumeActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                tv_show_birth.setText(year + "-" + (monthOfYear+1) + "-" + dayOfMonth);
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    /**
     * 提交简历
     */
    private void commitResume() {

        BmobUser myUser= BmobUser.getCurrentUser(User.class);
        myUser.setValue("name",et_name.getText().toString());
        myUser.setValue("birth",tv_show_birth.getText().toString());
        myUser.setValue("myemail",et_email.getText().toString());
        myUser.setValue("phone",et_phone.getText().toString());
        myUser.setValue("identity",tv_show_identity.getText().toString());
        myUser.setValue("intro",et_intro.getText().toString());
        myUser.setValue("experience",et_experience.getText().toString());
        myUser.setValue("gender",tv_show_sex.getText().toString());
        myUser.setValue("city",tv_show_city.getText().toString());
        myUser.setValue("isResume","有简历");

        myUser.update(myUser.getObjectId(), new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e==null){
                    ToastUtils.setOkToast(EditResumeActivity.this,"简历提交成功！");
                    setResult(200);
                    finish();
                }else {
                    ToastUtils.setOkToast(EditResumeActivity.this,"提交失败，请检查格式是否有误！");
                    Log.i("tag1",e.getMessage().toString());
                }
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == 200) {
                if (data == null) {
                    return;
                }
                tv_show_city.setText(data.getStringExtra("cityname"));
            }
        }
    }


}
