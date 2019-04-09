package com.mfzj.parttimer.view.activity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.mfzj.parttimer.R;
import com.mfzj.parttimer.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class HelpActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.tv_title)
    TextView tv_title;
    private ExpandableListView my_expandable;
    String[] groupArray={
            "兼职可靠吗？",
            "兼职怎么报名？",
            "兼职要收费不？",
            "绑定个人信息安全吗？"
    };

    String[][] childArray={
            {"蜜蜂兼职的所有兼职都是经过严格审核后才上架的，如遇到个别有收费情况，请点击举报按钮进行举报。"},
            {"兼职怎么报名？点开首页的列表，进入可以进行报名参加兼职。"},
            {"兼职信息都是免费的，如有收费，可以在平台进行投诉反馈，我们工作人员会第一时间进行核实处理。"},
            {"蜜蜂兼职运用全新安全技术保障用户的信息安全，同时蜜蜂兼职将严守用户隐私。"}
    };


    @Override
    public int getContentViewResId() {
        return R.layout.activity_help;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        tv_title.setText("常见问题FAQ");
        my_expandable=(ExpandableListView)findViewById(R.id.my_expandable);
        MyBaseAdapter adapter=new MyBaseAdapter();
        my_expandable.setAdapter(adapter);
    }

    @Override
    public void initData() {

    }

    @OnClick(R.id.iv_back)
    public void onClick(View view){
        finish();
    }

    private class MyBaseAdapter extends BaseExpandableListAdapter{
        @Override
        public int getGroupCount() {
            return groupArray.length;
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return childArray[groupPosition].length;
        }

        @Override
        public Object getGroup(int groupPosition) {
            return groupArray[groupPosition];
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return childArray[groupPosition][childPosition];
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition*100;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition*100+childPosition;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            View views=View.inflate(HelpActivity.this,R.layout.item_group_layout,null);
            TextView textView=(TextView)views.findViewById(R.id.tv_group);
            Object data=getGroup(groupPosition);
            textView.setText((String)data);
            return views;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            View childview=View.inflate(HelpActivity.this,R.layout.item_child_layout,null);
            TextView textView1=(TextView)childview.findViewById(R.id.tv_child);
            Object childdata=getChild(groupPosition,childPosition);
            textView1.setText((String)childdata);
            return childview;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return false;
        }
    }
}
