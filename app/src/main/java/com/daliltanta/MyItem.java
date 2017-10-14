package com.daliltanta;

/**
 * Created by David on 8/5/2017.
 */

public class MyItem {

    String item_description;
    String item_isPrivate;
    String item_Image;
    String item_categorie;


    public MyItem(String comment, String isPrivate, String image, String item_categorie) {
        this.item_description = comment;
        this.item_isPrivate = isPrivate;
        this.item_Image = image;
        this.item_categorie = item_categorie;
    }

    public String getItem_description() {
        return item_description;
    }

    public void setItem_description(String item_description) {
        this.item_description = item_description;
    }

    public String isItem_isPrivate() {
        return item_isPrivate;
    }

    public void setItem_isPrivate(String item_isPrivate) {
        this.item_isPrivate = item_isPrivate;
    }

    public String getItem_Image() {
        return item_Image;
    }

    public void setItem_Image(String item_Image) {
        this.item_Image = item_Image;
    }

    public String getItem_categorie() {
        return item_categorie;
    }

    public void setItem_categorie(String item_categorie) {
        this.item_categorie = item_categorie;
    }
}