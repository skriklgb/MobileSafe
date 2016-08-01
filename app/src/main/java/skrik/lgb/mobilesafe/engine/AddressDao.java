package skrik.lgb.mobilesafe.engine;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class AddressDao {
    //1,指定访问数据库的路径
    private static String path = "data/data/skrik.lgb.mobilesafe/files/address.db";
    private static final String tag = "AddressDao";
    private static String mAddress = "未知号码";

    /**传递一个电话号码,开启数据库连接,进行访问,返回一个归属地
     * @param phone	查询电话号码
     */
    public static String getAddress(String phone){
        mAddress = "未知号码";
        //正则表达式,匹配手机号码
        //手机号码的正则表达式:
        /*
        * ^       匹配输入字符串的开始位置。如果设置了RegExp对象的Multiline属性，^也匹配“\n”或“\r”之后的位置

[a-z]  字符范围。匹配指定范围内的任意字符。例如，“[a-z]”可以匹配“a”到“z”范围内的任意小写字母字符。
         注意:只有连字符在字符组内部时,并且出现在两个字符之间时,才能表示字符的范围; 如果出字符组的开头,则只能表示连字符本身.

\d     匹配一个数字字符。等价于[0-9]。grep 要加上-P，perl正则支持
        * */
        String regularExpression = "^1[3-8]\\d{9}";
        //2,开启数据库连接(只读的形式打开)
        SQLiteDatabase db = SQLiteDatabase.openDatabase(path,null,SQLiteDatabase.OPEN_READONLY);
        if (phone.matches(regularExpression)){
            phone=phone.substring(0,7);
            //3,数据库查询; "表名", new String[]{"字段1，字段2"}, "条件1=? and 条件2=?", new String[]{"条件1的值，条件2的值"},null,null,null
            Cursor cursor = db.query("data1", new String[]{"outkey"}, "id=?", new String[]{phone}, null, null, null);
            if (cursor.moveToNext()){
                String outkey = cursor.getString(0);
                Log.i(tag, "outkey = "+outkey);
                //5,通过data1查询到的结果,作为外键查询data2
                Cursor indexCursor = db.query("data2", new String[]{"location"}, "id=?", new String[]{outkey}, null, null, null);
                if (indexCursor.moveToNext()){
                    //6,获取查询到的电话归属地
                    mAddress = indexCursor.getString(0);
                    Log.i(tag,"address ="+ mAddress);
                }
            } else {
                mAddress = "未知号码";
            }
        } else {
            int length = phone.length();
            switch (length){
                 case 3:  //119 110 120 114
                     mAddress = "报警电话";
                     break;
                case 4:
                    mAddress = "模拟器";
                    break;
                case 5://10086 99555
                    mAddress = "服务电话";
                    break;
                case 7:
                    mAddress = "本地电话";
                    break;
                case 8:
                    mAddress = "本地电话";
                    break;
                case 11:
                    //(3+8) 区号+座机号码(外地),查询data2
                    String area = phone.substring(1, 3);
                    Cursor cursor = db.query("data2", new String[]{"location"}, "area = ?", new String[]{area}, null, null, null);
                    if(cursor.moveToNext()){
                        mAddress = cursor.getString(0);
                    }else{
                        mAddress = "未知号码";
                    }
                    break;

                case 12:
                    //(4+8) 区号(0791(江西南昌))+座机号码(外地),查询data2
                    String area1 = phone.substring(1, 4);
                    Cursor cursor1 = db.query("data2", new String[]{"location"}, "area = ?", new String[]{area1}, null, null, null);
                    if(cursor1.moveToNext()){
                        mAddress = cursor1.getString(0);
                    }else{
                        mAddress = "未知号码";
                    }
                    break;

                 }

        }


        return mAddress;
    }

}
