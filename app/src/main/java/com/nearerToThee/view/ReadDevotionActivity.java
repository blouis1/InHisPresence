package com.nearerToThee.view;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Spanned;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;

import com.nearerToThee.R;
import com.nearerToThee.controller.Controller;

import java.io.IOException;

public class ReadDevotionActivity extends AppCompatActivity {

    private View mRootView;
    private Controller mController;
    private WebView wvReading;
    private String mFileName;
    public final static String FILE_NAME = "com.nearerToThee.FILE_NAME";
    public final static String SEARCH_FRAGMENT = "com.nearerToThee.SEARCH_FRAGMENT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_read_devotion);

        Intent intent = getIntent();
        mFileName = intent.getStringExtra(FILE_NAME);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_top);
        toolbar.setTitle("NearerToThee");
        setSupportActionBar(toolbar);

        // Add up button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mController = Controller.getInstance();
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

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //here you can handle orientation change
    }

    public void setText() {
        try {
            String head = "<head><style>@font-face {font-family: 'Gabriela-Regular';src: url('file:///android_asset/fonts/Gabriela-Regular.ttf');}" +
                    "@font-face {font-family: 'Junge-Regular';src: url('file:///android_asset/fonts/Junge-Regular.ttf');}" +
                    "body {font-family: 'Junge-Regular'; font-weight: 600;}h1 {font-family: 'Gabriela-Regular'; }aside {font-family: 'Gabriela-Regular'; font-style: italic; font-weight: 400;}</style></head>";
            String text="<html>" +head + "<body >" + mController.getReading(mFileName) + "</body></html>";
            wvReading.loadDataWithBaseURL("", text, "text/html", "UTF-8", null);
        }
        catch (IOException ioe) {
                new AlertDialog.Builder(this)
                        .setTitle("File Not Found")
                        .setMessage("Could not load devotion.")
                        .setPositiveButton(android.R.string.ok, null)
                        .show();
            }

    }

    public void setImage() {
        mRootView.setBackgroundResource(mController.getImageForTheDay());
        //	Alpha Values 0-255, 0 means fully transparent, and 255 means fully opaque
        mRootView.getBackground().setAlpha(75);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        if (id == R.id.action_search) {
            Intent i = new Intent(this, MainActivity.class);
            i.putExtra(SEARCH_FRAGMENT, "SearchFragment");
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }

}
