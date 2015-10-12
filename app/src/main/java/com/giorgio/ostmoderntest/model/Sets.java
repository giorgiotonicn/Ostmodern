package com.giorgio.ostmoderntest.model;

import org.json.JSONArray;

/**
 * Created by Giorgio on 10/10/15.
 */
public class Sets {

    String title;
    String body;
    int filmCount;
    JSONArray items;
    JSONArray photoArray;
    String imageUrl;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public int getFilmCount() {
        return filmCount;
    }

    public void setFilmCount(int filmCount) {
        this.filmCount = filmCount;
    }

    public JSONArray getItems() {
        return items;
    }

    public void setItems(JSONArray items) {
        this.items = items;
    }

    public JSONArray getPhotoArray() {
        return photoArray;
    }

    public void setPhotoArray(JSONArray photo_array) {
        this.photoArray = photo_array;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
