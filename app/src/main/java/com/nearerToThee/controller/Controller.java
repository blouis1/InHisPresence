package com.nearerToThee.controller;

import android.content.Context;

import com.nearerToThee.R;
import com.nearerToThee.model.Devotion;
import com.nearerToThee.utilities.AssetReader;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Random;

/**
 * Controller class
 * Created by Betsy on 4/15/2016.
 */
public class Controller {
    private Devotion devotion;
    private String fileName;
    private AssetReader assetReader;
    private static Controller instance = null;
    private static Context mContext;
    private Random randomGenerator;
    private String fileContent;

    public static void initialize(Context context) {
        mContext = context;
    }

    /**
     * Check if the class has been initialized
     * @return true  if the class has been initialized
     *         false Otherwise
     */
    public static boolean hasBeenInitialized() {
        return mContext != null;
    }

    /**
     * The private constructor. Here you can use the context to initialize your variables.
     */
    private Controller()  {
        assetReader = new AssetReader(mContext);
        //randomGenerator  = new Random();
        //setFileName();
        //fileContent = assetReader.readFromAssetsFile(fileName, "devotions");
        //devotion = new Devotion(fileContent);
    }

    public static synchronized Controller getInstance()  {
        if (mContext == null) {
            throw new IllegalArgumentException("Impossible to get the instance. This class must be initialized before");
        }

        if (instance == null) {
            instance = new Controller();
        }

        return instance;
    }

    public String getTodaysFileName() {
        Calendar calendar = Calendar.getInstance();
        //Get Text File name and format it to get file name
        SimpleDateFormat sdf2 = new SimpleDateFormat("MMdd", Locale.US);
        String fileName = sdf2.format(calendar.getTime()) + ".txt";
        return fileName;
    }

    public String getTodaysVerse() throws IOException{
        return new Devotion(readFromFile(getTodaysFileName())).getVerse();
    }

    public String readFromFile(String fileName) throws IOException{
        return assetReader.readFromAssetsFile(fileName, "devotions");
    }

    public String getReading(String fileName) throws IOException {
        return new Devotion(readFromFile(fileName)).getReading();
    }

    public int getImageForTheDay() {
        Calendar cal = Calendar.getInstance();
        int day_of_week = cal.get(Calendar.DAY_OF_WEEK); //(1-7 means sunday - saturday)
        int id = 0;
        switch(day_of_week) {

            case 1: //Sun
                id = R.drawable.scenery2;
                break;
            case 2: //Mon
                id = R.drawable.flower1;
                break;
            case 3: //Tue
                id = R.drawable.orchid;
                break;
            case 4: //Wed
                id = R.drawable.flower;
                break;
            case 5: //Thu
                id = R.drawable.swan;
                break;
            case 6: //Fri
                id = R.drawable.tulip_field;
                break;
            case 7: //Sat
                id = R.drawable.pink_flower2;
                break;
        }
        return id;
    }
}
