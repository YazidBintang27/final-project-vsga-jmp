package com.latihan.booklibrary.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "library.db";
    private static final int DATABASE_VERSION = 3;

    //Table name for users
    private static final String TABLE_USER = "users";
    // Users table column names
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_PASSWORD = "password";

    //Table name for book
    private static final String TABLE_BOOK = "books";
    // Books table column names
    private static final String COLUMN_BOOK_TITLE = "book_title";
    private static final String COLUMN_BOOK_AUTHOR = "book_author";
    private static final String COLUMN_TOTAL_BOOK = "total_book";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String userTable = "CREATE TABLE " + TABLE_USER + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_USERNAME + " TEXT, " +
                COLUMN_EMAIL + " TEXT UNIQUE, " +
                COLUMN_PASSWORD + " TEXT)";
        db.execSQL(userTable);

        String bookTable = "CREATE TABLE " + TABLE_BOOK + "(" +
                COLUMN_BOOK_TITLE + " TEXT, " +
                COLUMN_BOOK_AUTHOR + " TEXT, " +
                COLUMN_TOTAL_BOOK + " TEXT)";
        db.execSQL(bookTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BOOK);
        onCreate(db);
    }

    public boolean addUser(UserDataModel user) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_USERNAME, user.getUsername());
        contentValues.put(COLUMN_EMAIL, user.getEmail());
        contentValues.put(COLUMN_PASSWORD, user.getPassword());
        long result =  db.insert(TABLE_USER, null, contentValues);
        if (result == -1) {
            Log.e("DatabaseHelper", "Error inserting data for user: " + user.getEmail());
            return false; // Signifies failure
        } else {
            Log.i("DatabaseHelper", "User added successfully: " + user.getEmail());
            return true; // Signifies success
        }
    }

    @SuppressLint("Recycle")
    public boolean isEmailAvailable(String email) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USER + " WHERE " + COLUMN_EMAIL + " = ?", new String[]{email});
        boolean result = cursor.getCount() > 0;
        cursor.close();
        return result;
    }

    @SuppressLint("Recycle")
    public boolean isUserAvailable(String email, String password) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USER + " WHERE " + COLUMN_EMAIL + " = ? AND " + COLUMN_PASSWORD + " = ?", new String[]{email, password});
        boolean result = cursor.getCount() > 0;
        cursor.close();
        return result;
    }

    public boolean addBook(BookDataModel book) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_BOOK_TITLE, book.getBookTitle());
        contentValues.put(COLUMN_BOOK_AUTHOR, book.getBookAuthor());
        contentValues.put(COLUMN_TOTAL_BOOK, book.getTotalBook());
        long result = db.insert(TABLE_BOOK, null, contentValues);
        if (result == -1) {
            Log.e("DatabaseHelper", "Error inserting data for user: " + book.getBookTitle());
            return false; // Signifies failure
        } else {
            Log.i("DatabaseHelper", "User added successfully: " + book.getBookTitle());
            return true; // Signifies success
        }
    }

    public Cursor getAllBook() {
        String query = "SELECT * FROM " + TABLE_BOOK;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    public boolean updateBook(String oldTitle, BookDataModel book) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_BOOK_TITLE, book.getBookTitle());
        contentValues.put(COLUMN_BOOK_AUTHOR, book.getBookAuthor());
        contentValues.put(COLUMN_TOTAL_BOOK, book.getTotalBook());
        int result = db.update(TABLE_BOOK, contentValues, COLUMN_BOOK_TITLE + " = ?", new String[]{oldTitle});
        if (result == 0) {
            Log.e("DatabaseHelper", "Error updating book: " + book.getBookTitle());
            return false; // Signifies failure
        } else {
            Log.i("DatabaseHelper", "Book updated successfully: " + book.getBookTitle());
            return true; // Signifies success
        }
    }

    public boolean deleteBook(String bookTitle) {
        SQLiteDatabase db = getWritableDatabase();
        int result = db.delete(TABLE_BOOK, COLUMN_BOOK_TITLE + " = ?", new String[]{bookTitle});
        if (result == 0) {
            Log.e("DatabaseHelper", "Error deleting book: " + bookTitle);
            return false; // Signifies failure
        } else {
            Log.i("DatabaseHelper", "Book deleted successfully: " + bookTitle);
            return true; // Signifies success
        }
    }
}
