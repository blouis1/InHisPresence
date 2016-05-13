package com.inHisPresence.utilityTests;

import android.test.ActivityInstrumentationTestCase2;

import com.inHisPresence.utilities.AssetReader;
import com.inHisPresence.view.MainActivity;

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
        assertEquals("File count incorrect", 2, reader.getFilesFromAssets("testFiles").size());
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
        assertEquals(expectedText.length(), readerText.length());
    }

}
