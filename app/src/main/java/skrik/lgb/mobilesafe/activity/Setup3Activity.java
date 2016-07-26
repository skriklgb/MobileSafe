package skrik.lgb.mobilesafe.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import skrik.lgb.mobilesafe.R;

public class Setup3Activity extends Activity {

    private EditText mEt_phone_num;
    private Button mBt_select_num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup3);

        initUI();
    }

    private void initUI() {
        //显示电话号码的输入框
        mEt_phone_num = (EditText) findViewById(R.id.et_phone_num);
        mBt_select_num = (Button) findViewById(R.id.bt_select_num);
        mBt_select_num.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ContactListActivity.class);
                startActivityForResult(intent,0);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null){
            //1,返回到当前界面的时候,接受结果的方法
            String phone = data.getStringExtra("phone");
            //2,将特殊字符过滤(中划线转换成空字符串)
            phone = phone.replace("-", "").replace(" ","").trim();
            mEt_phone_num.setText(phone);
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    public void  nextPage(View view){
        Intent intent = new Intent(getApplicationContext(), Setup4Activity.class);
        startActivity(intent);

        finish();
    }

    public void  prePage(View view){
        Intent intent = new Intent(getApplicationContext(), Setup2Activity.class);
        startActivity(intent);

        finish();
    }
}
