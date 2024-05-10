package com.example.library;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class DisplayMemberActivity extends AppCompatActivity {

    DBHelper dbHelper;
    TextView textViewMemberDetails;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_member);

        dbHelper = new DBHelper(this);
        textViewMemberDetails = findViewById(R.id.textViewMemberDetails);

        // Retrieve all member details from the database
        String memberDetails = getAllMemberDetails();

        // Display the retrieved details in the TextView
        textViewMemberDetails.setText(memberDetails);
    }

    private String getAllMemberDetails() {
        StringBuilder memberDetails = new StringBuilder();

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Member", null);

        while (cursor.moveToNext()) {
            @SuppressLint("Range") String cardNo = cursor.getString(cursor.getColumnIndex("CARD_NO"));
            @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("NAME"));
            @SuppressLint("Range") String address = cursor.getString(cursor.getColumnIndex("ADDRESS"));
            @SuppressLint("Range") String phone = cursor.getString(cursor.getColumnIndex("PHONE"));
            @SuppressLint("Range") double unpaidDues = cursor.getDouble(cursor.getColumnIndex("UNPAID_DUES"));

            memberDetails.append("Card No: ").append(cardNo).append("\n");
            memberDetails.append("Name: ").append(name).append("\n");
            memberDetails.append("Address: ").append(address).append("\n");
            memberDetails.append("Phone: ").append(phone).append("\n");
            memberDetails.append("Unpaid Dues: ").append(unpaidDues).append("\n\n");
        }

        cursor.close();
        return memberDetails.toString();
    }
}
