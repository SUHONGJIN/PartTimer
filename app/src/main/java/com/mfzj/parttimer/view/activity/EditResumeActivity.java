package com.mfzj.parttimer.view.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
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
    @BindView(R.id.btn_commit)
    Button btn_commit;

    @Override
    public int getContentViewResId() {
        return R.layout.activity_edit_resume;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        tv_title.setText("完善简历");
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
        if (user.getGender()==0){
            tv_show_sex.setText("男生");
            rb_men.setChecked(true);
        }else if(user.getGender()==1){
            tv_show_sex.setText("女生");
            rb_women.setChecked(true);
        }
        if (user.getBirth()!=null){
            tv_show_birth.setText(user.getBirth());
        }
        if (user.getIdentity()!=null){
            tv_show_identity.setText(user.getIdentity());
        }
        if (user.getPhone()!=null){
            et_phone.setText(user.getPhone());
        }
        if (user.getEmail()!=null){
            et_email.setText(user.getEmail());
        }
        if (user.getIntro()!=null){
            et_intro.setText(user.getIntro());
        }
        if (user.getExperience()!=null){
            et_experience.setText(user.getExperience());
        }
    }

    @OnClick({R.id.iv_back,R.id.tv_select_identity,R.id.tv_select_birth,R.id.btn_commit})
    public void OnClickE(View view){
        switch (view.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_select_identity:

                setIdentity();

                break;
            case R.id.tv_select_birth:
                showDatePickDlg();
                break;
            case R.id.btn_commit:
                commitResume();
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
                    tv_show_sex.setText("男生");
                }
                break;
            case R.id.rb_women:
                if (ischanged) {
                    tv_show_sex.setText("女生");
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
        list.add("不限");
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
                        tv_show_identity.setText("不限");
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

        DatePickerDialog datePickerDialog = new DatePickerDialog(EditResumeActivity.this,DatePickerDialog.THEME_HOLO_LIGHT, new DatePickerDialog.OnDateSetListener() {
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
        myUser.setValue("email",et_email.getText().toString());
        myUser.setValue("phone",et_phone.getText().toString());
        myUser.setValue("identity",tv_show_identity.getText().toString());
        myUser.setValue("intro",et_intro.getText().toString());
        myUser.setValue("experience",et_experience.getText().toString());

        if (tv_show_sex.getText().equals("男生")){
            ((User) myUser).setGender(0);
        }else if (tv_show_sex.getText().equals("女生")){
            ((User) myUser).setGender(1);
        }
        myUser.update(myUser.getObjectId(), new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e==null){
                    ToastUtils.setOkToast(EditResumeActivity.this,"简历提交成功！");
                    setResult(200);
                    finish();
                }else {
                    ToastUtils.setOkToast(EditResumeActivity.this,"简历提交失败！");
                    Log.i("tag1",e.getMessage().toString());
                }
            }
        });
    }

}