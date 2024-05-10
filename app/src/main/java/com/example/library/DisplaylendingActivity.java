package com.example.library;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class DisplaylendingActivity extends AppCompatActivity {

    DBHelper dbHelper;
    TextView textViewBookLoanDetails;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_lending);

        dbHelper = new DBHelper(this);
        textViewBookLoanDetails = findViewById(R.id.textViewLendingDetails);

        // Retrieve all book loan details from the database
        String bookLoanDetails = getAllBookLoanDetails();

        // Display the retrieved details in the TextView
        textViewBookLoanDetails.setText(bookLoanDetails);
    }

    private String getAllBookLoanDetails() {
        StringBuilder bookLoanDetails = new StringBuilder();

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Book_Loan", null);

        while (cursor.moveToNext()) {
            @SuppressLint("Range") String accessNo = cursor.getString(cursor.getColumnIndex("ACCESS_NO"));
            @SuppressLint("Range") String branchId = cursor.getString(cursor.getColumnIndex("BRANCH_ID"));
            @SuppressLint("Range") String cardNo = cursor.getString(cursor.getColumnIndex("CARD_NO"));
            @SuppressLint("Range") String dateOut = cursor.getString(cursor.getColumnIndex("DATE_OUT"));
            @SuppressLint("Range") String dateDue = cursor.getString(cursor.getColumnIndex("DATE_DUE"));
            @SuppressLint("Range") String dateReturned = cursor.getString(cursor.getColumnIndex("DATE_RETURNED"));

            bookLoanDetails.append("Access No: ").append(accessNo).append("\n");
            bookLoanDetails.append("Branch ID: ").append(branchId).append("\n");
            bookLoanDetails.append("Card No: ").append(cardNo).append("\n");
            bookLoanDetails.append("Date Out: ").append(dateOut).append("\n");
            bookLoanDetails.append("Date Due: ").append(dateDue).append("\n");
            bookLoanDetails.append("Date Returned: ").append(dateReturned).append("\n\n");
        }

        cursor.close();
        return bookLoanDetails.toString();
    }
}
