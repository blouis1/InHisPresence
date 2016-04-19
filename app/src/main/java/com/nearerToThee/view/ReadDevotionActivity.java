package com.nearerToThee.view;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_devotion);

        Intent intent = getIntent();
        mFileName = intent.getStringExtra(FILE_NAME);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_top);
        toolbar.setTitle("NearerToThee".toString());
        setSupportActionBar(toolbar);
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

    public void setText() {
        try {
            wvReading.loadData(mController.getReading(mFileName), "text/html", "UTF-8");
        }
        catch (IOException ioe) {
                new AlertDialog.Builder(this)
                        .setTitle("File Not Found")
                        .setMessage("Could not load today's devotion.")
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

        return super.onOptionsItemSelected(item);
    }

}
