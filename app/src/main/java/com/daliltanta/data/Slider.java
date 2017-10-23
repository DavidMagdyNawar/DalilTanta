package com.daliltanta.data;

/**
 * Created by David on 10/23/2017.
 */

public class Slider {
    String slider_item_image;
    String slider_item_id;

    public Slider(String slider_item_image, String slider_item_id) {
        this.slider_item_image = slider_item_image;
        this.slider_item_id = slider_item_id;
    }

    public String getSlider_item_image() {
        return slider_item_image;
    }

    public void setSlider_item_image(String slider_item_image) {
        this.slider_item_image = slider_item_image;
    }

    public String getSlider_item_id() {
        return slider_item_id;
    }

    public void setSlider_item_id(String slider_item_id) {
        this.slider_item_id = slider_item_id;
    }
}
