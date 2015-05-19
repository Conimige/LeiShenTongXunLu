package simpleadapter.stx.com.simpleadapter.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "person.db";
    private static final int DATABASE_VERSION = 2;

    public DBHelper(Context context) {
        //CursorFactory����Ϊnull,ʹ��Ĭ��ֵ
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //���ݿ��һ�α�����ʱonCreate�ᱻ����
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS person" +
                "(_id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR, phone VARCHAR, info TEXT,sex VARCHAR)");

        //����ӵ�е��û����ݿ�
        db.execSQL("CREATE TABLE IF NOT EXISTS user" +
                "(_id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR, password VARCHAR)");
        db.execSQL("INSERT INTO user VALUES(null, ?, ?)", new Object[]{"chaoshen", "123456"});

        //���ر����¼���û���Ϣ
        db.execSQL("CREATE TABLE IF NOT EXISTS localuser" +
                "(_id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR, password VARCHAR,phone VARCHAR)");
    }

    //���DATABASE_VERSIONֵ����Ϊ2,ϵͳ�����������ݿ�汾��ͬ,�������onUpgrade
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("CREATE TABLE IF NOT EXISTS localuser" +
                "(_id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR, password VARCHAR,phone VARCHAR)");

    }
}