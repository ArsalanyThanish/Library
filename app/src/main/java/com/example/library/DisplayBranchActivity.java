package com.example.library;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class DisplayBranchActivity extends AppCompatActivity {

    DBHelper dbHelper;
    TextView textViewBranchDetails;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_branch);

        dbHelper = new DBHelper(this);
        textViewBranchDetails = findViewById(R.id.textViewBranchDetails);

        // Retrieve all branch details from the database
        String branchDetails = getAllBranchDetails();

        // Display the retrieved details in the TextView
        textViewBranchDetails.setText(branchDetails);
    }

    private String getAllBranchDetails() {
        StringBuilder branchDetails = new StringBuilder();

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Branch", null);

        while (cursor.moveToNext()) {
            @SuppressLint("Range") String branchId = cursor.getString(cursor.getColumnIndex("BRANCH_ID"));
            @SuppressLint("Range") String branchName = cursor.getString(cursor.getColumnIndex("BRANCH_NAME"));
            @SuppressLint("Range") String branchAddress = cursor.getString(cursor.getColumnIndex("ADDRESS"));

            branchDetails.append("Branch ID: ").append(branchId).append("\n");
            branchDetails.append("Branch Name: ").append(branchName).append("\n");
            branchDetails.append("Address: ").append(branchAddress).append("\n\n");
        }

        cursor.close();
        return branchDetails.toString();
    }
}
