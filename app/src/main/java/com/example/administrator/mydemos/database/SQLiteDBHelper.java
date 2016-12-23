package com.example.administrator.mydemos.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.administrator.mydemos.model.Students;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/5/17.
 */
public class SQLiteDBHelper extends SQLiteOpenHelper {

    public static final int DB_VERSION = 1;
    public static final String DB_NAME = "database.db";
    public static final String TABLE_STUDENTS = "students";
    private SQLiteDatabase mSqLiteDatabase;

    private static final String SQL_CREAT_TABLE_STUDENTS = "CREATE TABLE "
            + TABLE_STUDENTS
            + " ("
            + "id integer PRIMARY KEY AUTOINCREMENT, "
            + "name vachar(20) not null "
            + ");";

    public SQLiteDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        mSqLiteDatabase = this.getWritableDatabase();
    }

    public SQLiteDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREAT_TABLE_STUDENTS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public List<Students> queryStudents() {
        Cursor cursor = mSqLiteDatabase.query(TABLE_STUDENTS, null, null, null, null, null, null);
        List<Students> studentsList = new ArrayList<>(cursor.getCount());
        while (cursor.moveToNext()) {
            Students students = new Students();
            students.id = cursor.getInt(0);
            students.name = cursor.getString(cursor.getColumnIndex("name"));
            studentsList.add(students);
        }
        cursor.close();
        return studentsList;
    }

    public void insertStudents() {
        for (int i = 0; i <5; i++) {
            mSqLiteDatabase.insert(TABLE_STUDENTS, null, students2Values(i));
        }
    }

    private ContentValues students2Values(int i) {
        Students students = new Students();
        students.id = i;
        students.name = "stu " + i;
        ContentValues values = new ContentValues();
        values.put("id", students.id);
        values.put("name", students.name);
        return values;
    }
}
