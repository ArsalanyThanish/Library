package com.example.library;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class DisplayBookActivity extends AppCompatActivity {

    DBHelper dbHelper;
    TextView textViewBookDetails;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_book);

        dbHelper = new DBHelper(this);
        textViewBookDetails = findViewById(R.id.textViewBookDetails);

        // Retrieve all book details from the database
        String bookDetails = getAllBookDetails();

        // Display the retrieved details in the TextView
        textViewBookDetails.setText(bookDetails);
    }

    private String getAllBookDetails() {
        StringBuilder bookDetails = new StringBuilder();

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Book", null);

        while (cursor.moveToNext()) {
            @SuppressLint("Range") String bookId = cursor.getString(cursor.getColumnIndex("BOOK_ID"));
            @SuppressLint("Range") String title = cursor.getString(cursor.getColumnIndex("TITLE"));
            @SuppressLint("Range") String publisherName = cursor.getString(cursor.getColumnIndex("PUBLISHER_NAME"));

            bookDetails.append("Book ID: ").append(bookId).append("\n");
            bookDetails.append("Title: ").append(title).append("\n");
            bookDetails.append("Publisher Name: ").append(publisherName).append("\n\n");
        }

        cursor.close();
        return bookDetails.toString();
    }
}
