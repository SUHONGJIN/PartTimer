package com.bee.parttimer.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bee.parttimer.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 自定义菜单项 ItemMenu
 * Created by SuHongJin on 2018/11/10.
 */

public class ItemMenu extends LinearLayout {

    @BindView(R.id.iv_item_menu)
    ImageView iv_item_menu;
    @BindView(R.id.tv_item_menu)
    TextView tv_item_menu;

    /**
     *
     * @param context  上下文
     * @param attrs  属性集合
     */
    public ItemMenu(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.view_itemmenu_ayout,this);

        //绑定注解
        ButterKnife.bind(this);

        //绑定属性
        TypedArray typedArray=context.obtainStyledAttributes(attrs,R.styleable.ItemMenu);

        //获取属性的值
        int setIcon = typedArray.getResourceId(R.styleable.ItemMenu_setMenuIcon,R.mipmap.ic_my_d);
        String setText = typedArray.getString(R.styleable.ItemMenu_setMenuText);

        //设置View
        setView(setIcon,setText);

        //回收
        typedArray.recycle();
    }

    /**
     * 设置我们自定义的View
     * @param ItemMenu_setMenuIcon    设置图标
     * @param ItemMenu_setMenuText    设置文本
     */
    public void setView(int ItemMenu_setMenuIcon ,String ItemMenu_setMenuText){
        if (ItemMenu_setMenuIcon != 0){
            iv_item_menu.setImageResource(ItemMenu_setMenuIcon);
        }
        if (ItemMenu_setMenuText != null){
            tv_item_menu.setText(ItemMenu_setMenuText);
        }
    }
}
