package simpleadapter.stx.com.simpleadapter.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "person.db";
    private static final int DATABASE_VERSION = 1;

    public DBHelper(Context context) {
        //CursorFactory����Ϊnull,ʹ��Ĭ��ֵ
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //���ݿ��һ�α�����ʱonCreate�ᱻ����
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS person" +
                "(_id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR, phone VARCHAR, info TEXT)");
        db.execSQL("INSERT INTO person VALUES(null, ?, ?, ?)", new Object[]{"����", "18875050386", ""});
        db.execSQL("INSERT INTO person VALUES(null, ?, ?, ?)", new Object[]{"Ф��", "18888888888", ""});
        db.execSQL("INSERT INTO person VALUES(null, ?, ?, ?)", new Object[]{"̷��Ů", "18888888888", ""});
        db.execSQL("INSERT INTO person VALUES(null, ?, ?, ?)", new Object[]{"�ɻ�", "18888888888", ""});
        db.execSQL("INSERT INTO person VALUES(null, ?, ?, ?)", new Object[]{"�����", "18888888888", ""});
        db.execSQL("INSERT INTO person VALUES(null, ?, ?, ?)", new Object[]{"��˧��", "18888888888", ""});


//        db.execSQL("INSERT INTO person VALUES(null, ?, ?, ?)", new Object[]{"Ф��", "18502339836", ""});
//        db.execSQL("INSERT INTO person VALUES(null, ?, ?, ?)", new Object[]{"̷��Ů", "15334513069", ""});
//        db.execSQL("INSERT INTO person VALUES(null, ?, ?, ?)", new Object[]{"�ɻ�", "18723549123", ""});
//        db.execSQL("INSERT INTO person VALUES(null, ?, ?, ?)", new Object[]{"�����", "18883339682", ""});
//        db.execSQL("INSERT INTO person VALUES(null, ?, ?, ?)", new Object[]{"��˧��", "13310237646", ""});


//        db.execSQL("INSERT INTO person VALUES(null, ?, ?, ?)", new Object[]{"������ʦ ����", "15223188957", ""});
//        db.execSQL("INSERT INTO person VALUES(null, ?, ?, ?)", new Object[]{"������ �����", "15223265570", ""});
//        db.execSQL("INSERT INTO person VALUES(null, ?, ?, ?)", new Object[]{"����½ս���鳤 �ų�", "18875050386", ""});
//        db.execSQL("INSERT INTO person VALUES(null, ?, ?, ?)", new Object[]{"�����������鳤 ������", "13996616085", ""});
//        db.execSQL("INSERT INTO person VALUES(null, ?, ?, ?)", new Object[]{"ĳ�����鳤 ̷����", "13310232090", ""});
//        db.execSQL("INSERT INTO person VALUES(null, ?, ?, ?)", new Object[]{"SixGod�鳤 ����", "18623198412", ""});
//        db.execSQL("INSERT INTO person VALUES(null, ?, ?, ?)", new Object[]{"����Plus�鳤 ����", "13310237974", ""});
//        db.execSQL("INSERT INTO person VALUES(null, ?, ?, ?)", new Object[]{"�������� ϯ͢", "18996214773", ""});
//        db.execSQL("INSERT INTO person VALUES(null, ?, ?, ?)", new Object[]{"������Ա ������", "15223314255", ""});
//        db.execSQL("INSERT INTO person VALUES(null, ?, ?, ?)", new Object[]{"������Ա ��ɭ", "15223263305", ""});

    }

    //���DATABASE_VERSIONֵ����Ϊ2,ϵͳ�����������ݿ�汾��ͬ,�������onUpgrade
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("ALTER TABLE person ADD COLUMN other STRING");
    }
}