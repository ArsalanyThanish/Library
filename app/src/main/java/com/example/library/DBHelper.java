package com.example.library;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DBNAME = "Library.db";

    public DBHelper(@Nullable Context context) {
        super(context, "Library.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase MyDB) {
        // Create "users" table
        MyDB.execSQL("create Table users(username TEXT primary key, password TEXT)");

        MyDB.execSQL("CREATE TABLE Book(BOOK_ID VARCHAR(13) PRIMARY KEY, TITLE VARCHAR(30), PUBLISHER_NAME VARCHAR(20))");

        // Create "Member" table
        MyDB.execSQL("CREATE TABLE Member(CARD_NO VARCHAR(10) PRIMARY KEY, NAME VARCHAR(20), ADDRESS VARCHAR(30), PHONE VARCHAR(10), UNPAID_DUES NUMBER(5,2))");

        // Create "Branch" table
        MyDB.execSQL("CREATE TABLE Branch(BRANCH_ID VARCHAR(5) PRIMARY KEY, BRANCH_NAME VARCHAR(20), ADDRESS VARCHAR(30))");

        // Create "Publisher" table
        MyDB.execSQL("CREATE TABLE Publisher(NAME VARCHAR(20) PRIMARY KEY, ADDRESS VARCHAR(30), PHONE VARCHAR(10))");

        // Create "Book_Author" table
        MyDB.execSQL("CREATE TABLE Book_Author(BOOK_ID VARCHAR(13), AUTHOR_NAME VARCHAR(20), PRIMARY KEY(BOOK_ID, AUTHOR_NAME), FOREIGN KEY(BOOK_ID) REFERENCES Book(BOOK_ID))");

        // Create "Book_Copy" table
        MyDB.execSQL("CREATE TABLE Book_Copy(BOOK_ID VARCHAR(13), BRANCH_ID VARCHAR(5), ACCESS_NO VARCHAR(5), PRIMARY KEY(ACCESS_NO, BRANCH_ID), FOREIGN KEY(BOOK_ID) REFERENCES Book(BOOK_ID), FOREIGN KEY(BRANCH_ID) REFERENCES Branch(BRANCH_ID))");

        // Create "Book_Loan" table
        MyDB.execSQL("CREATE TABLE Book_Loan(ACCESS_NO VARCHAR(5), BRANCH_ID VARCHAR(5), CARD_NO VARCHAR(5), DATE_OUT DATE, DATE_DUE DATE, DATE_RETURNED DATE, PRIMARY KEY(ACCESS_NO, BRANCH_ID, CARD_NO, DATE_OUT), FOREIGN KEY(ACCESS_NO, BRANCH_ID) REFERENCES Book_Copy(ACCESS_NO, BRANCH_ID), FOREIGN KEY(CARD_NO) REFERENCES Member(CARD_NO), FOREIGN KEY(BRANCH_ID) REFERENCES Branch(BRANCH_ID))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase MyDB, int i, int i1) {
        MyDB.execSQL("DROP TABLE IF EXISTS users");
        MyDB.execSQL("DROP TABLE IF EXISTS Book");
        MyDB.execSQL("DROP TABLE IF EXISTS Member");
        MyDB.execSQL("DROP TABLE IF EXISTS Branch");
        MyDB.execSQL("DROP TABLE IF EXISTS Publisher");
        MyDB.execSQL("DROP TABLE IF EXISTS Book_Author");
        MyDB.execSQL("DROP TABLE IF EXISTS Book_Copy");
        MyDB.execSQL("DROP TABLE IF EXISTS Book_Loan");
        onCreate(MyDB);
    }

    public Boolean insertData(String username, String password) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username);
        contentValues.put("password", password);
        long result = MyDB.insert("users", null, contentValues);
        if (result == -1) return false;
        else
            return true;
    }

    public Boolean checkusername(String username) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from users where username = ?", new String[]{username});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }

    public Boolean checkusernamepassword(String username, String password) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from users where username = ? and password = ?", new String[]{username, password});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }



}
