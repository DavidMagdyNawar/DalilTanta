package com.daliltanta.data;

import java.util.HashMap;
import java.lang.String;

/**
 * Created by David on 10/16/2017.
 */

public class SubItem {
    private String subItemImage;//
    private String subItem_approved;//
    private String subItem_description;//
    private String subItem_id;//subItem_id is the categorie ID
    private String subItem_privacy;//
    private String subItem_type;//
    private String subItem_request_id;//subItem_request_id is the item ID


    private String subItem_user_id;//

    //

    private HashMap<String,String> subItem_shop_IDs;//

    public SubItem() {
    }

    public String getSubItem_type() {
        return subItem_type;
    }

    public void setSubItem_type(String subItem_type) {
        this.subItem_type = subItem_type;
    }

    public String getSubItem_approved() {
        return subItem_approved;
    }

    public void setSubItem_approved(String subItem_approved) {
        this.subItem_approved = subItem_approved;
    }

    public String getSubItemImage() {
        return subItemImage;
    }

    public void setSubItemImage(String subItemImage) {
        this.subItemImage = subItemImage;
    }

    public String getSubItem_privacy() {
        return subItem_privacy;
    }

    public void setSubItem_privacy(String subItem_privacy) {
        this.subItem_privacy = subItem_privacy;
    }

    public String getSubItem_description() {
        return subItem_description;
    }

    public void setSubItem_description(String subItem_description) {
        this.subItem_description = subItem_description;
    }

    public String getSubItem_id() {
        return subItem_id;
    }

    public void setSubItem_id(String subItem_id) {
        this.subItem_id = subItem_id;
    }

    public String getSubItem_user_id() {
        return subItem_user_id;
    }

    public void setSubItem_user_id(String subItem_user_id) {
        this.subItem_user_id = subItem_user_id;
    }

    public String getSubItem_request_id() {
        return subItem_request_id;
    }

    public void setSubItem_request_id(String subItem_request_id) {
        this.subItem_request_id = subItem_request_id;
    }

    public HashMap<String, String> getSubItem_shop_IDs() {
        return subItem_shop_IDs;
    }

    public void setSubItem_shop_IDs(HashMap<String, String> subItem_shop_IDs) {
        this.subItem_shop_IDs = subItem_shop_IDs;
    }
}



