package com.nearerToThee.utilities;

import android.content.Context;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Class with methods to read files from Assets folders.
 * Created by Betsy on 4/15/2016.
 */
public class AssetReader {
    private Context mContext;

    public AssetReader(Context context) {
        mContext = context;
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
