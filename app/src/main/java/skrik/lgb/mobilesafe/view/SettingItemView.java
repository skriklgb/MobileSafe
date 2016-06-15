package skrik.lgb.mobilesafe.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import skrik.lgb.mobilesafe.R;

public class SettingItemView extends RelativeLayout {
    public SettingItemView(Context context) {
        this(context,null);
    }

    public SettingItemView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SettingItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //xml--->view	将设置界面的一个条目转换成view对象,直接添加到了当前SettingItemView对应的view中
        View.inflate(context, R.layout.setting_item_view,this);
        //等同于以下两行代码
		/*View view = View.inflate(context, R.layout.setting_item_view, null);
		this.addView(view);*/
        TextView  tv_setting_titile  = (TextView) findViewById(R.id.tv_setting_titile);
        TextView  tv_setting_des  = (TextView) findViewById(R.id.tv_setting_des);
        CheckBox  cb_setting_box   = (CheckBox) findViewById(R.id.cb_setting_box);
    }


}
