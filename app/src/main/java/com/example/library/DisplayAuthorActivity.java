package com.example.library;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class DisplayAuthorActivity extends AppCompatActivity {

    DBHelper dbHelper;
    TextView textViewAuthorDetails;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_author);

        dbHelper = new DBHelper(this);
        textViewAuthorDetails = findViewById(R.id.textViewAuthorDetails);

        // Retrieve all author details from the database
        String authorDetails = getAllAuthorDetails();

        // Display the retrieved details in the TextView
        textViewAuthorDetails.setText(authorDetails);
    }

    private String getAllAuthorDetails() {
        StringBuilder authorDetails = new StringBuilder();

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Book_Author", null);

        while (cursor.moveToNext()) {
            @SuppressLint("Range") String bookId = cursor.getString(cursor.getColumnIndex("BOOK_ID"));
            @SuppressLint("Range") String authorName = cursor.getString(cursor.getColumnIndex("AUTHOR_NAME"));

            authorDetails.append("Book ID: ").append(bookId).append("\n");
            authorDetails.append("Author Name: ").append(authorName).append("\n\n");
        }

        cursor.close();
        return authorDetails.toString();
    }
}
