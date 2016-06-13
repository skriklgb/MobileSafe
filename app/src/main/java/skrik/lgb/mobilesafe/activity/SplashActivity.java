package skrik.lgb.mobilesafe.activity;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import skrik.lgb.mobilesafe.R;
import skrik.lgb.mobilesafe.utils.StreamUtils;

public class SplashActivity extends Activity {
   protected static final String tag = "SplashActivity ";
    private TextView mTvVersionName;
    private int mLocalVersionCode;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mContext = this;
        initUI();  //初始化UI
        initData();//初始化数据

    }

    /**
     * 获取数据的方法
     */
    private void initData() {
        //1.应用版本名称.
        mTvVersionName.setText("版本名称:"+getVersionName());
        //检测(本地版本号和服务器版本号比对)是否有更新,如果有更新,提示用户下载(member)
        //2,获取本地版本号
        mLocalVersionCode = getVersionCode();
//        Toast.makeText(this,"版本号为："+mVersionCode,Toast.LENGTH_LONG).show();

        //3,获取服务器版本号(客户端发请求,服务端给响应,(json,xml))
        //http://www.oxxx.com/update74.json?key=value  返回200 请求成功,流的方式将数据读取下来
        //json中内容包含:
		/* 更新版本的版本名称
		 * 新版本的描述信息
		 * 服务器版本号
		 * 新版本apk下载地址*/
        checkVersion();

    }

    /**
     * 检测服务器端app的版本，拉取网络，在子线程中实现
     */
    private void checkVersion() {
        new Thread(){
            @Override
            public void run() {
                //发送请求获取数据,参数则为请求json的链接地址
                //http://192.168.13.99:8080/update74.json	测试阶段不是最优
                //仅限于模拟器访问电脑tomcat
                try {
                    //1,封装url地址
                       URL url = new URL("http://192.168.1.200:8080/update.json");
                    //2,开启一个链接
                        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    //3,设置常见请求参数(请求头)
                         //请求超时
                        connection.setConnectTimeout(2000);
                        //读取超时
                        connection.setReadTimeout(2000);
                        //默认就是get请求方式,
                       connection.setRequestMethod("GET");
                    //4,获取请求成功响应码
                    if (connection.getResponseCode() == 200){
                          //5,以流的形式,将数据获取下来
                        InputStream is = connection.getInputStream();
                        //6,将流转换成字符串(工具类封装)
                      String json =   StreamUtils.StreamToString(is);
                        Log.i(tag,json);
                        //7,json解析
                        JSONObject jsonObject = new JSONObject(json);
                        //debug调试,解决问题
                        String versionName = jsonObject.getString("versionName");
                        String versionDes = jsonObject.getString("versionDes");
                        String versionCode = jsonObject.getString("versionCode");
                        String downloadUrl = jsonObject.getString("downloadUrl");
                        //日志打印
                        Log.i(tag,versionName);
                        Log.i(tag,versionDes);
                        Log.i(tag,versionCode);
                        Log.i(tag,downloadUrl);

                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }.start();

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//
//            }
//        }).start();

    }

    /**
     * 获取版本号: gradle文件中
     * @return        当前应用的版本号         非0 则代表获取成功
     */
    private int getVersionCode() {
        //1,包管理者对象packageManager
        PackageManager pm = getPackageManager();
        //2,从包的管理者对象中,获取指定包名的基本信息(版本名称,版本号),传0代表获取基本信息
        try {
            PackageInfo pi = pm.getPackageInfo(getPackageName(),0);
            //3,获取版本号
            return pi.versionCode;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 获取版本名称: gradle文件中
     * @return 当前应用的版本名称    返回null代表异常
     */
    private String getVersionName() {
        //1,包管理者对象packageManager
        PackageManager pm = getPackageManager();
        //2,从包的管理者对象中,获取指定包名的基本信息(版本名称,版本号),传0代表获取基本信息
        try {
            PackageInfo pi = pm.getPackageInfo(getPackageName(),0);
            //3,获取版本名称
            return pi.versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }




    /**
     * 初始化UI方法
     */
    private void initUI() {
        mTvVersionName = (TextView) findViewById(R.id.tv_splash_version);
    }
}
