package com.nearerToThee.utilities;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Class with methods to read files from Assets folders.
 * Created by Betsy on 4/15/2016.
 */
public class AssetReader {
    private Context mContext;
    private Random randomGenerator;

    public AssetReader(Context context) {
        mContext = context;
        randomGenerator = new Random();
    }

    /**
     * Constructor that accepts seed for random number generation.
     * Useful for testing
     * @param seed Long integer to seed random number generator
     */
    public AssetReader(Context context, long seed) {
        this(context);
    }

    public ArrayList<String> getFilesFromAssets(String folderName) throws IOException {
        ArrayList<String> filePool = new ArrayList<String>();
        if (folderName == null) {
            for (String name : mContext.getAssets().list(""))
            {
                filePool.add(name);
            }
        } else {

            for (String name : mContext.getAssets().list(folderName)) {
                filePool.add(name);
            }
        }
        return filePool;
    }

    public String getRandomFile(Random randomGenerator, String folderName) throws IOException {
        ArrayList<String> filePool = getFilesFromAssets(folderName);
        String fileName = filePool.get(randomGenerator.nextInt(filePool.size()));
        return fileName;
    }


    public String readFromAssetsFile(String fileName, String folderName) throws IOException{
        byte[] buffer = null;
        InputStream inputStream = mContext.getAssets().open(folderName + "/" + fileName);
        int size = inputStream.available(); //size of the file in bytes
        buffer = new byte[size]; //declare the size of the byte array with size of the file
        inputStream.read(buffer); //read file
        inputStream.close(); //close file

        String fileData = new String(buffer);
        return fileData; // return a String with the file data
    }
}
