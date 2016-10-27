package com.wxj.customview.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.wxj.customview.utils.LogUtils;
import com.wxj.customview.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wuxiaojun on 16-9-14.
 */
public class SqliteManager {

    public static final String SQL_INSERT = "insert into " + MySqliteOpenHelper.SQLITE_TABLE_NAME + "(name,age) values(?,?)";
    public static final String SQL_SELECT = "select * from " + MySqliteOpenHelper.SQLITE_TABLE_NAME;
    public static final String SQL_UPDATE = "update " + MySqliteOpenHelper.SQLITE_TABLE_NAME + " set name where age > ?";

    private Context mContext;

    public SqliteManager(Context context) {
        this.mContext = context;
    }

    /***
     * 插入单条数据(第一种方式)
     *
     * @param sqlitedatabase
     * @param student
     * @return
     */
    public long insert(SQLiteDatabase sqlitedatabase, Student student) {
        ContentValues mContentValues = new ContentValues();
        mContentValues.put(Student.NAME, student.name);
        mContentValues.put(Student.AGE, student.age);
        return sqlitedatabase.insert(MySqliteOpenHelper.SQLITE_TABLE_NAME, null, mContentValues);
    }

    /***
     * 批量插入数据(第二种方式)
     *
     * @param sqliteDatabase
     * @param list
     */
    public void insert(SQLiteDatabase sqliteDatabase, List<Student> list) {
        long currentTimeMillis = System.currentTimeMillis();
        try {
            //开启事物
            sqliteDatabase.beginTransaction();
            for (Student student : list) {
                sqliteDatabase.execSQL(SQL_INSERT, new Object[]{student.name, student.age});
            }
            //设置事物成功
            sqliteDatabase.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sqliteDatabase.endTransaction();
            long timeMillis = System.currentTimeMillis();
            ToastUtils.showToast(mContext, "批量插入1000条数据成功,耗时" + (timeMillis - currentTimeMillis) + "毫秒");
        }
    }

    /***
     * 查询数据库
     *
     * @param sqliteDatabase
     * @return
     */
    public List<Student> query(SQLiteDatabase sqliteDatabase) {
        List<Student> list = new ArrayList<>();
        Cursor cursor = sqliteDatabase.rawQuery(SQL_SELECT, null);
        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex("name"));
            int age = cursor.getInt(cursor.getColumnIndex("age"));
            int id = cursor.getInt(cursor.getColumnIndex("_id"));
            list.add(new Student(id, name, age));
        }
        if (!cursor.isClosed()) {
            cursor.close();
        }
        return list;
    }

    /***
     * 更新语句
     *
     * @param sqLiteDatabase
     * @param values
     * @param whereClause
     * @param whereArgs
     */
    public int update(SQLiteDatabase sqLiteDatabase, ContentValues values, String whereClause, String[] whereArgs) {
        return sqLiteDatabase.update(MySqliteOpenHelper.SQLITE_TABLE_NAME, values, whereClause, whereArgs);
    }

    /***
     * 删除数据
     *
     * @param sqLiteDatabase
     * @param whereClause
     * @param whereArgs
     * @return 返回结果表示删除了多少条数据
     */
    public int delete(SQLiteDatabase sqLiteDatabase, String whereClause, String[] whereArgs) {
        return sqLiteDatabase.delete(MySqliteOpenHelper.SQLITE_TABLE_NAME, whereClause, whereArgs);
    }

}
