package com.mfzj.parttimer.view.activity;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mfzj.parttimer.R;
import com.mfzj.parttimer.adapter.SelectionAdapter;
import com.mfzj.parttimer.base.BaseActivity;
import com.mfzj.parttimer.bean.ApplyTable;
import com.mfzj.parttimer.bean.JobSelection;
import com.mfzj.parttimer.bean.User;
import com.mfzj.parttimer.utils.ToastUtils;
import com.mfzj.parttimer.view.activity.postjob.JobPostDetailsActivity;
import com.mfzj.parttimer.widget.WeiboDialogUtils;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.sharesdk.onekeyshare.OnekeyShare;
import de.hdodenhof.circleimageview.CircleImageView;

public class JobDetailsActivity extends BaseActivity {
    @BindView(R.id.tv_job_details_title)
    TextView tv_job_details_title;
    @BindView(R.id.tv_job_details_time)
    TextView tv_job_details_time;
    @BindView(R.id.tv_job_details_pay)
    TextView tv_job_details_pay;
    @BindView(R.id.tv_job_details_company)
    TextView tv_job_details_company;
    @BindView(R.id.tv_job_details_address)
    TextView tv_job_details_address;
    @BindView(R.id.tv_job_details_describe)
    TextView tv_job_details_describe;
    @BindView(R.id.tv_job_details_people)
    TextView tv_job_details_people;
    @BindView(R.id.tv_job_details_type)
    TextView tv_job_details_type;
    @BindView(R.id.btn_apply)
    Button btn_apply;
    @BindView(R.id.tv_report)
    TextView tv_report;
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.iv_collect)
    ImageView iv_collect;
    @BindView(R.id.iv_call)
    ImageView iv_call;
    @BindView(R.id.iv_share)
    ImageView iv_share;
    @BindView(R.id.civ_boss_logo)
    CircleImageView civ_boss_logo;
    private Dialog mWeiboDialog;

    @Override
    public int getContentViewResId() {
        return R.layout.activity_job_details;
    }

    @Override
    public void initView(Bundle savedInstanceState) {

        String job_title = (String) getIntent().getExtras().get("job_title");
        String job_pay = (String) getIntent().getExtras().get("job_pay");
        String job_time = (String) getIntent().getExtras().get("job_time");
        String job_type = (String) getIntent().getExtras().get("job_type");
        String job_company = (String) getIntent().getExtras().get("job_company");
        String job_address = (String) getIntent().getExtras().get("job_address");
        String job_describe = (String) getIntent().getExtras().get("job_describe");
        String job_people = (String) getIntent().getExtras().get("job_people");
        String job_logo = (String) getIntent().getExtras().get("job_logo");

        if (job_title != null) {
            tv_job_details_title.setText(job_title);
        }
        if (job_pay != null) {
            tv_job_details_pay.setText(job_pay);
        }
        if (job_time != null) {
            tv_job_details_time.setText(job_time);
        }
        if (job_type != null) {
            tv_job_details_type.setText(job_type);
        }
        if (job_company != null) {
            tv_job_details_company.setText(job_company);
        }
        if (job_address != null) {
            tv_job_details_address.setText(job_address);
        }
        if (job_describe != null) {
            tv_job_details_describe.setText(job_describe);
        }
        if (job_people != null) {
            tv_job_details_people.setText(job_people);
        }
        if (job_logo != null) {
            Glide.with(JobDetailsActivity.this).load(job_logo).error(R.drawable.head).into(civ_boss_logo);
        }

    }

    @Override
    public void initData() {

    }

    @OnClick({R.id.iv_back, R.id.iv_collect, R.id.iv_call, R.id.iv_share, R.id.tv_report, R.id.btn_apply,R.id.tv_job_details_address})
    public void OnclickDedatils(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_collect:
                if (BmobUser.isLogin()) {
                    setCollect();
                } else {
                    startActivity(new Intent(JobDetailsActivity.this, LoginActivity.class));
                }
                break;
            case R.id.iv_call:

                if (BmobUser.isLogin()) {
                    startActivity(new Intent(JobDetailsActivity.this, CallBossActivity.class));
                } else {
                    startActivity(new Intent(JobDetailsActivity.this, LoginActivity.class));
                }
                break;
            case R.id.iv_share:
                showShare();
                break;
            case R.id.tv_report:
                ToastUtils.setOkToast(JobDetailsActivity.this, "举报成功！我们会认真处理");
                break;
            case R.id.btn_apply:

                if (BmobUser.isLogin()) {
                    //加载框
                    mWeiboDialog = WeiboDialogUtils.createLoadingDialog(JobDetailsActivity.this, "报名中...");

                    User user = BmobUser.getCurrentUser(User.class);
                    JobSelection apply = new JobSelection();
                    String id = (String) getIntent().getExtras().get("object_id");
                    apply.setObjectId(id);
                    ApplyTable applyTable = new ApplyTable();
                    applyTable.setUser(user);
                    applyTable.setJob(apply);
                    applyTable.setApply("已报名");
                    applyTable.save(new SaveListener<String>() {
                        @Override
                        public void done(String objectId, BmobException e) {
                            if (e == null) {
                                setApply();
                            } else {
                                //Log.i("bmob","失败："+e.getMessage());
                                ToastUtils.setOkToast(JobDetailsActivity.this, "报名失败!");
                                //关闭加载框
                                WeiboDialogUtils.closeDialog(mWeiboDialog);
                            }
                        }
                    });

                } else {
                    startActivity(new Intent(JobDetailsActivity.this, LoginActivity.class));
                }
                break;
            case R.id.tv_job_details_address:
                //调用百度地图导航到工作地点
                Intent i1 = new Intent();
                // 地址解析
                i1.setData(Uri.parse("baidumap://map/geocoder?src=andr.baidu.openAPIdemo&address="+tv_job_details_address.getText()));
                startActivity(i1);
                break;
            default:
                break;
        }
    }

    /**
     * 收藏兼职
     */
    private void setCollect() {
        String id = (String) getIntent().getExtras().get("object_id");
        User user = User.getCurrentUser(User.class);
        JobSelection job = new JobSelection();
        job.setObjectId(id);
//将当前用户添加到Post表中的likes字段值中，表明当前用户喜欢该帖子
        BmobRelation relation = new BmobRelation();
//将当前用户添加到多对多关联中
        relation.add(user);
//多对多关联指向`post`的`likes`字段
        job.setCollect(relation);
        job.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    Log.i("bmob", "多对多关联添加成功");
                    iv_collect.setImageResource(R.mipmap.icon_collect_s);
                    ToastUtils.setOkToast(JobDetailsActivity.this, "收藏成功！");
                } else {
                    Log.i("bmob", "失败：" + e.getMessage());
                    ToastUtils.setOkToast(JobDetailsActivity.this, "收藏失败!");
                }
            }

        });
    }

    /**
     * 把报名信息和本地用户关联
     */
    private void setApply() {
        String id = (String) getIntent().getExtras().get("object_id");
        User user = User.getCurrentUser(User.class);
        JobSelection job = new JobSelection();
        job.setObjectId(id);
        //将当前用户添加到Post表中的likes字段值中，表明当前用户喜欢该帖子
        BmobRelation relation = new BmobRelation();
        //将当前用户添加到多对多关联中
        relation.add(user);
        //多对多关联指向`post`的`likes`字段
        job.setApply(relation);
        job.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    btn_apply.setClickable(false);
                    btn_apply.setText("已报名");
                    ToastUtils.setOkToast(JobDetailsActivity.this, "报名成功");
                    //关闭加载框
                    WeiboDialogUtils.closeDialog(mWeiboDialog);
                } else {
                    Log.i("bmob", "失败：" + e.getMessage());
                    //关闭加载框
                    WeiboDialogUtils.closeDialog(mWeiboDialog);
                    ToastUtils.setOkToast(JobDetailsActivity.this, "报名失败");
                }
            }

        });
    }
    private void showShare() {

        String job_title = (String) getIntent().getExtras().get("job_title");
        String job_describe = (String) getIntent().getExtras().get("job_describe");
        String job_logo = (String) getIntent().getExtras().get("job_logo");

        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        // title标题，微信、QQ和QQ空间等平台使用
        oks.setTitle("我发现了一个有趣的兼职"+job_title+"你也来吧！"+job_describe);
        // titleUrl QQ和QQ空间跳转链接
        oks.setTitleUrl(job_logo);
        // text是分享文本，所有平台都需要这个字段
        oks.setText("我发现了一个有趣的兼职，快来蜜蜂兼职看看吧！");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url在微信、微博，Facebook等平台中使用
        oks.setUrl(job_logo);
        // comment是我对这条分享的评论，仅在人人网使用
        oks.setComment("我发现了一个有趣的兼职，快来蜜蜂兼职看看吧！");
        // 启动分享GUI
        oks.show(this);
    }
}
