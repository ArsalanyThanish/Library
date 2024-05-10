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

public class BookActivity extends AppCompatActivity {

    EditText editTextBookId, editTextTitle, editTextPublisher;
    Button btnCreate, btnRead, btnUpdate, btnDelete;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_activity);

        editTextBookId = findViewById(R.id.bookId);
        editTextTitle = findViewById(R.id.title);
        editTextPublisher = findViewById(R.id.publisherName);
        btnCreate = findViewById(R.id.btnCreate);
        btnRead = findViewById(R.id.btnRead);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);

        dbHelper = new DBHelper(this);

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addBook();
            }
        });

        btnRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BookActivity.this, DisplayBookActivity.class);
                startActivity(intent);
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateBook();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteBook();
            }
        });
    }

    private void addBook() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("BOOK_ID", editTextBookId.getText().toString());
        values.put("TITLE", editTextTitle.getText().toString());
        values.put("PUBLISHER_NAME", editTextPublisher.getText().toString());
        long newRowId = db.insert("Book", null, values);
        if (newRowId == -1) {
            Toast.makeText(this, "Error adding book", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Book added successfully", Toast.LENGTH_SHORT).show();
            clearFields();
        }
    }




    private void updateBook() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("TITLE", editTextTitle.getText().toString());
        values.put("PUBLISHER_NAME", editTextPublisher.getText().toString());
        String selection = "BOOK_ID = ?";
        String[] selectionArgs = {editTextBookId.getText().toString()};
        int count = db.update(
                "Book",
                values,
                selection,
                selectionArgs);
        if (count == 0) {
            Toast.makeText(this, "No book found with the given ID", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Book updated successfully", Toast.LENGTH_SHORT).show();
            clearFields();
        }
    }

    private void deleteBook() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String selection = "BOOK_ID = ?";
        String[] selectionArgs = {editTextBookId.getText().toString()};
        int deletedRows = db.delete("Book", selection, selectionArgs);
        if (deletedRows == 0) {
            Toast.makeText(this, "No book found with the given ID", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Book deleted successfully", Toast.LENGTH_SHORT).show();
            clearFields();
        }
    }

    private void clearFields() {
        editTextBookId.setText("");
        editTextTitle.setText("");
        editTextPublisher.setText("");
    }
}
