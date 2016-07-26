package skrik.lgb.mobilesafe.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import skrik.lgb.mobilesafe.R;
import skrik.lgb.mobilesafe.utils.ConstantValue;
import skrik.lgb.mobilesafe.utils.SpUtil;
import skrik.lgb.mobilesafe.utils.ToastUtil;

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
        //获取联系人电话号码回显过程
        String phone = SpUtil.getString(getApplicationContext(), ConstantValue.CONTACT_PHONE, "");
        mEt_phone_num.setText(phone);

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
            //3,存储联系人至sp中
            SpUtil.putString(getApplicationContext(), ConstantValue.CONTACT_PHONE,phone);
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    public void  nextPage(View view){
        //点击按钮以后,需要获取输入框中的联系人,再做下一页操作
        String phone = mEt_phone_num.getText().toString();

        //在sp存储了相关联系人以后才可以跳转到下一个界面
//        String contact_phone = SpUtil.getString(getApplicationContext(), ConstantValue.CONTACT_PHONE, "");
        if (!TextUtils.isEmpty(phone)){
            Intent intent = new Intent(getApplicationContext(), Setup4Activity.class);
            startActivity(intent);

            finish();
            //如果现在是输入电话号码,则需要去保存
            SpUtil.putString(getApplicationContext(),ConstantValue.CONTACT_PHONE,phone);

        } else{
            ToastUtil.show(this,"请输入电话号码");
        }

    }

    public void  prePage(View view){
        Intent intent = new Intent(getApplicationContext(), Setup2Activity.class);
        startActivity(intent);

        finish();
    }
}
