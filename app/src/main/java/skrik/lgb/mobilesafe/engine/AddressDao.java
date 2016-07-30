package skrik.lgb.mobilesafe.engine;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class AddressDao {
    //1,指定访问数据库的路径
    private static String path = "data/data/skrik.lgb.mobilesafe/files/address.db";
    private static final String tag = "AddressDao";

    /**传递一个电话号码,开启数据库连接,进行访问,返回一个归属地
     * @param phone	查询电话号码
     */
    public static String getAddress(String phone){
        phone=phone.substring(0,7);
        //2,开启数据库连接(只读的形式打开)
        SQLiteDatabase db = SQLiteDatabase.openDatabase(path,null,SQLiteDatabase.OPEN_READONLY);
        //3,数据库查询; "表名", new String[]{"字段1，字段2"}, "条件1=? and 条件2=?", new String[]{"条件1的值，条件2的值"},null,null,null
        Cursor cursor = db.query("data1", new String[]{"outkey"}, "id=?", new String[]{phone}, null, null, null);
        if (cursor.moveToNext()){
            String outkey = cursor.getString(0);
            Log.i(tag, "outkey = "+outkey);
        }


        return null;
    }

}
