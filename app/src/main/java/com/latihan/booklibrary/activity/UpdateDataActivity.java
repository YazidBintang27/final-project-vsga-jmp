package com.latihan.booklibrary.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.latihan.booklibrary.MainActivity;
import com.latihan.booklibrary.R;
import com.latihan.booklibrary.database.BookDataModel;
import com.latihan.booklibrary.database.DatabaseHelper;
import com.latihan.booklibrary.databinding.ActivityUpdateDataBinding;

public class UpdateDataActivity extends AppCompatActivity {

    private ActivityUpdateDataBinding binding;
    DatabaseHelper db = new DatabaseHelper(this);
    String title, author, total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityUpdateDataBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        binding.btnUpdateData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateBook();
            }
        });
        binding.btnDeleteData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.deleteBook(title);
                Intent intent = new Intent(UpdateDataActivity.this, MainActivity.class);
                Toast.makeText(UpdateDataActivity.this, "Delete Data Success", Toast.LENGTH_SHORT).show();
                startActivity(intent);
                finish();
            }
        });
        getIntentData();
    }

    private void getIntentData() {
        if (getIntent().hasExtra("title") && getIntent().hasExtra("author") && getIntent().hasExtra("total")) {
            title = getIntent().getStringExtra("title");
            author = getIntent().getStringExtra("author");
            total = getIntent().getStringExtra("total");

            binding.etBookTitle.setText(title);
            binding.etBookAuthor.setText(author);
            binding.etTotalBook.setText(total);
        } else {
            Toast.makeText(this, "No data available", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateBook() {
        BookDataModel book = new BookDataModel();
        String title = binding.etBookTitle.getText().toString();
        String author = binding.etBookAuthor.getText().toString();
        String total = binding.etTotalBook.getText().toString();
        book.setBookTitle(title).setBookAuthor(author).setTotalBook(total);
        if (title.isEmpty() || author.isEmpty() || total.isEmpty()) {
            Toast.makeText(UpdateDataActivity.this, "Please fill out all form", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(UpdateDataActivity.this, MainActivity.class);
            db.updateBook(this.title, book);
            startActivity(intent);
            Toast.makeText(UpdateDataActivity.this, "Update Success", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}