package com.nearerToThee.view;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;

import com.nearerToThee.R;
import com.nearerToThee.controller.Controller;
import com.nearerToThee.utilities.ApplicationClass;

import java.io.IOException;

public class TodaysReadingActivity extends AppCompatActivity {

    private View mRootView;
    private Controller mController;
    private WebView wvReading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todays_reading);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ApplicationClass appClass = (ApplicationClass)getApplicationContext();
        mController = appClass.getController();
        mRootView = this.findViewById(R.id.rootLayout);
        wvReading = (WebView) this.findViewById(R.id.wvReading);
        wvReading.setBackgroundColor(Color.TRANSPARENT);

        setImage();
        setText();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setVisibility(View.GONE);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public void setText() {
        try {
            String text_string = mController.getDevotion();
            Spanned text = Html.fromHtml(text_string);
            wvReading.loadData(text_string, "text/html", "UTF-8");        }
        catch (IOException ioe){
            // show error
            Log.d("FILE_ERROR", ioe.getMessage());
        }
    }

    public void setImage() {
        mRootView.setBackgroundResource(mController.getImageForTheDay());
        mRootView.getBackground().setAlpha(75);
    }

}
