package com.mfzj.parttimer.view.activity;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.mfzj.parttimer.R;
import com.mfzj.parttimer.base.BaseActivity;
import com.mfzj.parttimer.bean.FeedBack;
import com.mfzj.parttimer.utils.ToastUtils;

import butterknife.BindView;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class FeedBackActivity extends BaseActivity {
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.rg_feedback)
    RadioGroup rg_feedback;
    @BindView(R.id.rb_feedback1)
    RadioButton rb_feedback1;
    @BindView(R.id.rb_feedback2)
    RadioButton rb_feedback2;
    @BindView(R.id.rb_feedback3)
    RadioButton rb_feedback3;
    @BindView(R.id.et_feedback)
    EditText et_feedback;
    @BindView(R.id.tv_feedback_type)
    TextView tv_feedback_type;
    @BindView(R.id.btn_commit_feedback)
    Button btn_commit_feedback;

    @Override
    public int getContentViewResId() {
        return R.layout.activity_feed_back;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        tv_title.setText("意见反馈");
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public void initData() {
        //设置默认点击项
        rb_feedback1.setChecked(true);

        rg_feedback.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.rb_feedback1:
                        tv_feedback_type.setText("产品功能意见");
                        break;
                    case R.id.rb_feedback2:
                        tv_feedback_type.setText("投诉建议");
                        break;
                    case R.id.rb_feedback3:
                        tv_feedback_type.setText("其他问题");
                        break;
                }
            }
        });
        btn_commit_feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(et_feedback.getText().toString().trim())) {
                    ToastUtils.setOkToast(FeedBackActivity.this, "反馈信息不能为空");
                } else {
                    sendFeedBack();
                }
            }
        });
    }

    //发送反馈信息
    private void sendFeedBack() {
        FeedBack feedback=new FeedBack();
        feedback.setFeedbackType(tv_feedback_type.getText().toString());
        feedback.setFeedbackContent(et_feedback.getText().toString());
        feedback.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e==null){
                    ToastUtils.setOkToast(FeedBackActivity.this, "反馈成功！");
                    finish();
                }else {
                    ToastUtils.setOkToast(FeedBackActivity.this, "反馈失败！");
                }
            }
        });
    }

}
