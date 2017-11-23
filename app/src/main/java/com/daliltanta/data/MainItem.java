package com.daliltanta.data;

import java.io.Serializable;

/**
 * Created by David on 11/18/2017.
 */

public class MainItem implements Serializable {
    String mainItemImage;
    String mainItemType;

    public String getMainItemID() {
        return mainItemID;
    }

    public void setMainItemID(String mainItemID) {
        this.mainItemID = mainItemID;
    }

    String mainItemID;

    public MainItem() {

    }



    public String getMainItemImage() {
        return mainItemImage;
    }

    public void setMainItemImage(String mainItemImage) {
        this.mainItemImage = mainItemImage;
    }

    public String getMainItemType() {
        return mainItemType;
    }

    public void setMainItemType(String mainItemType) {
        this.mainItemType = mainItemType;
    }
}
