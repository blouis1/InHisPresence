package com.nearerToThee.model;

import com.nearerToThee.utilities.AssetReader;
import com.nearerToThee.utilities.PatternMatcherGroupHtml;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Betsy on 4/15/2016.
 */
public class Devotion {

    private String fileName;
    private String verse;
    private String devotion;
    private PatternMatcherGroupHtml patternMatcher;

    public Devotion(String fileName) {
        if (fileName == null) {
            throw new IllegalArgumentException("Invalid file");
        }
        patternMatcher = new PatternMatcherGroupHtml();
        this.fileName = fileName;
    }

    public String getFileName()
    {
        return this.fileName;
    }

    /**
     * Returns the verse for the day
     * @return  Verse for the day
     */
    public String getTodaysVerse(String fileContent) {
        // read from file to get verse
        this.verse = patternMatcher.getStringBetweenTags(fileContent, "<verse>(.+)</verse>");
        return this.verse;
    }

    public String getTodaysDevotion(String fileContent) {
        this.devotion = patternMatcher.getStringBetweenTags(fileContent, "<html>(.+)</html>");
        return this.devotion;
    }





}
