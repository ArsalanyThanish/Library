package com.example.library;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class DisplayPublisherActivity extends AppCompatActivity {

    DBHelper dbHelper;
    TextView textViewPublisherDetails;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_publisher);

        dbHelper = new DBHelper(this);
        textViewPublisherDetails = findViewById(R.id.textViewPublisherDetails);

        // Retrieve all publisher details from the database
        String publisherDetails = getAllPublisherDetails();

        // Display the retrieved details in the TextView
        textViewPublisherDetails.setText(publisherDetails);
    }

    private String getAllPublisherDetails() {
        StringBuilder publisherDetails = new StringBuilder();

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Publisher", null);

        while (cursor.moveToNext()) {
            @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("NAME"));
            @SuppressLint("Range") String address = cursor.getString(cursor.getColumnIndex("ADDRESS"));
            @SuppressLint("Range") String phone = cursor.getString(cursor.getColumnIndex("PHONE"));

            publisherDetails.append("Name: ").append(name).append("\n");
            publisherDetails.append("Address: ").append(address).append("\n");
            publisherDetails.append("Phone: ").append(phone).append("\n\n");
        }

        cursor.close();
        return publisherDetails.toString();
    }
}
