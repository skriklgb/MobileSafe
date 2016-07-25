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
//                    Log.d("读取联系人信息","id ="+id);
                    //4,根据用户唯一性id值,查询data表和mimetype表生成的视图,获取data以及mimetype字段
                    Cursor indexcursor = contentResolver.query(Uri.parse("content://com.android.contacts/data"), new String[]{"data1", "mimetype"}, "raw_contact_id = ?", new String[]{id}, null);
                    //5,循环获取每一个联系人的电话号码以及姓名,数据类型
                    while (indexcursor.moveToNext()){
                        Log.i("循环获取每一个联系人的电话号码以及姓名数据类型","data = "+indexcursor.getString(0));
                        Log.i("循环获取每一个联系人的电话号码以及姓名数据类型","mimetype = "+indexcursor.getString(1));
                    }
                    indexcursor.close();
                }
                cursor.close();
            }
        }.start();

    }

    private void initUI() {
        mLv_contact = (ListView) findViewById(R.id.lv_contact);
    }


}
