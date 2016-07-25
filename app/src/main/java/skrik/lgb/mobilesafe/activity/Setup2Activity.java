package skrik.lgb.mobilesafe.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;

import skrik.lgb.mobilesafe.R;
import skrik.lgb.mobilesafe.utils.ConstantValue;
import skrik.lgb.mobilesafe.utils.SpUtil;
import skrik.lgb.mobilesafe.utils.ToastUtil;
import skrik.lgb.mobilesafe.view.SettingItemView;

public class Setup2Activity extends Activity {

    private SettingItemView mSiv_sim_bunnd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup2);

        initUI();
    }

    private void initUI() {
        mSiv_sim_bunnd = (SettingItemView) findViewById(R.id.siv_sim_bunnd);
        //1,回显(读取已有的绑定状态,用作显示,sp中是否存储了sim卡的序列号)
        final String sim_number = SpUtil.getString(this, ConstantValue.SIM_NUMBER, "");
        //2,判断是否序列卡号为""
        if (TextUtils.isEmpty(sim_number)){
            mSiv_sim_bunnd.setCheck(false);
        }else {
            mSiv_sim_bunnd.setCheck(true);
        }

        mSiv_sim_bunnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //3,获取原有的状态
                   boolean isCheck =  mSiv_sim_bunnd.isCheck();
                //4,将原有状态取反并将状态设置给当前条目
                mSiv_sim_bunnd.setCheck(!isCheck);
                //5.判断状态
                if (!isCheck) {
                    //6,存储(序列卡号)
                             //6.1获取sim卡序列号TelephoneManager
                             TelephonyManager manager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
                            //6.2获取sim卡的序列卡号
                              String simSerialNumber = manager.getSimSerialNumber();
                             //6.3存储
                            SpUtil.putString(getApplicationContext(),ConstantValue.SIM_NUMBER,simSerialNumber);
                } else {
                    //7,将存储序列卡号的节点,从sp中删除掉
                    SpUtil.remove(getApplicationContext(),ConstantValue.SIM_NUMBER);
                }
            }
        });

    }

    public void  nextPage(View view){
       String simSerialNumber = SpUtil.getString(this,ConstantValue.SIM_NUMBER,"");
        if (!TextUtils.isEmpty(simSerialNumber)) {
            Intent intent = new Intent(getApplicationContext(), Setup3Activity.class);
            startActivity(intent);

            finish();
        } else {
            ToastUtil.show(this,"请绑定SIM卡");
        }

    }

    public void  prePage(View view){
        Intent intent = new Intent(getApplicationContext(), Setup1Activity.class);
        startActivity(intent);

        finish();
    }
}
