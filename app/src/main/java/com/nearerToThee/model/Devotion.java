package com.nearerToThee.model;

import com.nearerToThee.utilities.AssetReader;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

/**
 * Created by Betsy on 4/15/2016.
 */
public class Devotion {
    private AssetReader reader;
    private String fileName;
    private String verse;
    private String devotion;

    public Devotion(AssetReader reader) {
        this.reader = reader;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * Returns the verse for the day
     * @return  Verse for the day
     */
    public String getTodaysVerse() throws IOException {
        this.fileName = this.reader.getRandomFile();
        // read from file to get verse
        this.verse = this.reader.readFromAssetsFile(this.fileName);
        return this.verse;
    }



}
