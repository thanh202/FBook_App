package com.example.fbook_app.Model;

public class Cart {
    private int idBook;
    private String bookName;
    private String description;
    private String priceBook;
    private int imageBook;

    public Cart(int idBook, String bookName, String description, String priceBook, int imageBook) {
        this.idBook = idBook;
        this.bookName = bookName;
        this.description = description;
        this.priceBook = priceBook;
        this.imageBook = imageBook;
    }

    public Cart() {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPriceBook() {
        return priceBook;
    }

    public void setPriceBook(String priceBook) {
        this.priceBook = priceBook;
    }

    public int getImageBook() {
        return imageBook;
    }

    public void setImageBook(int imageBook) {
        this.imageBook = imageBook;
    }
}
