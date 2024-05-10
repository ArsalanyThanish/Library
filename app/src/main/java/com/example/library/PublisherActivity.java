package com.example.library;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.text.BreakIterator;

public class PublisherActivity extends AppCompatActivity {

    EditText editTextName, editTextAddress, editTextPhone;
    Button btnCreate, btnRead, btnUpdate, btnDelete;
    DBHelper dbHelper;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.publisher_activity);

        editTextName = findViewById(R.id.publisherName);
        editTextAddress = findViewById(R.id.publisherAddress);
        editTextPhone = findViewById(R.id.publisherPhone);
        btnCreate = findViewById(R.id.btnCreate);
        btnRead = findViewById(R.id.btnRead);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);

        dbHelper = new DBHelper(this);

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPublisher();
            }
        });

        btnRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PublisherActivity.this, DisplayPublisherActivity.class);
                startActivity(intent);
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatePublisher();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deletePublisher();
            }
        });
    }

    private void addPublisher() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("NAME", editTextName.getText().toString());
        values.put("ADDRESS", editTextAddress.getText().toString());
        values.put("PHONE", editTextPhone.getText().toString());

        try {
            long newRowId = db.insertOrThrow("Publisher", null, values);

            if (newRowId == -1) {
                Toast.makeText(this, "Error adding publisher", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Publisher added successfully", Toast.LENGTH_SHORT).show();
                clearFields();
            }
        } catch (SQLiteConstraintException e) {
            // Handle the exception when UNIQUE constraint fails (publisher name already exists)
            Toast.makeText(this, "Publisher with the same name already exists", Toast.LENGTH_SHORT).show();
        }
    }


    private void updatePublisher() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("ADDRESS", editTextAddress.getText().toString());
        values.put("PHONE", editTextPhone.getText().toString());
        String selection = "NAME = ?";
        String[] selectionArgs = {editTextName.getText().toString()};
        int count = db.update(
                "Publisher",
                values,
                selection,
                selectionArgs);
        if (count == 0) {
            Toast.makeText(this, "No publisher found with the given name", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Publisher updated successfully", Toast.LENGTH_SHORT).show();
            clearFields();
        }
    }

    private void deletePublisher() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String selection = "NAME = ?";

        String[] selectionArgs = {editTextName.getText().toString()};
        int deletedRows = db.delete("Publisher", selection, selectionArgs);
        if (deletedRows == 0) {
            Toast.makeText(this, "No publisher found with the given name", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Publisher deleted successfully", Toast.LENGTH_SHORT).show();
            clearFields();
        }
    }

    private void clearFields() {
        editTextName.setText("");
        editTextAddress.setText("");
        editTextPhone.setText("");
    }
}
