package com.latihan.booklibrary.database;

public class BookDataModel {
    private String bookTitle;
    private String bookAuthor;
    private String totalBook;

    public String getBookTitle() {
        return bookTitle;
    }

    public BookDataModel setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
        return this;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public BookDataModel setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
        return this;
    }

    public String getTotalBook() {
        return totalBook;
    }

    public BookDataModel setTotalBook(String totalBook) {
        this.totalBook = totalBook;
        return this;
    }
}
