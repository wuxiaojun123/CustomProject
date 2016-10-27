package com.wxj.customview.sqlite;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.wxj.customview.R;
import com.wxj.customview.utils.LogUtils;
import com.wxj.customview.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by wuxiaojun on 16-9-14.
 */
public class SqliteActivity extends Activity implements View.OnClickListener {

    @BindView(R.id.btn_insert_one)
    Button btn_insert_one;
    @BindView(R.id.btn_insert_trascation)
    Button btn_insert_trascation;
    @BindView(R.id.btn_update)
    Button btn_update;
    @BindView(R.id.btn_query)
    Button btn_query;
    @BindView(R.id.btn_delete)
    Button btn_delete;
    @BindView(R.id.tv_content)
    TextView tv_content;

    private Context mContext;
    private MySqliteOpenHelper mSqliteOpenHelper;
    private SQLiteDatabase mWritableDatabase;
    private SQLiteDatabase mReadableDatabae;

    private SqliteManager mManger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlite);
        ButterKnife.bind(this);
        mContext = getApplicationContext();
        mSqliteOpenHelper = new MySqliteOpenHelper(mContext);
        mWritableDatabase = mSqliteOpenHelper.getWritableDatabase();
        mReadableDatabae = mSqliteOpenHelper.getReadableDatabase();
        mManger = new SqliteManager(mContext);
    }

    @OnClick({R.id.btn_insert_one, R.id.btn_query, R.id.btn_update, R.id.btn_delete, R.id.btn_insert_trascation})
    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btn_insert_one:
                //点击插入数据,  这里特意插入一条字符串长度大于10的，就是为了证明sqlite是一个弱类型
                long insertResult = mManger.insert(mWritableDatabase, new Student(1, "asdfadsfasdfasdfasdfasdfasdfasdfasdfasdfafd;lkad；离开；是地方阿斯顿附近卡是地方阿斯顿发生地方", 38));
                if (insertResult != -1) {
                    ToastUtils.showToast(mContext, "插入数据成功");
                } else {
                    ToastUtils.showToast(mContext, "插入数据失败");
                }
                break;
            case R.id.btn_insert_trascation:
                //批量插入数据
                List<Student> mList = new ArrayList<>();
                for (int i = 0; i < 1000; i++) {
                    mList.add(new Student("哈哈哈" + i, i));
                }
                mManger.insert(mWritableDatabase, mList);

                break;
            case R.id.btn_update:
                //更新数据
                ContentValues values = new ContentValues();
                values.put(Student.NAME, "呵呵呵");
                String whereClause = " _id > ? ";
                String[] whereArgs = new String[]{"1000"};
                int updateResult = mManger.update(mWritableDatabase, values, whereClause, whereArgs);
                if (updateResult > 0) {
                    ToastUtils.showToast(mContext, "更新" + updateResult + "条数据成功");
                }

                break;
            case R.id.btn_query:
                //查询数据
                List<Student> list = mManger.query(mReadableDatabae);
                StringBuilder result = new StringBuilder();
                for (Student student : list) {
                    result.append("id=" + student.id + "==name=" + student.name + "==age=" + student.age + "\n");
                }
                tv_content.setText(result.toString());

                break;
            case R.id.btn_delete:
                //删除数据
                int deleteResult = mManger.delete(mWritableDatabase, Student.NAME + " like ?", new String[]{"哈哈哈%"});
                if (deleteResult > 0) {
                    ToastUtils.showToast(mContext, "删除" + deleteResult + "条数据成功");
                }
                break;
        }
    }


}
