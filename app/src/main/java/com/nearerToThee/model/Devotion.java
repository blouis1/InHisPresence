package com.nearerToThee.model;

import com.nearerToThee.utilities.AssetReader;
import com.nearerToThee.utilities.PatternMatcherGroupHtml;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Random;

/**
 * Created by Betsy on 4/15/2016.
 */
public class Devotion implements Serializable {
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
        //this.verse = this.reader.readFromAssetsFile(this.fileName);
        PatternMatcherGroupHtml patternMatcher = new PatternMatcherGroupHtml();
        this.verse = patternMatcher.getStringBetweenTags(this.reader.readFromAssetsFile(this.fileName), "<verse>(.+)</verse>");
        return this.verse;
    }

    public String getTodaysDevotion() throws IOException {
        this.fileName = this.getFileName();
        // read from file
        String fileText = this.reader.readFromAssetsFile(this.fileName);
        //this.verse = this.reader.readFromAssetsFile(this.fileName);
        PatternMatcherGroupHtml patternMatcher = new PatternMatcherGroupHtml();
        this.devotion = patternMatcher.getStringBetweenTags(fileText, "<html>(.+)</html>");
        return this.devotion;
    }





}
