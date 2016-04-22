package com.nearerToThee.viewTests;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.ListView;
import android.widget.TextView;

import com.nearerToThee.R;

/**
 * Created by Betsy on 4/19/2016.
 */
public class FileListActivityTests extends ActivityInstrumentationTestCase2<FileListActivity> {

    private FileListActivity m_activity;
    private TextView tvSelectedTag;
    private ListView listview;
    private String intentExtra;
    public final static String SELECTED_TAG = "com.nearerToThee.SELECTED_TAG";
    public final static String FILE_NAME = "com.nearerToThee.FILE_NAME";

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
        listview = (ListView) m_activity.findViewById(R.id.listview);
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
        assertNotNull("The list was not loaded", listview);
    }

    public void testListHasItems() {
        assertTrue(listview.getCount()>0);
    }

}
