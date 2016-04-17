package com.nearerToThee;

import com.nearerToThee.model.Devotion;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tests the Devotion class methods.
 * Created by Betsy on 4/17/2016.
 */
public class DevotionTests {

    private Devotion devotion;
    String fileContent = "<verse>This is the verse.</verse><html>This is the devotion.</html>";

    public DevotionTests() {
        devotion = new Devotion("unitTest.txt");
    }

    @Test
    public void testConstructorConstructsObjectWithGivenFileName() {
        assertTrue(devotion.getFileName().equals("unitTest.txt"));
    }

    @Test
    public void testCorrectVerseIsRetrieved() {
        assertTrue(devotion.getTodaysVerse(fileContent).equals("This is the verse."));
    }

    @Test
    public void testConstructorCotructsObjectWithGivenFileName() {
        assertTrue(devotion.getTodaysDevotion(fileContent).equals("This is the devotion."));
    }
}