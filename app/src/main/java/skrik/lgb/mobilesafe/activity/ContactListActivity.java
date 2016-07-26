package skrik.lgb.mobilesafe.activity;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import skrik.lgb.mobilesafe.R;

public class ContactListActivity extends Activity {

    private ListView mLv_contact;
    private List<HashMap<String,String>> contactlist = new ArrayList<HashMap<String,String>>();
    private Handler mhandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            //8,填充数据适配器
            ContactAdapter contactAdapter = new ContactAdapter();
            mLv_contact.setAdapter(contactAdapter);


        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);

        initUI();

        initData();
    }

    /**
     * 获取系统联系人数据方法
     */
    private void initData() {
//        因为读取联系人有时候是一个耗时操作，所以需要放置到子线程中操作
        new Thread(){
            @Override
            public void run() {
                //        1.获取内容解析器对象
                ContentResolver contentResolver = getContentResolver();
                //2.查询系统联系人数据库表的过程
                Cursor cursor = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,null,null,null);
//                3.循环游标，直到没有数据为止
                contactlist.clear();

                while (cursor.moveToNext()){
                    String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                    String phone = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                    HashMap<String, String> hashMap = new HashMap<String, String>();
                    hashMap.put("name",name);
                    hashMap.put("phone",phone);
                    contactlist.add(hashMap);

                }

                cursor.close();

                //7,消息机制,发送一个空的消息,告知主线程可以去使用子线程已经填充好的数据集合
                mhandler.sendEmptyMessage(0);
            }
        }.start();

    }

    private void initUI() {
        mLv_contact = (ListView) findViewById(R.id.lv_contact);
    }


    private class ContactAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return contactlist.size();
        }

        @Override
        public HashMap<String, String> getItem(int position) {
            return contactlist.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;
            if (convertView == null){
                view = View.inflate(getApplicationContext(), R.layout.listview_contact_item, null);
            }else {
                view = convertView;
            }


            TextView tv_name = (TextView) view.findViewById(R.id.tv_name);
            TextView tv_phone = (TextView) view.findViewById(R.id.tv_phone);
            tv_name.setText(getItem(position).get("name"));
            tv_phone.setText(getItem(position).get("phone"));

            return view;
        }
    }
}
