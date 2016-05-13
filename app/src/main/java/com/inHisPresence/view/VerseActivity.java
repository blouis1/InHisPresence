package com.inHisPresence.view;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.inHisPresence.R;
import com.inHisPresence.controller.Controller;

import java.io.IOException;

public class VerseActivity extends AppCompatActivity {

    private Controller mController;
    public final static String FAVORITES = "com.inHisPresence.FAVORITES";
    private View mRootView;
    private TextView mVerse;
    private ImageButton mRead;
    public final static String FILE_NAME = "com.inHisPresence.FILE_NAME";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setContentView(R.layout.activity_verse);

        mController.initialize(this.getApplicationContext());
        mController = Controller.getInstance();
        mRootView = findViewById(R.id.rootLayout);
        mVerse = (TextView) findViewById(R.id.tvVerse);

        setImage();
        setVerse();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VerseActivity.this, ReadDevotionActivity.class);
                String fileName = mController.getTodaysFileName();
                intent.putExtra(FILE_NAME, fileName);
                startActivity(intent);
            }
        });
    }

    public void setVerse() {

        Typeface typeFace = Typeface.createFromAsset(this.getAssets(), "fonts/Gabriela-Regular.ttf");
        mVerse.setTypeface(typeFace);
        try {
            mVerse.setText(Html.fromHtml(mController.getTodaysVerse()));
        }
        catch (IOException ioe) {
            mRead.setEnabled(false);
            new AlertDialog.Builder(this)
                    .setTitle("File Not Found")
                    .setMessage("Could not load devotion.")
                    .setPositiveButton(android.R.string.ok, null)
                    .show();
        }
    }

    public void setImage() {
        int id = mController.getImageForTheDay();
        mRootView.setBackgroundResource(id);
        // Alpha Values 0-255, 0 means fully transparent, and 255 means fully opaque
        mRootView.getBackground().setAlpha(255);
    }

    @Override
    public void onResume() {
        super.onResume();
        mRootView.getBackground().setAlpha(255);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //here you can handle orientation change
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
        /*if (id == R.id.action_settings) {
            return true;
        }*/
        if (id == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        /*if (id == R.id.action_search) {
            Intent i = new Intent(this, SearchActivity.class);
            //i.putExtra(SEARCH_FRAGMENT, "FavoritesFragment");
            startActivity(i);
            //onSearchRequested();
        }*/
        if (id == R.id.action_favorite) {
            Intent intent = new Intent(this, FileListActivity.class);
            intent.putExtra(FAVORITES, "Your Favorites");
            startActivity(intent);
        }


        return super.onOptionsItemSelected(item);
    }

}
