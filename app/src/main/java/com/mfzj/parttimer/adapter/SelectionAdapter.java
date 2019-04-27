package com.mfzj.parttimer.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mfzj.parttimer.R;
import com.mfzj.parttimer.bean.JobSelection;
import com.mfzj.parttimer.view.activity.JobStrategyActivity;
import com.mfzj.parttimer.view.activity.WebDetailsActivity;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoaderInterface;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class SelectionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener{

    private List<JobSelection> datalist;
    private Context context;
    public static final int TYPE_HEADER = 0;
    public static final int TYPE_NORMAL = 1;
    //轮播图的集合
    private ArrayList<String> images_list;
    private ArrayList<String> titles_list;
    private ArrayList<String> web_list;

    private int headCount=1;//头部个数


    private OnItemClickListener onItemClickListener;

    public SelectionAdapter(Context context, List<JobSelection> datalist, ArrayList<String> titles_list, ArrayList<String> images_list, ArrayList<String> web_list) {
        this.context = context;
        this.datalist=datalist;
        this.titles_list = titles_list;
        this.images_list = images_list;
        this.web_list = web_list;
    }

    @Override
    public int getItemViewType(int position) {

        if (isHead(position)) {
            return TYPE_HEADER;
        } else {
            return TYPE_NORMAL;
        }
    }

    private boolean isHead(int position) {
        return headCount!=0&&position<headCount;
    }
    private int getBodySize() {
        return datalist.size();
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (i == TYPE_HEADER) {
            View layout = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_header_layout, viewGroup, false);
            return new MyHeaderHolder(layout);
        } else if (i == TYPE_NORMAL) {
            View layout = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list_job, viewGroup, false);
            //为每个item添加监听事件
            layout.setOnClickListener(this);
            return new MyHolder(layout);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        if (viewHolder instanceof MyHeaderHolder) {

            //设置banner样式
            ((MyHeaderHolder) viewHolder).mBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
            //设置图片加载器，图片加载器在下方
            ((MyHeaderHolder) viewHolder).mBanner.setImageLoader(new MyLoader());
            //设置图片地址集合
            ((MyHeaderHolder) viewHolder).mBanner.setImages(this.images_list);
            //设置标题的集合
            ((MyHeaderHolder) viewHolder).mBanner.setBannerTitles(this.titles_list);
            //设置轮播间隔时间
            ((MyHeaderHolder) viewHolder).mBanner.setDelayTime(3000);
            //设置轮播动画效果
            ((MyHeaderHolder) viewHolder).mBanner.setBannerAnimation(Transformer.Default);
            //设置轮播
            ((MyHeaderHolder) viewHolder).mBanner.isAutoPlay(true);
            //设置指示器位置
            ((MyHeaderHolder) viewHolder).mBanner.setIndicatorGravity(BannerConfig.CENTER);
            //设置监听
            //mBanner.setOnBannerListener(this);
            //启动轮播图
            ((MyHeaderHolder) viewHolder).mBanner.start();
            //轮播图的点击事件
            ((MyHeaderHolder) viewHolder).mBanner.setOnBannerListener(new OnBannerListener() {
                @Override
                public void OnBannerClick(int position) {
                    Intent intent = new Intent(context, WebDetailsActivity.class);
                    intent.putExtra("url", web_list.get(position));
                    context.startActivity(intent);
                }
            });

            ((MyHeaderHolder) viewHolder).ll_strategy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    context.startActivity(new Intent(context, JobStrategyActivity.class));
                }
            });

            ((MyHeaderHolder) viewHolder).tv_banner1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, WebDetailsActivity.class);
                    intent.putExtra("url", "http://bmob-cdn-24662.b0.upaiyun.com/2019/04/12/cc53499a40f12f8680c28d778804c8eb.html");
                    context.startActivity(intent);
                }
            });
            ((MyHeaderHolder) viewHolder).tv_banner2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, WebDetailsActivity.class);
                    intent.putExtra("url", "http://bmob-cdn-24662.b0.upaiyun.com/2019/04/12/cc53499a40f12f8680c28d778804c8eb.html");
                    context.startActivity(intent);
                }
            });
            ((MyHeaderHolder) viewHolder).tv_banner3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, WebDetailsActivity.class);
                    intent.putExtra("url", "http://bmob-cdn-24662.b0.upaiyun.com/2019/04/12/cc53499a40f12f8680c28d778804c8eb.html");
                    context.startActivity(intent);
                }
            });
            ((MyHeaderHolder) viewHolder).tv_banner4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, WebDetailsActivity.class);
                    intent.putExtra("url", "http://bmob-cdn-24662.b0.upaiyun.com/2019/04/12/cc53499a40f12f8680c28d778804c8eb.html");
                    context.startActivity(intent);
                }
            });


        }

        if (viewHolder instanceof MyHolder) {
            int pos = i-1;
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
            ((MyHolder) viewHolder).tv_job_time.setText(time.substring(0,10));
            ((MyHolder) viewHolder).tv_job_type.setText(type);
            ((MyHolder) viewHolder).tv_job_company.setText(company);
            ((MyHolder) viewHolder).tv_job_address.setText(address);
            if (logo!=null){
                Glide.with(context).load(logo).error(R.drawable.head).into(((MyHolder) viewHolder).civ_boss_logo);
            }


        }
    }

    //轮播图图片加载器
    private class MyLoader implements ImageLoaderInterface {
        @Override
        public void displayImage(Context context, Object path, View imageView) {
            Glide.with(context).load(path).error(R.drawable.no_banner).into((ImageView) imageView);
        }

        @Override
        public View createImageView(Context context) {
            return null;
        }
    }

    @Override
    public int getItemCount() {
        if(datalist==null){
            return headCount;
        }
        return getBodySize()+headCount;
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

    class MyHeaderHolder extends RecyclerView.ViewHolder {

        Banner mBanner;
        LinearLayout ll_strategy;
        TextView tv_banner1;
        TextView tv_banner2;
        TextView tv_banner3;
        TextView tv_banner4;

        public MyHeaderHolder(@NonNull View itemView) {
            super(itemView);
            mBanner = (Banner) itemView.findViewById(R.id.mBanner);
            ll_strategy = (LinearLayout) itemView.findViewById(R.id.ll_strategy);
            tv_banner1= (TextView)itemView.findViewById(R.id.tv_banner1);
            tv_banner2= (TextView)itemView.findViewById(R.id.tv_banner2);
            tv_banner3= (TextView)itemView.findViewById(R.id.tv_banner3);
            tv_banner4= (TextView)itemView.findViewById(R.id.tv_banner4);
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
        void onItemClick(View view,int position);
    }

}
