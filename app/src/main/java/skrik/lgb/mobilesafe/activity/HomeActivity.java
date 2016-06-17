package skrik.lgb.mobilesafe.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import skrik.lgb.mobilesafe.R;
import skrik.lgb.mobilesafe.utils.ConstantValue;
import skrik.lgb.mobilesafe.utils.SpUtil;
import skrik.lgb.mobilesafe.utils.ToastUtil;

public class HomeActivity extends Activity {

    private GridView mGv_home;
    private String[] mTitleStr;
    private int[] mDrawableIds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        
        initUI();
        //初始化数据的方法
        initData();
    }

    private void initData() {
        //准备数据(文字(9组),图片(9张))
        mTitleStr = new String[ ]{
                  "手机防盗","通信卫士","软件管理","进程管理","流量统计","手机杀毒","缓存清理","高级工具","设置中心"
          };
        mDrawableIds = new int[]{
                  R.drawable.home_safe,R.drawable.home_callmsgsafe,
                  R.drawable.home_apps,R.drawable.home_taskmanager,
                  R.drawable.home_netmanager,R.drawable.home_trojan,
                  R.drawable.home_sysoptimize,R.drawable.home_tools,R.drawable.home_settings
          };
        //九宫格控件设置数据适配器(等同ListView数据适配器)
        mGv_home.setAdapter(new MyAdapter());
        //注册九宫格单个条目点击事件
        mGv_home.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            //点中列表条目索引position
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               switch (position){
                    case 0:
                        //开启对话框
                        showDialog();
                        break;
                    case 8:
                       //开启通信卫士模块
                        Intent intent =  new Intent(getApplicationContext(),SettingActivity.class);
                        startActivity(intent);
                     default:
                        break;
                    } 
            }
        });
    }

    protected void showDialog() {
        //判断本地是否有存储密码(sp	字符串)
       String psd = SpUtil.getString(this, ConstantValue.MOBLIE_SAFE_KEY,null);
        if (TextUtils.isEmpty(psd)) {
            //1,初始设置密码对话框
            showSetPsdDialog();
        } else {
            //2,确认密码对话框
            showConfirmPsdDialog();
        }



    }

    private void showConfirmPsdDialog() {

    }

    private void showSetPsdDialog() {
        //因为需要去自己定义对话框的展示样式,所以需要调用dialog.setView(view);
        //view是由自己编写的xml转换成的view对象xml----->view
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog dialog = builder.create();
        final View view = View.inflate(this,R.layout.dialog_set_psd,null);//在内部类使用加final
        //让对话框显示一个自己定义的对话框界面效果
        dialog.setView(view);
        dialog.show();

        Button bt_safe_submmit  = (Button) view.findViewById(R.id.bt_safe_submmit);
        Button bt_safe_cancel  = (Button) view.findViewById(R.id.bt_safe_cancel);
        bt_safe_submmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText et_set_pwd = (EditText) view.findViewById(R.id.et_set_pwd);
                EditText et_confirm_pwd = (EditText) view.findViewById(R.id.et_confirm_pwd);

                String pwd = et_set_pwd.getText().toString();
                String confirmPsd = et_confirm_pwd.getText().toString();

                if ( !TextUtils.isEmpty(pwd) &&!TextUtils.isEmpty(confirmPsd)) {
                    if (pwd.equals(confirmPsd)) {
                        //进入应用手机防盗模块,开启一个新的activity
                         Intent intent =   new Intent(getApplicationContext(),TestActivity.class);
                        startActivity(intent);
                        //跳转到新的界面以后需要去隐藏对话框
                        dialog.dismiss();

                    } else {
                        ToastUtil.show(getApplicationContext(),"确认密码错误");
                    }
                } else {
                    //提示用户密码输入有为空的情况
                    ToastUtil.show(getApplicationContext(),"请输入密码");
                }
            }
        });

        bt_safe_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }


    class MyAdapter extends BaseAdapter{

        @Override
        public int getCount() {      //得到数据的行数
            //条目的总数	文字组数 == 图片张数
            return mTitleStr.length;
        }

        @Override
        public Object getItem(int position) {  //根据position得到某一行的记录
            return mTitleStr[position];
        }

        @Override
        public long getItemId(int position) { //得到某一条记录的ID
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {  //我们通常会给它写个布局文件
            View view = View.inflate(getApplicationContext(),R.layout.gridview_item, null);
           TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
           ImageView iv_icon = (ImageView) view.findViewById(R.id.iv_icon);
            tv_title.setText(mTitleStr[position]);
            iv_icon.setBackgroundResource(mDrawableIds[position]);
            return view;
        }
    }


    private void initUI() {
        mGv_home = (GridView) findViewById(R.id.gv_home);

    }
}
