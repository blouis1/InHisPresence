package com.inHisPresence.model;

/**
 * Created by Betsy on 4/18/2016.
 */
public class File {

    int id;
    String fileName;
    String fileTitle;
    boolean isFavorite;
    int fileTagId;

    // constructors
    public File() {
    }

    // setters
    public void setId(int id) {
        this.id = id;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setFileTitle(String fileTitle) {
        this.fileTitle = fileTitle;
    }

    public void setIsFavorite(boolean isFavorite) {
        this.isFavorite = isFavorite;
    }

    public void setFileTagId(int tag_id) {
        this.fileTagId = tag_id;
    }


    // getters
    public long getId() {
        return this.id;
    }

    public String getFileName() {
        return this.fileName;
    }

    public String getFileTitle() { return this.fileTitle; }

    public boolean getIsFavorite() {
        return this.isFavorite;
    }

    public int getFileTagId() {
        return this.fileTagId;
    }
}
