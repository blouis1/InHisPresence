package com.nearerToThee.view;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.ActionMenuItemView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ActionMenuView;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;

import com.nearerToThee.R;
import com.nearerToThee.controller.Controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ReadDevotionActivity extends AppCompatActivity {

    private View mRootView;
    private Controller mController;
    private WebView wvReading;
    private String mFileName;
    public final static String FILE_NAME = "com.nearerToThee.FILE_NAME";
    public final static String SEARCH_FRAGMENT = "com.nearerToThee.SEARCH_FRAGMENT";
    private Calendar mCalendar;
    private int year;
    private int month;
    private int day;
    private String displayed_date;
    private ImageButton btnChangeDate;
    private TextView tvDisplayDate;
    private SimpleDateFormat displayDateFormatter = new SimpleDateFormat("MMM d, yyyy", Locale.US);
    private SimpleDateFormat fileNameFormatter = new SimpleDateFormat("MMdd",Locale.US);
    private Toolbar toolbar_bottom;


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

        toolbar_bottom = (Toolbar) findViewById(R.id.toolbar_bottom);
        toolbar_bottom.setTitle("");
        setupEvenlyDistributedToolbar();
        toolbar_bottom.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.action_previous) {
                    loadPrevious();
                }
                if (id == R.id.action_next) {
                    loadNext();
                }
                return onOptionsItemSelected(item);
            }
        });


        // Add up button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mController = Controller.getInstance();
        mRootView = this.findViewById(R.id.rootLayout);
        wvReading = (WebView) this.findViewById(R.id.wvReading);
        wvReading.setBackgroundColor(Color.TRANSPARENT);

        setImage();
        setDateOnView();

        addListenerOnChangeDate();

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

    // display current date both on the text view and the Date Picker when the application starts.
    private void setDateOnView() {

        //Use file name to extract date
        String beforeFirstDot = mFileName.split("\\.")[0];
        month = Integer.parseInt(beforeFirstDot.substring(0, 2))-1;
        day = Integer.parseInt(beforeFirstDot.substring(2, 4));

        //Get date. Also used by datepicker
        mCalendar = Calendar.getInstance();
        mCalendar.set(Calendar.MONTH, month);
        mCalendar.set(Calendar.DAY_OF_MONTH, day);
        year = mCalendar.get(Calendar.YEAR);

        Date date = mCalendar.getTime();

        displayed_date = displayDateFormatter.format(date);

        //update displayed date
        updateDisplayedDate();
        setText();
    }

    private void updateDisplayedDate() {
        tvDisplayDate = (TextView) findViewById(R.id.tvDisplayDate);
        tvDisplayDate.setText(displayed_date);
    }


    public void addListenerOnChangeDate() {

        btnChangeDate = (ImageButton) findViewById(R.id.calendarButton);

        btnChangeDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                DatePickerDialog datePickerDialog = new DatePickerDialog(ReadDevotionActivity.this,
                        android.R.style.Theme_Holo_Dialog, datePickerListener,
                        year, month, day);
                datePickerDialog.show();
            }

        });

    }

    private DatePickerDialog.OnDateSetListener datePickerListener
            = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
            Calendar c = Calendar.getInstance();
            year = c.get(Calendar.YEAR);
            month = selectedMonth;
            day = selectedDay;
            mCalendar.set(year, month, day);

            Date date = mCalendar.getTime();

            // get file Name from selected date
            mFileName = fileNameFormatter.format(date).concat(".txt");
            setText();

            //update displayed date
            displayed_date = displayDateFormatter.format(date);
            updateDisplayedDate();
        }
    };

    public void loadPrevious() {

        mCalendar.add(Calendar.DATE, -1);
        Date date = mCalendar.getTime();

        //update datepicker
        year = Calendar.getInstance().get(Calendar.YEAR);
        month= mCalendar.get(Calendar.MONTH);
        day= mCalendar.get(Calendar.DAY_OF_MONTH);
        //cal.set(year, month, day);

        // get file Name from selected date
        mFileName = fileNameFormatter.format(date).concat(".txt");
        setText();

        //update displayed date
        displayed_date = displayDateFormatter.format(date);
        updateDisplayedDate();

    }

    public void loadNext() {

        mCalendar.add(Calendar.DATE, 1);
        Date date = mCalendar.getTime();

        //update datepicker
        year = Calendar.getInstance().get(Calendar.YEAR);
        month= mCalendar.get(Calendar.MONTH);
        day= mCalendar.get(Calendar.DAY_OF_MONTH);
        //cal.set(year, month, day);

        // get file Name from selected date
        mFileName = fileNameFormatter.format(date).concat(".txt");
        setText();

        //update displayed date
        displayed_date = displayDateFormatter.format(date);
        updateDisplayedDate();

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
        if (id == R.id.action_search) {
            Intent i = new Intent(this, MainActivity.class);
            i.putExtra(SEARCH_FRAGMENT, "SearchFragment");
            startActivity(i);
        }


        return super.onOptionsItemSelected(item);
    }

    @TargetApi(21)
    public void setupEvenlyDistributedToolbar(){
        // Use Display metrics to get Screen Dimensions
        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);

        // Toolbar
        Toolbar mToolbar = toolbar_bottom;
        // Inflate your menu
        mToolbar.inflateMenu(R.menu.menu_bottom);

        // Add 10 spacing on either side of the toolbar
        mToolbar.setContentInsetsAbsolute(10, 10);

        // Get the ChildCount of your Toolbar, this should only be 1
        int childCount = mToolbar.getChildCount();
        // Get the Screen Width in pixels
        int screenWidth = metrics.widthPixels;

        // Create the Toolbar Params based on the screenWidth
        Toolbar.LayoutParams toolbarParams = new Toolbar.LayoutParams(screenWidth, android.widget.Toolbar.LayoutParams.WRAP_CONTENT);

        // Loop through the child Items
        for(int i = 0; i < childCount; i++){
            // Get the item at the current index
            View childView = mToolbar.getChildAt(i);
            // If its a ViewGroup
            if(childView instanceof ViewGroup){
                // Set its layout params
                childView.setLayoutParams(toolbarParams);
                // Get the child count of this view group, and compute the item widths based on this count & screen size
                int innerChildCount = ((ViewGroup) childView).getChildCount();
                int itemWidth  = (screenWidth / innerChildCount);
                // Create layout params for the ActionMenuView
                ActionMenuView.LayoutParams params = new ActionMenuView.LayoutParams(itemWidth, ActionMenuView.LayoutParams.WRAP_CONTENT);
                // Loop through the children
                for(int j = 0; j < innerChildCount; j++){
                    View grandChild = ((ViewGroup) childView).getChildAt(j);
                    if(grandChild instanceof ActionMenuItemView){
                        // set the layout parameters on each View
                        grandChild.setLayoutParams(params);
                    }
                }
            }
        }
    }

}
