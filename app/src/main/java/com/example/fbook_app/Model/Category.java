package com.example.fbook_app.Model;

public class Category {
    private String name;
    private int idCat,img;

    public Category() {
    }

    public Category(String name, int idCat, int img) {
        this.name = name;
        this.idCat = idCat;
        this.img = img;
    }

    public int getIdCat() {
        return idCat;
    }

    public void setIdCat(int idCat) {
        this.idCat = idCat;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
