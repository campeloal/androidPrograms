package com.example.alcampelo.viewtransitionanimation;

import android.widget.ImageView;

/**
 * Created by Al Campelo on 3/29/2015.
 */
public class Item {

    private String name;
    private int imageResource;

    public Item(String name, int imageResource) {
        this.name = name;
        this.imageResource = imageResource;
    }

    String getName() {
        return this.name;
    }

    int getImageResource()
    {
        return this.imageResource;
    }
}
