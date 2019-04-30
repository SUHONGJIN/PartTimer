package com.mfzj.parttimer.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mfzj.parttimer.R;
import com.mfzj.parttimer.bean.JobSelection;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class BossSelectionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {
    private List<JobSelection> datalist;
    private Context context;
    private OnItemClickListener onItemClickListener;
    /**
     * 构造方法
     *
     * @param context  上下文
     * @param datalist 数据集合
     */
    public BossSelectionAdapter(Context context, List<JobSelection> datalist) {
        this.context = context;
        this.datalist = datalist;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View layout = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list_job, viewGroup, false);
        //为每个item添加监听事件
        layout.setOnClickListener(this);
        return new MyHolder(layout);
    }
    /**
     * 赋值给相应的控件
     *
     * @param viewHolder
     * @param i
     */
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        int pos = i;
        //注意除去头布局
        viewHolder.itemView.setTag(pos);

        String title = datalist.get(pos).getJob_title();  //兼职标题
        String pay = datalist.get(pos).getJob_pay();    //兼职薪资
        String time = datalist.get(pos).getJob_time();  //兼职时间段
        String type = datalist.get(pos).getJob_type();  //兼职类型
        String company = datalist.get(pos).getJob_company();  //雇主名称
        String address = datalist.get(pos).getJob_address();  //兼职地址
        String logo = datalist.get(pos).getJob_logo();  //雇主商标

        ((MyHolder) viewHolder).tv_job_title.setText(title);
        ((MyHolder) viewHolder).tv_job_pay.setText(pay);
        ((MyHolder) viewHolder).tv_job_time.setText(time.substring(0, 10));
        ((MyHolder) viewHolder).tv_job_type.setText(type);
        ((MyHolder) viewHolder).tv_job_company.setText(company);
        ((MyHolder) viewHolder).tv_job_address.setText(address);

        if (logo != null) {
            //网络加载图片
            Glide.with(context).load(logo).error(R.drawable.head).into(((MyHolder) viewHolder).civ_boss_logo);
        }
    }
    /**
     * 获取数据大小
     *
     * @return
     */
    @Override
    public int getItemCount() {
        return datalist.size();
    }
    class MyHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_job_title)
        TextView tv_job_title;
        @BindView(R.id.tv_job_pay)
        TextView tv_job_pay;
        @BindView(R.id.tv_job_time)
        TextView tv_job_time;
        @BindView(R.id.tv_job_type)
        TextView tv_job_type;
        @BindView(R.id.tv_job_company)
        TextView tv_job_company;
        @BindView(R.id.tv_job_address)
        TextView tv_job_address;
        @BindView(R.id.civ_boss_logo)
        CircleImageView civ_boss_logo;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
    @Override
    public void onClick(View view) {
        if (onItemClickListener != null) {
            onItemClickListener.onItemClick(view, (Integer) view.getTag());
        }
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
}
