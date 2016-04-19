package com.nearerToThee.model;

/**
 * Created by Betsy on 4/18/2016.
 */
public class Tag {

    int id;
    String tag_name;

    // constructors
    public Tag() {

    }

    // setter
    public void setId(int id) {
        this.id = id;
    }

    public void setTagName(String tag_name) {
        this.tag_name = tag_name;
    }

    // getter
    public int getId() {
        return this.id;
    }

    public String getTagName() {
        return this.tag_name;
    }
}
