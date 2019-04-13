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

import de.hdodenhof.circleimageview.CircleImageView;

public class BossSelectionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    private List<JobSelection> datalist;
    private Context context;

    private OnItemClickListener onItemClickListener;

    public BossSelectionAdapter(Context context, List<JobSelection> datalist) {
        this.context = context;
        this.datalist = datalist;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View layout = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list_day, viewGroup, false);
        //为每个item添加监听事件
        layout.setOnClickListener(this);
        return new MyHolder(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        int pos = i;
        //注意除去头布局
        viewHolder.itemView.setTag(pos);

        String title = datalist.get(pos).getJob_title();
        String pay = datalist.get(pos).getJob_pay();
        String time = datalist.get(pos).getJob_time();
        String type = datalist.get(pos).getJob_type();
        String company = datalist.get(pos).getJob_company();
        String address = datalist.get(pos).getJob_address();
        String logo = datalist.get(pos).getJob_logo();

        ((MyHolder) viewHolder).tv_job_title.setText(title);
        ((MyHolder) viewHolder).tv_job_pay.setText(pay);
        ((MyHolder) viewHolder).tv_job_time.setText(time);
        ((MyHolder) viewHolder).tv_job_type.setText(type);
        ((MyHolder) viewHolder).tv_job_company.setText(company);
        ((MyHolder) viewHolder).tv_job_address.setText(address);

        if (logo != null) {
            Glide.with(context).load(logo).error(R.drawable.head).into(((MyHolder) viewHolder).civ_boss_logo);
        }
    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {
        TextView tv_job_title;
        TextView tv_job_pay;
        TextView tv_job_time;
        TextView tv_job_type;
        TextView tv_job_company;
        TextView tv_job_address;
        CircleImageView civ_boss_logo;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            tv_job_title = (TextView) itemView.findViewById(R.id.tv_job_title);
            tv_job_pay = (TextView) itemView.findViewById(R.id.tv_job_pay);
            tv_job_time = (TextView) itemView.findViewById(R.id.tv_job_time);
            tv_job_type = (TextView) itemView.findViewById(R.id.tv_job_type);
            tv_job_company = (TextView) itemView.findViewById(R.id.tv_job_company);
            tv_job_address = (TextView) itemView.findViewById(R.id.tv_job_address);
            civ_boss_logo = (CircleImageView) itemView.findViewById(R.id.civ_boss_logo);
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
