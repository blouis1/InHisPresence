package com.nearerToThee.controller;

import android.content.Context;

import com.nearerToThee.R;
import com.nearerToThee.model.Devotion;
import com.nearerToThee.utilities.AssetReader;

import java.io.IOException;
import java.util.Calendar;

/**
 * Controller class
 * Created by Betsy on 4/15/2016.
 */
public class Controller {
    private Context mContext;
    private AssetReader assetReader;
    private Devotion devotion;

    public Controller(Context context) {
        mContext = context;
        assetReader = new AssetReader(mContext);
        devotion = new Devotion(assetReader);
    }

    public String getVerse() throws IOException{
        return devotion.getTodaysVerse();
    }

    public String getDevotion() throws IOException{
        return devotion.getTodaysDevotion();
    }

    public int getImageForTheDay() {
        Calendar cal = Calendar.getInstance();
        int day_of_week = cal.get(Calendar.DAY_OF_WEEK); //(1-7 means sunday - saturday)

        switch(day_of_week) {

            case 1: //Sun
                return R.drawable.scenery2;
            case 2: //Mon
                return R.drawable.flower1;
            case 3: //Tue
                return R.drawable.orchid;
            case 4: //Wed
                return R.drawable.flower;
            case 5: //Thu
                return R.drawable.swan;
            case 6: //Fri
                return R.drawable.tulip_field;
            case 7: //Sat
                return R.drawable.pink_flower2;
            default:
                return R.drawable.flower;
        }

    }
}
