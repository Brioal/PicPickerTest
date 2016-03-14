package com.brioal.picpickertest.base;

import java.io.Serializable;

/**
 * Created by brioal on 16-3-12.
 */
public class ImageItem implements Serializable{
    private String uri ;
    private boolean isChecked;
    private int id ;

    public ImageItem() {

    }
    public ImageItem(String uri, boolean isChecked, int id) {
        this.uri = uri;
        this.isChecked = isChecked;
        this.id = id;
    }

    public String getUri() {
        return uri;
    }

    public ImageItem setUri(String uri) {
        this.uri = uri;
        return this;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public ImageItem setChecked(boolean checked) {

        isChecked = checked;
        return this;
    }

    public int getId() {
        return id;
    }

    public ImageItem setId(int id) {

        this.id = id;
        return this;
    }
}
