package com.example.library;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class BookCopyActivity extends AppCompatActivity {

    EditText editTextBookId, editTextBranchId, editTextAccessNo;
    Button btnCreate, btnRead, btnUpdate, btnDelete;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bookcopy_activity);

        editTextBookId = findViewById(R.id.editTextBookId);
        editTextBranchId = findViewById(R.id.editTextBranchId);
        editTextAccessNo = findViewById(R.id.editTextAccessNo);
        btnCreate = findViewById(R.id.btnCreate);
        btnRead = findViewById(R.id.btnRead);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);

        dbHelper = new DBHelper(this);

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addBookCopy();
            }
        });

        btnRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BookCopyActivity.this, DisplayBookCopyActivity.class);
                startActivity(intent);
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateBookCopy();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteBookCopy();
            }
        });
    }

    private void addBookCopy() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("BOOK_ID", editTextBookId.getText().toString());
        values.put("BRANCH_ID", editTextBranchId.getText().toString());
        values.put("ACCESS_NO", editTextAccessNo.getText().toString());

        long newRowId = db.insert("Book_Copy", null, values);

        if (newRowId == -1) {
            Toast.makeText(this, "Error adding book copy", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Book copy added successfully", Toast.LENGTH_SHORT).show();
            clearFields();
        }
    }

        private void updateBookCopy() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("BRANCH_ID", editTextBranchId.getText().toString());
        String selection = "BOOK_ID = ? AND ACCESS_NO = ?";
        String[] selectionArgs = {editTextBookId.getText().toString(), editTextAccessNo.getText().toString()};
        int count = db.update(
                "Book_Copy",
                values,
                selection,
                selectionArgs);
        if (count == 0) {
            Toast.makeText(this, "No book copy found with the given details", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Book copy updated successfully", Toast.LENGTH_SHORT).show();
            clearFields();
        }
    }

    private void deleteBookCopy() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String selection = "BOOK_ID = ? AND ACCESS_NO = ?";
        String[] selectionArgs = {editTextBookId.getText().toString(), editTextAccessNo.getText().toString()};
        int deletedRows = db.delete("Book_Copy", selection, selectionArgs);
        if (deletedRows == 0) {
            Toast.makeText(this, "No book copy found with the given details", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Book copy deleted successfully", Toast.LENGTH_SHORT).show();
            clearFields();
        }
    }

    private void clearFields() {
        editTextBookId.setText("");
        editTextBranchId.setText("");
        editTextAccessNo.setText("");
    }
}
