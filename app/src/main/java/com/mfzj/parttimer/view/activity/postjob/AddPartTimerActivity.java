package com.mfzj.parttimer.view.activity.postjob;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.longsh.optionframelibrary.OptionCenterDialog;
import com.mfzj.parttimer.CitySelect.CityPickerActivity;
import com.mfzj.parttimer.R;
import com.mfzj.parttimer.base.BaseActivity;
import com.mfzj.parttimer.bean.JobSelection;
import com.mfzj.parttimer.bean.User;
import com.mfzj.parttimer.utils.ToastUtils;

import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

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
    @BindView(R.id.tv_post_address)
    TextView tv_post_address;
    @BindView(R.id.btn_post_job)
    Button btn_post_job;

    private final  int TIME_START = 0;
    private final  int TIME_END = 1;

    @Override
    public int getContentViewResId() {
        return R.layout.activity_add_part_timer;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        tv_title.setText("发布兼职");
    }

    @Override
    public void initData() {
    }

    @OnClick({R.id.btn_post_job,R.id.iv_back,R.id.tv_post_pay_type,R.id.tv_post_type,R.id.tv_post_time1,R.id.tv_post_time2,R.id.tv_post_city})
    public void Onclick(View view){
        switch (view.getId()){
            case R.id.btn_post_job:
                savePost();
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

                default:break;
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
                switch (position){
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
                    default:break;
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
                switch (position){
                    case 0:
                        tv_post_type.setText("日结");
                        break;
                    case 1:
                        tv_post_type.setText("周结");
                        break;
                    case 2:
                        tv_post_type.setText("月结");
                        break;
                    default:break;
                }
                optionCenterDialog.dismiss();
            }
        });
    }

    /**
     * 工作开始时间和结束时间
     * @param tag
     */
    protected void setTime(final int tag) {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(AddPartTimerActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                if (tag ==TIME_START ){
                    tv_post_time1.setText(year + "年" + (monthOfYear+1) + "月" + dayOfMonth+"日");
                }
                if (tag ==TIME_END ){
                    tv_post_time2.setText(year + "年" + (monthOfYear+1) + "月" + dayOfMonth+"日");
                }

            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }
    /**
     * 添当前用户发布兼职
     */
    private void savePost() {
        if (BmobUser.isLogin()){
            String time1 = tv_post_time1.getText().toString();
            String time2 = tv_post_time2.getText().toString();
            String city = tv_post_city.getText().toString();
            String address = tv_post_address.getText().toString();
            String pay = et_post_pay.getText().toString();
            String pay_type = tv_post_pay_type.getText().toString();
            JobSelection post = new JobSelection();
            post.setJob_title(et_post_title.getText().toString());
            post.setJob_people(et_post_people.getText().toString());
            post.setJob_pay(pay+"/"+pay_type);
            post.setJob_type(tv_post_type.getText().toString());
            post.setJob_describe(et_post_describe.getText().toString());
            post.setJob_time(time1+" - "+time2);
            post.setJob_address(city+address);
            post.setJob_company(et_post_company.getText().toString());
            //添加一对一关联，用户关联帖子
            post.setBoss(BmobUser.getCurrentUser(User.class));
            post.save(new SaveListener<String>() {
                @Override
                public void done(String s, BmobException e) {
                    if (e == null) {
                        ToastUtils.setOkToast(AddPartTimerActivity.this,"发布成功！");
                        finish();
                    } else {
                        ToastUtils.setOkToast(AddPartTimerActivity.this,"发布失败！");
                    }
                }
            });
        }else {
            ToastUtils.setOkToast(AddPartTimerActivity.this,"请先登录！");
        }
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
    }

}
