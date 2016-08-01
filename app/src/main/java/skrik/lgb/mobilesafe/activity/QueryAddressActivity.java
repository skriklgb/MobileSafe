package skrik.lgb.mobilesafe.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import skrik.lgb.mobilesafe.R;
import skrik.lgb.mobilesafe.engine.AddressDao;

public class QueryAddressActivity extends Activity {

    private EditText mEt_phone;
    private Button mBt_query;
    private TextView mTv_query_result;
    private Handler mHandler =new Handler(){
        @Override
        public void handleMessage(Message msg) {
            //4,控件使用查询结果
            mTv_query_result.setText(mAddress);

        }
    };
    private String mAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query_address);

        initUI();
    }

    private void initUI() {
        mEt_phone = (EditText) findViewById(R.id.et_phone);
        mBt_query = (Button) findViewById(R.id.bt_query);
        mTv_query_result = (TextView) findViewById(R.id.tv_query_result);

        //1,点查询功能,注册按钮的点击事件
        mBt_query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = mEt_phone.getText().toString();
                //2,查询是耗时操作,开启子线程
                query(phone);

            }
        });
    }

    /**
     * 耗时操作
     * 获取电话号码归属地
     * @param phone	查询电话号码
     */
    private void query(final String phone) {
        new Thread(){
            @Override
            public void run() {
                mAddress = AddressDao.getAddress(phone);
                //3,消息机制,告知主线程查询结束,可以去使用查询结果
                mHandler.sendEmptyMessage(0);
            }
        }.start();
    }
}
