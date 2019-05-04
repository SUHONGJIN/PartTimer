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
import com.mfzj.parttimer.bean.ApplyTable;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ApplyDetailsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    private List<ApplyTable> datalist;
    private Context context;

    private OnItemClickListener onItemClickListener;

    public ApplyDetailsAdapter(Context context, List<ApplyTable> datalist) {
        this.context = context;
        this.datalist = datalist;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View layout = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_apply_details_layout, viewGroup, false);
        //为每个item添加监听事件
        layout.setOnClickListener(this);
        return new MyHolder(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        int pos = i;
        //注意除去头布局
        viewHolder.itemView.setTag(pos);

        String name = datalist.get(pos).getUser().getName();
        String birth = datalist.get(pos).getUser().getBirth();
        String city = datalist.get(pos).getUser().getCity();
        String sex = datalist.get(pos).getUser().getGender();
        String avatar = datalist.get(pos).getUser().getAvatar();
        String identity = datalist.get(pos).getUser().getIdentity();


        ((MyHolder) viewHolder).tv_user_name.setText(name);
        ((MyHolder) viewHolder).tv_user_identity.setText(identity);
        ((MyHolder) viewHolder).tv_user_birth.setText(birth);
        ((MyHolder) viewHolder).tv_user_city.setText(city);
        ((MyHolder) viewHolder).tv_user_sex.setText(sex);

        Glide.with(context).load(avatar).placeholder(R.drawable.head).error(R.drawable.head).into(((MyHolder) viewHolder).civ_user_head);

    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {
        TextView tv_user_name;
        TextView tv_user_identity;
        TextView tv_user_birth;
        TextView tv_user_city;
        TextView tv_user_sex;
        CircleImageView civ_user_head;


        public MyHolder(@NonNull View itemView) {
            super(itemView);
            tv_user_name = (TextView) itemView.findViewById(R.id.tv_user_name);
            tv_user_identity = (TextView) itemView.findViewById(R.id.tv_user_identity);
            tv_user_birth = (TextView) itemView.findViewById(R.id.tv_user_birth);
            tv_user_city = (TextView) itemView.findViewById(R.id.tv_user_city);
            tv_user_sex = (TextView) itemView.findViewById(R.id.tv_user_sex);
            civ_user_head = (CircleImageView) itemView.findViewById(R.id.civ_user_head);

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
