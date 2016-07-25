package skrik.lgb.mobilesafe.activity;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import skrik.lgb.mobilesafe.R;

public class ContactListActivity extends Activity {

    private ListView mLv_contact;

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
                Cursor cursor = contentResolver.query(Uri.parse("content://com.android.contacts/raw_contacts"), new String[]{"contact_id"}, null, null, null);
//                3.循环游标，直到没有数据为止
                while (cursor.moveToNext()){
                    String id = cursor.getString(0);
                    Log.d("读取联系人信息","id ="+id);

                }
                cursor.close();
            }
        }.start();

    }

    private void initUI() {
        mLv_contact = (ListView) findViewById(R.id.lv_contact);
    }


}
