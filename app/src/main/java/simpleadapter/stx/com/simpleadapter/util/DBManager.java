package simpleadapter.stx.com.simpleadapter.util;

/**
 * Created by chao on 2015/4/20.
 */
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DBManager {
    private DBHelper helper;
    private SQLiteDatabase db;

    public DBManager(Context context) {
        helper = new DBHelper(context);
        //��ΪgetWritableDatabase�ڲ�������mContext.openOrCreateDatabase(mName, 0, mFactory);
        //����Ҫȷ��context�ѳ�ʼ��,���ǿ��԰�ʵ����DBManager�Ĳ������Activity��onCreate��
        db = helper.getWritableDatabase();
    }

    /**
     * add persons
     * @param persons
     */
    public void addPerson(List<Person> persons) {
        db.beginTransaction();  //��ʼ����
        try {
            for (Person person : persons) {
                db.execSQL("INSERT INTO person VALUES(null, ?, ?, ?)", new Object[]{person.name, person.phone, person.info});
            }
            db.setTransactionSuccessful();  //��������ɹ����
        } finally {
            db.endTransaction();    //��������
        }
    }

    /**
     * update person's age
     * @param person
     */
    public void updatePhone(Person person) {
        ContentValues cv = new ContentValues();
        cv.put("phone", person.phone);
        db.update("person", cv, "name = ?", new String[]{person.name});
    }

    /**
     * delete old person
     * @param person
     */
    public void delete(Person person) {
        db.delete("person", "phone = ?", new String[]{String.valueOf(person.phone)});
    }

    /**
     * query all persons, return list
     * @return List<Person>
     */
    public List<Person> query() {
        ArrayList<Person> persons = new ArrayList<Person>();
        Cursor c = queryTheCursor();
        while (c.moveToNext()) {
            Person person = new Person();
            person._id = c.getInt(c.getColumnIndex("_id"));
            person.name = c.getString(c.getColumnIndex("name"));
            person.phone = c.getString(c.getColumnIndex("phone"));
            person.info = c.getString(c.getColumnIndex("info"));
            persons.add(person);
        }
        c.close();
        return persons;
    }

    /**
     * query all persons, return cursor
     * @return  Cursor
     */
    public Cursor queryTheCursor() {
        Cursor c = db.rawQuery("SELECT * FROM person", null);
        return c;
    }

    public String queryDB(String strDatabase, String findColumn, String strWhere) {
        Cursor c = db.rawQuery("SELECT * FROM " + strDatabase + " " + strWhere, null);
        String resultStr = "";
        try {
            while (c.moveToNext()) {
                resultStr = c.getString(c.getColumnIndex(findColumn));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            c.close();
        }

        return resultStr;
    }

    public void insertDB(String strDatabase, String values) {
        db.beginTransaction();  //��ʼ����
        db.execSQL("INSERT INTO " + strDatabase + " VALUES(" + values + ")");
        db.setTransactionSuccessful();
        db.endTransaction();
    }

    /**
     * close database
     */
    public void closeDB() {
        db.close();
    }

    public void InitializeChaoShenDB() {
        db.beginTransaction();  //��ʼ����
        db.execSQL("INSERT INTO person VALUES(null, ?, ?, ?)", new Object[]{"����", "18875050386", ""});
        db.execSQL("INSERT INTO person VALUES(null, ?, ?, ?)", new Object[]{"Ф��", "18502339836", ""});
        db.execSQL("INSERT INTO person VALUES(null, ?, ?, ?)", new Object[]{"̷��Ů", "15334513069", ""});
        db.execSQL("INSERT INTO person VALUES(null, ?, ?, ?)", new Object[]{"�ɻ�", "18723549123", ""});
        db.execSQL("INSERT INTO person VALUES(null, ?, ?, ?)", new Object[]{"�����", "18883339682", ""});
        db.execSQL("INSERT INTO person VALUES(null, ?, ?, ?)", new Object[]{"��˧��", "13310237646", ""});

        db.setTransactionSuccessful();
        db.endTransaction();
    }
}