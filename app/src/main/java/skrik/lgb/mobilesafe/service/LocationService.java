package skrik.lgb.mobilesafe.service;

import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.telephony.SmsManager;

import java.util.List;

import skrik.lgb.mobilesafe.utils.ConstantValue;
import skrik.lgb.mobilesafe.utils.SpUtil;
import skrik.lgb.mobilesafe.utils.ToastUtil;

public class LocationService extends Service {

    private String mProvider;

    /**
     * 在服务创建的时候调用
     */
    @Override
    public void onCreate() {
        super.onCreate();

        //获取手机的经纬度坐标
        //1,获取位置管理者对象
        LocationManager lm = (LocationManager) getSystemService(LOCATION_SERVICE);
        //2.获取所有可用的位置提供器
        List<String> providers = lm.getProviders(true);
        if (providers.contains(LocationManager.GPS_PROVIDER)){
            mProvider = LocationManager.GPS_PROVIDER;
        } else if(providers.contains(LocationManager.NETWORK_PROVIDER)){
            mProvider = LocationManager.NETWORK_PROVIDER;
        } else {
            ToastUtil.show(getApplicationContext(),"请打开GPS");
        }
        Location location = lm.getLastKnownLocation(mProvider);
        if (location != null){
            showLocation(location);
        }
        //3,在一定时间间隔,移动一定距离后获取经纬度坐标
        MyLocationListener myLocationListener = new MyLocationListener();
            //第一个参数是位置提供器的类型,第二个参数是监听位置变化的时间间隔，以毫秒为单位，第三个参数是监听位置变化的距离间隔，以米为单位,第四个参数则是LocationListener 监听器
        lm.requestLocationUpdates(mProvider,0,0,myLocationListener);
    }

    private void showLocation(Location location) {
        //经度
        double longitude = location.getLongitude();
        //纬度
        double latitude = location.getLatitude();
        //4,发送短信(添加权限)
        SmsManager smsManager = SmsManager.getDefault();
        String phone = SpUtil.getString(getApplicationContext(), ConstantValue.CONTACT_PHONE, "");
        smsManager.sendTextMessage(phone,null,"longitude = "+longitude+",latitude = "+latitude,null,null);
    }

    private class MyLocationListener implements LocationListener {
        @Override
        public void onLocationChanged(Location location) {
            showLocation(location);

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    }
    /**
     * 在每次服务启动的时候调用,通常情况下，如果我们希望服务一旦启动就立刻去执行某个动作，就可以将逻辑写在onStartCommand()方法里
     * @param intent
     * @param flags
     * @param startId
     * @return
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    /**
     *Service 中唯一的一个抽象方法，所以必须要在子类里实现。我们会在后面的小节中使用到onBind()方法，目前可以暂时将它忽略掉。
     * @param intent
     * @return
     */
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /**
     * 在服务销毁的时候调用，当服务销毁时，我们又应该在onDestroy()方法中去回收那些不再使用的资源。
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
    }


}
