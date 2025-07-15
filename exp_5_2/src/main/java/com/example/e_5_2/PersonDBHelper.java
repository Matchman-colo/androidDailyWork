package com.example.e_5_2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class PersonDBHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "person.db";
    public static final String TABLE_NAME = "person";

    public PersonDBHelper(Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABLE_NAME + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT," +
                "age INTEGER," +
                "height REAL," +
                "weight REAL," +
                "married INTEGER)";
        db.execSQL(sql);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // 如果数据库版本升级，可以在这里执行 SQL 来修改表结构
        db.execSQL("DROP TABLE IF EXISTS person");
        onCreate(db);
    }

}
