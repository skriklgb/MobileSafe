package skrik.lgb.mobilesafe.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import skrik.lgb.mobilesafe.R;

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
