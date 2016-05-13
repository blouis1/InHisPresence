package com.inHisPresence.model;

import com.inHisPresence.utilities.PatternMatcherGroupHtml;

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
        String title = patternMatcher.getStringBetweenTags(fileContent, "<h1>(.+)</h1>");
        String verse = patternMatcher.getStringBetweenTags(fileContent, "<aside>(.+)</aside>");
        this.verse = "<h1>" + title + "</h1>" + verse;
        return this.verse;
    }

    public String getReading() {
        this.devotion = patternMatcher.getStringBetweenTags(fileContent, "<html>(.+)</html>");
        return this.devotion;
    }

}
