package skrik.lgb.mobilesafe.activity;

import android.app.Activity;
import android.os.Bundle;
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


    }

    private void initUI() {
        mLv_contact = (ListView) findViewById(R.id.lv_contact);
    }


}
