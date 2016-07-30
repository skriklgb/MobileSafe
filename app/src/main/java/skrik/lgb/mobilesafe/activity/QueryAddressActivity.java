package skrik.lgb.mobilesafe.activity;

import android.app.Activity;
import android.os.Bundle;

import skrik.lgb.mobilesafe.R;
import skrik.lgb.mobilesafe.engine.AddressDao;

public class QueryAddressActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query_address);

        AddressDao.getAddress("1300000");
    }
}
