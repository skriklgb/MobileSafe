package skrik.lgb.mobilesafe.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import skrik.lgb.mobilesafe.R;
import skrik.lgb.mobilesafe.utils.ConstantValue;
import skrik.lgb.mobilesafe.utils.SpUtil;
import skrik.lgb.mobilesafe.utils.ToastUtil;

public class Setup4Activity extends Activity {

    private CheckBox mCb_box;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup4);

        initUI();
    }

    private void initUI() {
        mCb_box = (CheckBox) findViewById(R.id.cb_box);
        //1,是否选中状态的回显
        boolean open_security = SpUtil.getBoolean(this, ConstantValue.OPEN_SECURITY, false);
        //2,根据状态,修改checkbox后续的文字显示
        mCb_box.setChecked(open_security);
        if (open_security){
            mCb_box.setText("安全设置已开启");
        }else {
            mCb_box.setText("安全设置已关闭");
        }
        //3,点击过程中,监听选中状态发生改变过程,
        mCb_box.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //4,isChecked点击后的状态,存储点击后状态
                SpUtil.putBoolean(getApplicationContext(),ConstantValue.OPEN_SECURITY,isChecked);
                //5,根据开启关闭状态,去修改显示的文字
                if (isChecked){
                    mCb_box.setText("安全设置已开启");
                }else {
                    mCb_box.setText("安全设置已关闭");
                }
            }
        });


    }

    public void  nextPage(View view){
        boolean open_security = SpUtil.getBoolean(this, ConstantValue.OPEN_SECURITY, false);
        if (open_security){
            Intent intent = new Intent(getApplicationContext(), SetupOverActivity.class);
            startActivity(intent);

            finish();
            SpUtil.putBoolean(this, ConstantValue.SETUP_OVER,true);
        }else {
            ToastUtil.show(getApplicationContext(),"请开启防盗保护");
        }

    }

    public void  prePage(View view){
        Intent intent = new Intent(getApplicationContext(), Setup3Activity.class);
        startActivity(intent);

        finish();
    }

}
