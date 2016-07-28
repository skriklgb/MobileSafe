package skrik.lgb.mobilesafe.receive;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.util.Log;

import skrik.lgb.mobilesafe.utils.ConstantValue;
import skrik.lgb.mobilesafe.utils.SpUtil;
import skrik.lgb.mobilesafe.utils.ToastUtil;

public class BootReceiver extends BroadcastReceiver {
    public BootReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("BootReceiver","重启手机成功了");
        ToastUtil.show(context,"重启手机成功了");
        //1,获取开机后手机的sim卡的序列号
       TelephonyManager tm= (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String simSerialNumber = tm.getSimSerialNumber()+"xxx";
        //2,sp中存储的序列卡号
        String sim_number = SpUtil.getString(context, ConstantValue.SIM_NUMBER, "");
        //3,比对不一致
        if (!simSerialNumber.equals(sim_number)){
            //4,发送短信给选中联系人号码
            SmsManager smsManager = SmsManager.getDefault();
            String phone = SpUtil.getString(context, ConstantValue.CONTACT_PHONE, "");
            smsManager.sendTextMessage(phone,null,"sim change!!!",null,null);

        }


    }
}
