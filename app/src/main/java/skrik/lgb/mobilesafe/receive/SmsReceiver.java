package skrik.lgb.mobilesafe.receive;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.telephony.SmsMessage;

import skrik.lgb.mobilesafe.R;
import skrik.lgb.mobilesafe.service.LocationService;
import skrik.lgb.mobilesafe.utils.ConstantValue;
import skrik.lgb.mobilesafe.utils.SpUtil;

public class SmsReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        //1,判断是否开启了防盗保护
        boolean open_security = SpUtil.getBoolean(context, ConstantValue.OPEN_SECURITY, false);
        if (open_security){
            //2,获取短信内容
            Object[] objects = (Object[]) intent.getExtras().get("pdus");
            //3,循环遍历短信过程
            for (Object object : objects){
                //4,获取短信对象
                SmsMessage sms = SmsMessage.createFromPdu((byte[]) object);
                String originatingAddress = sms.getOriginatingAddress();
                String messageBody = sms.getMessageBody();

                //6,判断是否包含播放音乐的关键字
                if (messageBody.contains("#*alarm*#")){
                    //7,播放音乐(准备音乐,MediaPlayer)
                    MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.ylzs);
                    mediaPlayer.setLooping(true);
                    mediaPlayer.start();
                }
                if (messageBody.contains("#*location*#")){
                    //8,开启获取位置服务
                    context.startService(new Intent(context, LocationService.class));
                }
                if(messageBody.contains("#*lockscrenn*#")){
                }
                if(messageBody.contains("#*wipedate*#")){
                }
            }


        }

    }
}
