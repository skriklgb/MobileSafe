package skrik.lgb.mobilesafe.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import skrik.lgb.mobilesafe.R;

public class Setup1Activity extends Activity {

    private GestureDetector mGestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup1);
        //2,创建手势管理的对象,用作管理在onTouchEvent(event)传递过来的手势动作
        mGestureDetector = new GestureDetector(this,new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                //监听手势的移动
                if (e1.getX()-e2.getX()>0){
                    Intent intent = new Intent(getApplicationContext(), Setup2Activity.class);
                    startActivity(intent);

                    finish();
                    overridePendingTransition(R.anim.next_in_anim, R.anim.next_out_anim);
                }
                if (e1.getX()-e2.getX()<0){

                }
                return super.onFling(e1, e2, velocityX, velocityY);
            }
        });
    }

    //1,监听屏幕上响应的事件类型(按下(1次),移动(多次),抬起(1次))
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //3,通过手势处理类,接收多种类型的事件,用作处理
        mGestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    public void  nextPage(View view){
            Intent intent = new Intent(getApplicationContext(), Setup2Activity.class);
            startActivity(intent);

            finish();
            overridePendingTransition(R.anim.next_in_anim, R.anim.next_out_anim);
        }

}
