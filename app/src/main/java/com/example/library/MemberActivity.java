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

public class MemberActivity extends AppCompatActivity {

    EditText editTextCardNo, editTextName, editTextAddress, editTextPhone, editTextUnpaidDues;
    Button btnCreate, btnRead, btnUpdate, btnDelete;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.member_activity);

        editTextCardNo = findViewById(R.id.cardNo);
        editTextName = findViewById(R.id.name);
        editTextAddress = findViewById(R.id.address);
        editTextPhone = findViewById(R.id.phone);
        editTextUnpaidDues = findViewById(R.id.unpaidDues);
        btnCreate = findViewById(R.id.btnCreate);
        btnRead = findViewById(R.id.btnRead);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);

        dbHelper = new DBHelper(this);

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addMember();
            }
        });

        btnRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MemberActivity.this, DisplayMemberActivity.class);
                startActivity(intent);
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateMember();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteMember();
            }
        });
    }

    private void addMember() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("CARD_NO", editTextCardNo.getText().toString());
        values.put("NAME", editTextName.getText().toString());
        values.put("ADDRESS", editTextAddress.getText().toString());
        values.put("PHONE", editTextPhone.getText().toString());
        values.put("UNPAID_DUES", Double.parseDouble(editTextUnpaidDues.getText().toString()));
        long newRowId = db.insert("Member", null, values);
        if (newRowId == -1) {
            Toast.makeText(this, "Error adding member", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Member added successfully", Toast.LENGTH_SHORT).show();
            clearFields();
        }
    }

    private void updateMember() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("NAME", editTextName.getText().toString());
        values.put("ADDRESS", editTextAddress.getText().toString());
        values.put("PHONE", editTextPhone.getText().toString());
        values.put("UNPAID_DUES", Double.parseDouble(editTextUnpaidDues.getText().toString()));
        String selection = "CARD_NO = ?";
        String[] selectionArgs = {editTextCardNo.getText().toString()};
        int count = db.update(
                "Member",
                values,
                selection,
                selectionArgs);
        if (count == 0) {
            Toast.makeText(this, "No member found with the given card number", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Member updated successfully", Toast.LENGTH_SHORT).show();
            clearFields();
        }
    }

    private void deleteMember() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String selection = "CARD_NO = ?";
        String[] selectionArgs = {editTextCardNo.getText().toString()};
        int deletedRows = db.delete("Member", selection, selectionArgs);
        if (deletedRows == 0) {
            Toast.makeText(this, "No member found with the given card number", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Member deleted successfully", Toast.LENGTH_SHORT).show();
            clearFields();
        }
    }


    private void clearFields() {
        editTextCardNo.setText("");
        editTextName.setText("");
        editTextAddress.setText("");
        editTextPhone.setText("");
        editTextUnpaidDues.setText("");
    }
}
