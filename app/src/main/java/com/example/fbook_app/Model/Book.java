package com.example.fbook_app.Model;

import java.io.Serializable;

public class Book implements Serializable {
    private int idBook;
    private String bookName;
    private String author;
    private String publishYear;
    private String priceBook;
    private String description;
    private int imageBook;
    private int chapter;
    private String typeBook;

    public Book(int idBook, String bookName, String author, String publishYear, String priceBook, String description, int imageBook, int chapter, String typeBook) {
        this.idBook = idBook;
        this.bookName = bookName;
        this.author = author;
        this.publishYear = publishYear;
        this.priceBook = priceBook;
        this.description = description;
        this.imageBook = imageBook;
        this.chapter = chapter;
        this.typeBook = typeBook;
    }

    public int getChapter() {
        return chapter;
    }

    public void setChapter(int chapter) {
        this.chapter = chapter;
    }

    public String getTypeBook() {
        return typeBook;
    }

    public void setTypeBook(String typeBook) {
        this.typeBook = typeBook;
    }

    public int getIdBook() {
        return idBook;
    }

    public void setIdBook(int idBook) {
        this.idBook = idBook;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublishYear() {
        return publishYear;
    }

    public void setPublishYear(String publishYear) {
        this.publishYear = publishYear;
    }

    public String getPriceBook() {
        return priceBook;
    }

    public void setPriceBook(String priceBook) {
        this.priceBook = priceBook;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getImageBook() {
        return imageBook;
    }

    public void setImageBook(int imageBook) {
        this.imageBook = imageBook;
    }
}
