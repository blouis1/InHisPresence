package com.nearerToThee.model;

/**
 * Created by Betsy on 4/18/2016.
 */
public class File {

    int id;
    String fileName;
    String fileTitle;
    boolean isFavorite;

    // constructors
    public File() {
    }

    public File(String fileName, String fileTitle, boolean isFavorite) {
        this.fileName = fileName;
        this.fileTitle = fileTitle;
        this.isFavorite = isFavorite;
    }

    public File(int id, String fileName, boolean isFavorite) {
        this.id = id;
        this.fileName = fileName;
        this.isFavorite = isFavorite;
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

    // getters
    public long getId() {
        return this.id;
    }

    public String getFileName() {
        return this.fileName;
    }

    public String getFileTitle() {
        return this.fileTitle;
    }

    public boolean getIsFavorite() {
        return this.isFavorite;
    }
}
