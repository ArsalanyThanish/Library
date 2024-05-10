package com.example.library;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class LendingActivity extends AppCompatActivity {

    EditText editTextLendingId, editTextBranchId, editTextBookId, editTextMemberId, editTextDateOut, editTextDateDue, editTextDateReturned;
    Button btnCreate, btnRead, btnUpdate, btnDelete;
    DBHelper dbHelper;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lending_activity);

        editTextLendingId = findViewById(R.id.lendingId);
        editTextBranchId = findViewById(R.id.branchId);
        editTextBookId = findViewById(R.id.bookId);
        editTextMemberId = findViewById(R.id.memberId);
        editTextDateOut = findViewById(R.id.dateOut);
        editTextDateDue = findViewById(R.id.dateDue);
        editTextDateReturned = findViewById(R.id.dateReturned);
        btnCreate = findViewById(R.id.btnCreate);
        btnRead = findViewById(R.id.btnRead);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);

        dbHelper = new DBHelper(this);

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addLending();
            }
        });

        btnRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LendingActivity.this, DisplaylendingActivity.class);
                startActivity(intent);
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateLending();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteLending();
            }
        });
    }

    private void addLending() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("ACCESS_NO", editTextBookId.getText().toString());
        values.put("BRANCH_ID", editTextBranchId.getText().toString());
        values.put("CARD_NO", editTextMemberId.getText().toString());
        values.put("DATE_OUT", editTextDateOut.getText().toString());
        values.put("DATE_DUE", editTextDateDue.getText().toString());
        values.put("DATE_RETURNED", editTextDateReturned.getText().toString());

        long newRowId = db.insert("Book_Loan", null, values);

        if (newRowId == -1) {
            Toast.makeText(this, "Error adding lending details", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Lending details added successfully", Toast.LENGTH_SHORT).show();
            clearFields();
        }
    }

    private void updateLending() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("DATE_RETURNED", editTextDateReturned.getText().toString());

        String selection = "ACCESS_NO = ? AND BRANCH_ID = ? AND CARD_NO = ?";
        String[] selectionArgs = {editTextBookId.getText().toString(), editTextBranchId.getText().toString(), editTextMemberId.getText().toString()};

        int count = db.update(
                "Book_Loan",
                values,
                selection,
                selectionArgs);
        if (count == 0) {
            Toast.makeText(this, "No lending found with the given details", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Lending details updated successfully", Toast.LENGTH_SHORT).show();
            clearFields();
        }
    }

    private void deleteLending() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String selection = "ACCESS_NO = ? AND BRANCH_ID = ? AND CARD_NO = ?";

        String[] selectionArgs = {editTextBookId.getText().toString(), editTextBranchId.getText().toString(), editTextMemberId.getText().toString()};
        int deletedRows = db.delete("Book_Loan", selection, selectionArgs);
        if (deletedRows == 0) {
            Toast.makeText(this, "No lending found with the given details", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Lending details deleted successfully", Toast.LENGTH_SHORT).show();
            clearFields();
        }
    }


    private void clearFields() {
        editTextLendingId.setText("");
        editTextBranchId.setText("");
        editTextBookId.setText("");
        editTextMemberId.setText("");
        editTextDateOut.setText("");
        editTextDateDue.setText("");
        editTextDateReturned.setText("");
    }
}
