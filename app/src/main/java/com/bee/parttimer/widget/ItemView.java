package com.bee.parttimer.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bee.parttimer.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by SuHongJin on 2018/11/9.
 */

/**
 * 继承布局，自定义ItemView
 */
public class ItemView extends LinearLayout {

    @BindView(R.id.iv_itemview_icon)
    ImageView iv_itemview_icon;
    @BindView(R.id.tv_itemview_text)
    TextView tv_itemview_text;
    @BindView(R.id.iv_itemview_right)
    ImageView iv_itemview_right;
    @BindView(R.id.v_itemview_line)
    View v_itemview_line;
    @BindView(R.id.ll_itemview)
    LinearLayout ll_itemview;

    public ItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.view_itemview_layout,this);

        ButterKnife.bind(this);

        //获取属性的值
        TypedArray typedArray=context.obtainStyledAttributes(attrs,R.styleable.ItemView);

        int setIcon=typedArray.getResourceId(R.styleable.ItemView_setIcon,R.mipmap.ic_my_d);
        String setText=typedArray.getString(R.styleable.ItemView_setText);
        Boolean isShowLeftIcon=typedArray.getBoolean(R.styleable.ItemView_isShowLeftIcon,true);
        Boolean isShowRightIcon=typedArray.getBoolean(R.styleable.ItemView_isShowRightIcon,true);
        Boolean isShowLine=typedArray.getBoolean(R.styleable.ItemView_isShowLine,true);

        //通过绑定属性设置自定义的View
        setView(setIcon,setText,isShowLeftIcon,isShowRightIcon,isShowLine);

        //回收
        typedArray.recycle();
    }
    /**
     * 可调用该方法去设置我们的自定义View
     * @param setIcon  设置图片
     * @param setText  设置标题
     * @param isShowLeftIcon   是否显示左边的图标
     * @param isShowRightIcon   是否显示右边的图标
     * @param isShowLine  是否显示线条
     */
    public void setView(int setIcon,String setText,Boolean isShowLeftIcon,Boolean isShowRightIcon,Boolean isShowLine){

        if (setIcon != 0){
            iv_itemview_icon.setImageResource(setIcon);
        }

        if (setText != null){
            tv_itemview_text.setText(setText);
        }

        if (isShowLeftIcon){
            iv_itemview_icon.setVisibility(View.VISIBLE);
        }else {
            iv_itemview_icon.setVisibility(View.GONE);
        }

        if (isShowRightIcon){
            iv_itemview_right.setVisibility(View.VISIBLE);
        }else {
            iv_itemview_right.setVisibility(View.GONE);
        }

        if (isShowLine){
            v_itemview_line.setVisibility(View.VISIBLE);
        }else {
            v_itemview_line.setVisibility(View.GONE);
        }

    }

    /**
     * 设置点击事件
     * @param listener
     */
    public void setOnItemViewClickListener(OnClickListener listener){
        ll_itemview.setOnClickListener(listener);
    }

}
