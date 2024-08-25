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
import com.latihan.booklibrary.databinding.ActivityAddEditDataBinding;

public class AddEditDataActivity extends AppCompatActivity {

    private ActivityAddEditDataBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityAddEditDataBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        addBook();
    }

    private void addBook() {
        BookDataModel book = new BookDataModel();
        DatabaseHelper db = new DatabaseHelper(this);
        binding.btnAddData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String bookTitle = binding.etBookTitle.getText().toString();
                String bookAuthor = binding.etBookAuthor.getText().toString();
                String totalBook = binding.etTotalBook.getText().toString();
                book.setBookTitle(bookTitle).setBookAuthor(bookAuthor).setTotalBook(totalBook);
                if (bookTitle.isEmpty() || bookAuthor.isEmpty() || totalBook.isEmpty()) {
                    Toast.makeText(AddEditDataActivity.this, "Please fill out all form", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(AddEditDataActivity.this, MainActivity.class);
                    db.addBook(book);
                    startActivity(intent);
                    Toast.makeText(AddEditDataActivity.this, "Add Book Success", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }
}