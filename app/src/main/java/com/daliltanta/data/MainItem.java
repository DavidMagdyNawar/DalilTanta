package com.daliltanta.data;

import java.io.Serializable;

/**
 * Created by David on 11/18/2017.
 */

public class MainItem implements Serializable {
    String mainItemType;


    public MainItem() {

    }

    public String getMainItemType() {
        return mainItemType;
    }
    public void setMainItemType(String mainItemType) {
        this.mainItemType = mainItemType;
    }



}
