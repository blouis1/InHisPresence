package com.nearerToThee.viewTests;

import android.app.Instrumentation;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.view.View;
import android.widget.TextView;

import com.nearerToThee.R;
import com.nearerToThee.model.File;
import com.nearerToThee.view.FileListActivity;
import com.nearerToThee.view.ReadDevotionActivity;
import com.nearerToThee.view.SearchActivity;

/**
 * Created by Betsy on 4/19/2016.
 */
public class FileListActivityTests extends ActivityInstrumentationTestCase2<FileListActivity> {

    private FileListActivity m_activity;
    private TextView tvSelectedTag;
    private RecyclerView recyclerView;
    private String intentExtra;
    public final static String SELECTED_TAG = "com.nearerToThee.SELECTED_TAG";
    private Toolbar toolbar;

    public FileListActivityTests() {
        super(FileListActivity.class);
    }

    @Override
    protected void setUp() {
        Intent intent = new Intent();
        intent.putExtra(SELECTED_TAG, "Peace");
        setActivityIntent(intent);
        m_activity = getActivity();
        tvSelectedTag = (TextView) m_activity.findViewById(R.id.textView);
        intentExtra = m_activity.getIntent().getStringExtra(SELECTED_TAG);
        recyclerView = (RecyclerView) m_activity.findViewById(R.id.rv);
        toolbar = (Toolbar) m_activity.findViewById(R.id.toolbar);
    }

    public void testActivityExists() {
        assertNotNull(m_activity);
    }

    public void testCategoryNameIntentExtraIsReceived() {
        assertEquals("Peace", intentExtra);
    }

    public void testTextViewShowsCorrectTitle() {
        assertEquals("Devotions about Peace", tvSelectedTag.getText().toString());
    }

    public void testListIsNotNull() {
        assertNotNull("The recycler view is null", recyclerView);
    }

    public void testToolbarActionItemSearchOpensSearchActivity() {
        View searchButton = toolbar.findViewById(R.id.action_search);

        // register next activity that need to be monitored.
        Instrumentation.ActivityMonitor activityMonitor = getInstrumentation()
                .addMonitor(SearchActivity.class.getName(), null, false);
        TouchUtils.clickView(FileListActivityTests.this, searchButton);
        SearchActivity nextActivity = (SearchActivity) getInstrumentation().waitForMonitorWithTimeout(activityMonitor, 10000);

        // next activity is opened and captured.
        assertNotNull("Search activity is not launched", nextActivity);

        nextActivity .finish();

    }


    public void testToolbarActionItemFavoritesOpensFileListActivity() {
        View favoritesButton = toolbar.findViewById(R.id.action_favorite);

        // register next activity that need to be monitored.
        Instrumentation.ActivityMonitor activityMonitor = getInstrumentation()
                .addMonitor(FileListActivity.class.getName(), null, false);
        TouchUtils.clickView(FileListActivityTests.this, favoritesButton);
        FileListActivity nextActivity = (FileListActivity) getInstrumentation().waitForMonitorWithTimeout(activityMonitor, 10000);

        // next activity is opened and captured.
        assertNotNull("File list activity is not launched", nextActivity);

        nextActivity .finish();

    }

}
