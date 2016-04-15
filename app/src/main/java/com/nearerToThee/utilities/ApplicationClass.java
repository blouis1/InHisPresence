package com.nearerToThee.utilities;

import android.app.Application;

import com.nearerToThee.controller.Controller;

/**
 * Created by Betsy on 4/15/2016.
 */
public class ApplicationClass extends Application
{
    private Controller controller;

    public Controller getController() {
        return controller;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }
}
