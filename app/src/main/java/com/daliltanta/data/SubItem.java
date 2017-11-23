package com.daliltanta.data;

import java.util.HashMap;
import java.lang.String;

/**
 * Created by David on 10/16/2017.
 */

public class SubItem {
    private String SubItem_approved = "false";
    private String SubItemImage;
    private String SubItem_privacy;
    private String SubItem_description;
    //SubItem_id is the categorie ID
    private String SubItem_id;
    private String SubItem_user_id;
    //SubItem_request_id is the item ID
    private String SubItem_request_id;
    private HashMap<String,String> SubItem_shop_IDs;

    public SubItem() {
    }

    public String getSubItem_approved() {
        return SubItem_approved;
    }

    public void setSubItem_approved(String subItem_approved) {
        SubItem_approved = subItem_approved;
    }

    public String getSubItemImage() {
        return SubItemImage;
    }

    public void setSubItemImage(String subItemImage) {
        SubItemImage = subItemImage;
    }

    public String getSubItem_privacy() {
        return SubItem_privacy;
    }

    public void setSubItem_privacy(String subItem_privacy) {
        SubItem_privacy = subItem_privacy;
    }

    public String getSubItem_description() {
        return SubItem_description;
    }

    public void setSubItem_description(String subItem_description) {
        SubItem_description = subItem_description;
    }

    public String getSubItem_id() {
        return SubItem_id;
    }

    public void setSubItem_id(String subItem_id) {
        SubItem_id = subItem_id;
    }

    public String getSubItem_user_id() {
        return SubItem_user_id;
    }

    public void setSubItem_user_id(String subItem_user_id) {
        SubItem_user_id = subItem_user_id;
    }

    public String getSubItem_request_id() {
        return SubItem_request_id;
    }

    public void setSubItem_request_id(String subItem_request_id) {
        SubItem_request_id = subItem_request_id;
    }

    public HashMap<String, String> getSubItem_shop_IDs() {
        return SubItem_shop_IDs;
    }

    public void setSubItem_shop_IDs(HashMap<String, String> subItem_shop_IDs) {
        SubItem_shop_IDs = subItem_shop_IDs;
    }
}



