package skrik.lgb.mobilesafe.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import skrik.lgb.mobilesafe.R;
import skrik.lgb.mobilesafe.view.SettingItemView;

public class SettingActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        initUpdate();
    }

    private void initUpdate() {
        final SettingItemView siv_update  = (SettingItemView) findViewById(R.id.siv_update);
        siv_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //如果之前是选中的,点击过后,变成未选中
                //如果之前是未选中的,点击过后,变成选中
                //获取之前的选中状态
               boolean isCheck = siv_update.isCheck();
                //将原有状态取反,等同上诉的两部操作
                siv_update.setCheck(!isCheck);
            }
        });
    }
}
