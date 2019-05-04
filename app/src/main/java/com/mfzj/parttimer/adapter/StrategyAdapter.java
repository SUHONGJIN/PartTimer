package com.mfzj.parttimer.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mfzj.parttimer.R;
import com.mfzj.parttimer.bean.StrategyTable;
import com.mfzj.parttimer.utils.ToastUtils;

import java.util.List;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

public class StrategyAdapter extends RecyclerView.Adapter<StrategyAdapter.MyViewHolder> implements View.OnClickListener {

    private List<StrategyTable> datalist;
    private Context context;
    private OnItemClickListener onItemClickListener;


    public StrategyAdapter(List<StrategyTable> datalist, Context context) {
        this.datalist = datalist;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View layout = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_strategy_layout, viewGroup, false);
        //为每个item添加监听事件
        layout.setOnClickListener(this);
        return new MyViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {
        myViewHolder.itemView.setTag(i);

        ((MyViewHolder)myViewHolder).tv_str_title.setText(datalist.get(i).getStrategy_title());
        if (datalist.get(i).getStrategy_read()==null){
            ((MyViewHolder)myViewHolder).tv_str_read.setText("0");
        }else {
            ((MyViewHolder)myViewHolder).tv_str_read.setText(""+datalist.get(i).getStrategy_read());
        }
        if (datalist.get(i).getStrategy_like()==null){
            ((MyViewHolder)myViewHolder).tv_str_like.setText("0");
        }else {
            ((MyViewHolder)myViewHolder).tv_str_like.setText(""+datalist.get(i).getStrategy_like());
            ((MyViewHolder)myViewHolder).tv_str_like.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    StrategyTable strategyTable = new StrategyTable();
                    strategyTable.setStrategy_like(datalist.get(i).getStrategy_like()+1);
                    strategyTable.update(datalist.get(i).getObjectId(), new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e==null){
                                ToastUtils.setOkToast(context,"点赞+1");
                            }else {
                                ToastUtils.setOkToast(context,"点赞失败！");
                            }
                        }
                    });
                }
            });
        }

        Glide.with(context).load(datalist.get(i).getStrategy_image_url()).placeholder(R.drawable.banner_default).error(R.drawable.banner_default).into(((MyViewHolder)myViewHolder).iv_str_img);
    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView iv_str_img;
        TextView tv_str_title;
        TextView tv_str_read;
        TextView tv_str_like;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_str_img = (ImageView)itemView.findViewById(R.id.iv_str_img);
            tv_str_title = (TextView)itemView.findViewById(R.id.tv_str_title);
            tv_str_read = (TextView)itemView.findViewById(R.id.tv_str_read);
            tv_str_like = (TextView)itemView.findViewById(R.id.tv_str_like);
        }
    }
    @Override
    public void onClick(View view) {
        if(onItemClickListener!=null){
            onItemClickListener.onItemClick(view, (Integer) view.getTag());
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener=onItemClickListener;
    }

    public interface OnItemClickListener{
        void onItemClick(View view, int position);
    }

}
