package com.latihan.booklibrary;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.latihan.booklibrary.activity.AddEditDataActivity;
import com.latihan.booklibrary.adapter.ListAdapter;
import com.latihan.booklibrary.database.DatabaseHelper;
import com.latihan.booklibrary.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private DatabaseHelper db;
    ArrayList<String> bookTitle, bookAuthor, totalBook;
    private ListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        binding.fbAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddEditDataActivity.class);
                startActivity(intent);
            }
        });
        db = new DatabaseHelper(this);
        bookTitle = new ArrayList<>();
        bookAuthor = new ArrayList<>();
        totalBook = new ArrayList<>();
        displayData();
        setupAdapter();
    }

    private void setupAdapter() {
        adapter =  new ListAdapter(this, bookTitle, bookAuthor, totalBook);
        binding.rvList.setAdapter(adapter);
        binding.rvList.setLayoutManager(new LinearLayoutManager(MainActivity.this));
    }

    void displayData() {
        Cursor cursor = db.getAllBook();
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "There is no data", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                bookTitle.add(cursor.getString(0));
                bookAuthor.add(cursor.getString(1));
                totalBook.add(cursor.getString(2));
            }
        }
    }
}