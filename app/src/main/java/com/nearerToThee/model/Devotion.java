package com.nearerToThee.model;

import com.nearerToThee.utilities.PatternMatcherGroupHtml;

/**
 * Created by Betsy on 4/15/2016.
 */
public class Devotion {

    private String fileContent;
    private String verse;
    private String devotion;
    private PatternMatcherGroupHtml patternMatcher;

    public Devotion(String fileContent) {
        if (fileContent == null) {
            throw new IllegalArgumentException("Invalid file content");
        }
        patternMatcher = new PatternMatcherGroupHtml();
        this.fileContent = fileContent;
    }

    public String getFileContent()
    {
        return this.fileContent;
    }

    /**
     * Returns the verse for the day
     * @return  Verse for the day
     */
    public String getVerse() {
        // read from file to get verse
        this.verse = patternMatcher.getStringBetweenTags(fileContent, "<aside>(.+)</aside>");
        return this.verse;
    }

    public String getReading() {
        this.devotion = patternMatcher.getStringBetweenTags(fileContent, "<html>(.+)</html>");
        return this.devotion;
    }

}
