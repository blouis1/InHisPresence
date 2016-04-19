package com.nearerToThee.model;

/**
 * Created by Betsy on 4/18/2016.
 */
public class Keyword {

    int id;
    String keyword_name;

    // constructors
    public Keyword() {

    }

    public Keyword(String tag_name) {
        this.keyword_name = tag_name;
    }

    public Keyword(int id, String tag_name) {
        this.id = id;
        this.keyword_name = tag_name;
    }

    // setter
    public void setId(int id) {
        this.id = id;
    }

    public void setKeywordName(String tag_name) {
        this.keyword_name = tag_name;
    }

    // getter
    public int getId() {
        return this.id;
    }

    public String getTagName() {
        return this.keyword_name;
    }
}
