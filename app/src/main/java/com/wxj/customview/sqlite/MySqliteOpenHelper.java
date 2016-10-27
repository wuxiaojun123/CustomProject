package com.wxj.customview.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by wuxiaojun on 16-9-14.
 */
public class MySqliteOpenHelper extends SQLiteOpenHelper {
    //数据库名称
    public static final String SQLITE_NAME = "test.db";
    //当前数据库的版本
    public static final int SQLITE_VERSION = 1;
    //表名
    public static final String SQLITE_TABLE_NAME = "student";
    //创建表的语句
    public static final String CREATE_TABLE = "create table " + SQLITE_TABLE_NAME + "(_id INTEGER PRIMARY KEY AUTOINCREMENT,name verchar(10),age integer)";

    public MySqliteOpenHelper(Context context) {
        super(context, SQLITE_NAME, null, SQLITE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建数据库
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //数据库版本升级的时候使用
    }

}
