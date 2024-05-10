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

public class AuthorActivity extends AppCompatActivity {

    EditText editTextBookId, editTextAuthorName;
    Button btnCreate, btnRead, btnUpdate, btnDelete;
    DBHelper dbHelper;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.author_activity);

        editTextBookId = findViewById(R.id.bookId);
        editTextAuthorName = findViewById(R.id.authorName);
        btnCreate = findViewById(R.id.btnCreate);
        btnRead = findViewById(R.id.btnRead);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);

        dbHelper = new DBHelper(this);

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addAuthor();
            }
        });

        btnRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AuthorActivity.this, DisplayAuthorActivity.class);
                startActivity(intent);
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateAuthor();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteAuthor();
            }
        });
    }

    private void addAuthor() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("BOOK_ID", editTextBookId.getText().toString());
        values.put("AUTHOR_NAME", editTextAuthorName.getText().toString());

        long newRowId = db.insert("Book_Author", null, values);

        if (newRowId == -1) {
            Toast.makeText(this, "Error adding author", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Author added successfully", Toast.LENGTH_SHORT).show();
            clearFields();
        }
    }

    private void updateAuthor() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("BOOK_ID", editTextBookId.getText().toString());

        String selection = "AUTHOR_NAME = ?";
        String[] selectionArgs = {editTextAuthorName.getText().toString()};

        int count = db.update(
                "Book_Author",
                values,
                selection,
                selectionArgs);

        if (count == 0) {
            Toast.makeText(this, "No author found with the given name", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Author updated successfully", Toast.LENGTH_SHORT).show();
            clearFields();
        }
    }

    private void deleteAuthor() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String selection = "AUTHOR_NAME = ?";
        String[] selectionArgs = {editTextAuthorName.getText().toString()};

        int deletedRows = db.delete("Book_Author", selection, selectionArgs);

        if (deletedRows == 0) {
            Toast.makeText(this, "No author found with the given name", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Author deleted successfully", Toast.LENGTH_SHORT).show();
            clearFields();
        }
    }

    private void clearFields() {
        editTextBookId.setText("");
        editTextAuthorName.setText("");
    }
}
