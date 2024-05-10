package com.example.library;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity2);

        Button buttonBook = findViewById(R.id.button);
        Button buttonMember = findViewById(R.id.button2);
        Button buttonPublisher = findViewById(R.id.button3);
        Button buttonAuthor = findViewById(R.id.button4);
        Button buttonBookCopy = findViewById(R.id.button5);
        Button buttonLendingInfo = findViewById(R.id.button6);
        Button buttonBranch = findViewById(R.id.button7);

        buttonBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity2.this, BookActivity.class);
                startActivity(intent);
            }
        });

        buttonMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity2.this, MemberActivity.class);
                startActivity(intent);
            }
        });

        buttonPublisher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity2.this, PublisherActivity.class);
                startActivity(intent);
            }
        });

        buttonAuthor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity2.this, AuthorActivity.class);
                startActivity(intent);
            }
        });

        buttonBookCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity2.this, BookCopyActivity.class);
                startActivity(intent);
            }
        });

        buttonLendingInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity2.this, LendingActivity.class);
                startActivity(intent);
            }
        });

        buttonBranch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity2.this, BranchActivity.class);
                startActivity(intent);
            }
        });
    }
}
