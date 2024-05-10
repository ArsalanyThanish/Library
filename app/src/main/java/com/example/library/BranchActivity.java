package com.example.library;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class BranchActivity extends AppCompatActivity {

    EditText editTextBranchId, editTextBranchName, editTextBranchAddress;
    Button btnCreate, btnRead, btnUpdate, btnDelete;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.branch_activity);

        editTextBranchId = findViewById(R.id.branchId);
        editTextBranchName = findViewById(R.id.branchName);
        editTextBranchAddress = findViewById(R.id.branchAddress);
        btnCreate = findViewById(R.id.btnCreate);
        btnRead = findViewById(R.id.btnRead);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);

        dbHelper = new DBHelper(this);

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addBranch();
            }
        });
        btnRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BranchActivity.this, DisplayBranchActivity.class);
                startActivity(intent);
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateBranch();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteBranch();
            }
        });
    }

    private void addBranch() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("BRANCH_ID", editTextBranchId.getText().toString());
        values.put("BRANCH_NAME", editTextBranchName.getText().toString());
        values.put("ADDRESS", editTextBranchAddress.getText().toString());
        long newRowId = db.insert("Branch", null, values);
        if (newRowId == -1) {
            Toast.makeText(this, "Error adding branch", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Branch added successfully", Toast.LENGTH_SHORT).show();
            clearFields();
        }
    }

    private void updateBranch() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("BRANCH_NAME", editTextBranchName.getText().toString());
        values.put("ADDRESS", editTextBranchAddress.getText().toString());
        String selection = "BRANCH_ID = ?";
        String[] selectionArgs = {editTextBranchId.getText().toString()};
        int count = db.update(
                "Branch",
                values,
                selection,
                selectionArgs);
        if (count == 0) {
            Toast.makeText(this, "No branch found with the given ID", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Branch updated successfully", Toast.LENGTH_SHORT).show();
            clearFields();
        }
    }

    private void deleteBranch() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String selection = "BRANCH_ID = ?";
        String[] selectionArgs = {editTextBranchId.getText().toString()};
        int deletedRows = db.delete("Branch", selection, selectionArgs);
        if (deletedRows == 0) {
            Toast.makeText(this, "No branch found with the given ID", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Branch deleted successfully", Toast.LENGTH_SHORT).show();
            clearFields();
        }
    }


    private void clearFields() {
        editTextBranchId.setText("");
        editTextBranchName.setText("");
        editTextBranchAddress.setText("");
    }
}
