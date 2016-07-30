package skrik.lgb.mobilesafe.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import skrik.lgb.mobilesafe.R;

public class AToolActivity extends Activity {

    private TextView mTv_query_phone_address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atool);

        //电话归属地查询方法
        initPhoneAddress();
    }

    private void initPhoneAddress() {
        mTv_query_phone_address = (TextView) findViewById(R.id.tv_query_phone_address);
        mTv_query_phone_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),QueryAddressActivity.class));
            }
        });
    }
}
