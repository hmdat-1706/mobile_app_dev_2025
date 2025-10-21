package com.example.minhdat_23520249_lab3_studentmanagement;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    //Tên và phiên bản của Database
    public static final String DATABASE_NAME = "StudentManagement.db";
    public static final int DATABASE_VERSION = 1;

    //Tên bảng và các cột
    public static final String TABLE_STUDENTS = "students";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_STUDENT_ID = "student_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_CLASS_NAME = "class_name";

    //Câu lệnh tạo bảng
    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_STUDENTS + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_STUDENT_ID + " TEXT NOT NULL, " +
                    COLUMN_NAME + " TEXT NOT NULL, " +
                    COLUMN_EMAIL + " TEXT, " +
                    COLUMN_CLASS_NAME + " TEXT" +
                    ");";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Thực thi câu lệnh tạo bảng khi database được tạo lần đầu
        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Xóa bảng cũ nếu tồn tại và tạo lại
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STUDENTS);
        onCreate(db);
    }
}
    