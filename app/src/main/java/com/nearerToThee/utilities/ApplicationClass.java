package com.nearerToThee.utilities;

import android.app.Application;
import android.content.Context;

import com.nearerToThee.controller.Controller;

/**
 * This class provides a way to share information global to the application,
 * primarily to pass the context to classes outside activities.
 * Created by Betsy on 4/15/2016.
 */
public class ApplicationClass extends Application
{
    private static Context _context;

    /**
     * record the context in onCreate() and make it statically available.
     * Note: this is triggered only for live and instrumentation testing
     */
    @Override
    public void onCreate() {
        super.onCreate();
        _context = this;
    }//onCreate

    //static access routine to Context
    public static Context getContext() {
        return _context;
    }//getContext()
}
