package com.daliltanta.data;

import java.util.ArrayList;

/**
 * Created by David on 11/22/2017.
 */

public class Shop {
String shopID
        ,shopName
        ,shopImage
        ,shopAddress
        ,shopPhone
        ,shopFB
        ,shopWorkHours,
        shopApproved;
        ArrayList<String> categoriesRelated;
        ArrayList<String> ItemsIn ;

    public Shop() {
    }

    public String getShopID() {
        return shopID;
    }

    public void setShopID(String shopID) {
        this.shopID = shopID;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopImage() {
        return shopImage;
    }

    public void setShopImage(String shopImage) {
        this.shopImage = shopImage;
    }

    public String getShopAddress() {
        return shopAddress;
    }

    public void setShopAddress(String shopAddress) {
        this.shopAddress = shopAddress;
    }

    public String getShopPhone() {
        return shopPhone;
    }

    public void setShopPhone(String shopPhone) {
        this.shopPhone = shopPhone;
    }

    public String getShopFB() {
        return shopFB;
    }

    public void setShopFB(String shopFB) {
        this.shopFB = shopFB;
    }

    public String getShopWorkHours() {
        return shopWorkHours;
    }

    public void setShopWorkHours(String shopWorkHours) {
        this.shopWorkHours = shopWorkHours;
    }

    public ArrayList<String> getCategoriesRelated() {
        return categoriesRelated;
    }

    public void setCategoriesRelated(ArrayList<String> categoriesRelated) {
        this.categoriesRelated = categoriesRelated;
    }

    public ArrayList<String> getItemsIn() {
        return ItemsIn;
    }

    public void setItemsIn(ArrayList<String> itemsIn) {
        ItemsIn = itemsIn;
    }

    public String getShopApproved() {
        return shopApproved;
    }

    public void setShopApproved(String shopApproved) {
        this.shopApproved = shopApproved;
    }
}
