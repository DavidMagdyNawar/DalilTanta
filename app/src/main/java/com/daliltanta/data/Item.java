package com.daliltanta.data;

import java.util.List;

/**
 * Created by David on 10/16/2017.
 */

public class Item {
    final String item_approvaed = "false";
    String image;
    String item_privacy;
    String item_description;
    String item_categorie_id;
    String user_id;
    String item_request_id;
    List<String> shop_IDs;

    public Item() {
    }

    public Item(String image, String item_privacy, String item_description,
                String item_categorie_id, String user_id, String item_request_id, List<String> shop_IDs)
    {
        this.image = image;
        this.item_privacy = item_privacy;
        this.item_description = item_description;
        this.item_categorie_id = item_categorie_id;
        this.user_id = user_id;
        this.item_request_id = item_request_id;
        this.shop_IDs = shop_IDs;
    }

    public String getItem_approvaed() {
        return item_approvaed;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getItem_privacy() {
        return item_privacy;
    }

    public void setItem_privacy(String item_privacy) {
        this.item_privacy = item_privacy;
    }

    public String getItem_description() {
        return item_description;
    }

    public void setItem_description(String item_description) {
        this.item_description = item_description;
    }

    public String getItem_categorie_id() {
        return item_categorie_id;
    }

    public void setItem_categorie_id(String item_categorie_id) {
        this.item_categorie_id = item_categorie_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getItem_request_id() {
        return item_request_id;
    }

    public void setItem_request_id(String item_request_id) {
        this.item_request_id = item_request_id;
    }

    public List<String> getShop_IDs() {
        return shop_IDs;
    }

    public void setShop_IDs(List<String> shop_IDs) {
        this.shop_IDs = shop_IDs;
    }


}



