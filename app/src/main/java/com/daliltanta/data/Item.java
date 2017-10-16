package com.daliltanta.data;

/**
 * Created by David on 10/16/2017.
 */

public class Item {

    String item_privacy;
    String item_details;
    final String item_approvaed = "false";
    String ite_category;
    String item_imageURI;

    public Item(String item_privacy, String item_details, String ite_category, String item_imageURI) {

        this.item_privacy = item_privacy;
        this.item_details = item_details;
        this.ite_category = ite_category;
        this.item_imageURI = item_imageURI;
    }

    public String getItem_details() {
        return item_details;
    }

    public void setItem_details(String item_details) {
        this.item_details = item_details;
    }

    public String getItem_approvaed() {
        return item_approvaed;
    }

    public String getIte_category() {
        return ite_category;
    }

    public void setIte_category(String ite_category) {
        this.ite_category = ite_category;
    }

    public String getItem_imageURI() {
        return item_imageURI;
    }

    public void setItem_imageURI(String item_imageURI) {
        this.item_imageURI = item_imageURI;
    }


    public String getItem_privacy() {
        return item_privacy;
    }

    public void setItem_privacy(String item_privacy) {
        this.item_privacy = item_privacy;
    }


}
