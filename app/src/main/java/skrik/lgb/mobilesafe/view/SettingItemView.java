package skrik.lgb.mobilesafe.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import skrik.lgb.mobilesafe.R;

public class SettingItemView extends RelativeLayout {

    private CheckBox mCb_setting_box;
    private TextView mTv_setting_des;

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
        mTv_setting_des = (TextView) findViewById(R.id.tv_setting_des);
        mCb_setting_box = (CheckBox) findViewById(R.id.cb_setting_box);
    }

    /**
     * 判断是否开启的方法
     * @return  返回当前SettingItemView是否选中状态	true开启(checkBox返回true)	false关闭(checkBox返回false)
     */
    public boolean isCheck(){
        //由checkBox的选中结果,决定当前条目是否开启
            return mCb_setting_box.isChecked();
    }

    /**
     *
     * @param isCheck 作为是否开启的变量,由点击过程中去做传递
     */
    public void setCheck(boolean isCheck){
        //当前条目在选择的过程中,mCb_setting_box选中状态也在跟随(isCheck)变化
            mCb_setting_box.setChecked(isCheck);
        if (isCheck){
            mTv_setting_des.setText("自动更新已开启");
        } else {
            mTv_setting_des.setText("自动更新已关闭");
        }
    }

}
