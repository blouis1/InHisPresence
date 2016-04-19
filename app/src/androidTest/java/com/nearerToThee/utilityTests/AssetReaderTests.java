package com.nearerToThee.utilityTests;

import android.test.ActivityInstrumentationTestCase2;

import com.nearerToThee.utilities.AssetReader;
import com.nearerToThee.view.MainActivity;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Tests the AssetReader class.
 * Created by Betsy on 4/15/2016.
 */
public class AssetReaderTests extends ActivityInstrumentationTestCase2<MainActivity> {

    private AssetReader reader;
    private MainActivity mainActivity;

    public AssetReaderTests() {
        super(MainActivity.class);
    }

    @Override
    protected void setUp() {
        mainActivity = getActivity();
        reader = new AssetReader(mainActivity.getApplicationContext());
    }

    public void testNumberOfFilesInAssetsFolder() throws IOException {
        assertEquals("File count incorrect", 2, reader.getFilesFromAssets("devotions").size());
    }

    public void testGetFilesFromAssetsSubFolder() throws IOException {
        ArrayList<String> fileList = new ArrayList<String>();
        fileList.add("file01.txt");
        fileList.add("unitTest.txt");
        assertEquals(fileList, reader.getFilesFromAssets("testFiles"));
    }

    public void testReadFromAssetsFile() throws IOException {
        String expectedText = "This is text for unit testing ";
        String readerText = reader.readFromAssetsFile("unitTest.txt", "testFiles");
        assertTrue(readerText.length() == (expectedText.length()));
    }

    // test the random generator gets a random file each time
    public void testGetRandomFile() throws IOException {
        DeterministicRandom randomGenerator = new DeterministicRandom();
        assertTrue(reader.getRandomFile(randomGenerator, "testFiles").equals("file01.txt"));
        assertTrue(reader.getRandomFile(randomGenerator, "testFiles").equals("unitTest.txt"));
    }

}
