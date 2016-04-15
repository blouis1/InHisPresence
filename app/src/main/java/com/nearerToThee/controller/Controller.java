package com.nearerToThee.controller;

import android.content.Context;

import com.nearerToThee.model.Devotion;
import com.nearerToThee.utilities.AssetReader;

import java.io.IOException;

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
}
