package com.example.library;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class DisplayBookCopyActivity extends AppCompatActivity {

    DBHelper dbHelper;
    TextView textViewBookCopyDetails;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_bookcopy);

        dbHelper = new DBHelper(this);
        textViewBookCopyDetails = findViewById(R.id.textViewBookCopyDetails);

        // Retrieve all book copy details from the database
        String bookCopyDetails = getAllBookCopyDetails();

        // Display the retrieved details in the TextView
        textViewBookCopyDetails.setText(bookCopyDetails);
    }

    private String getAllBookCopyDetails() {
        StringBuilder bookCopyDetails = new StringBuilder();

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Book_Copy", null);

        while (cursor.moveToNext()) {
            @SuppressLint("Range") String bookId = cursor.getString(cursor.getColumnIndex("BOOK_ID"));
            @SuppressLint("Range") String branchId = cursor.getString(cursor.getColumnIndex("BRANCH_ID"));
            @SuppressLint("Range") String accessNo = cursor.getString(cursor.getColumnIndex("ACCESS_NO"));

            bookCopyDetails.append("Book ID: ").append(bookId).append("\n");
            bookCopyDetails.append("Branch ID: ").append(branchId).append("\n");
            bookCopyDetails.append("Access No: ").append(accessNo).append("\n\n");
        }

        cursor.close();
        return bookCopyDetails.toString();
    }
}
