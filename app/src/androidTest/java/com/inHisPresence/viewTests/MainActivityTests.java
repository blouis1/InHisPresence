package com.inHisPresence.viewTests;

import android.app.Instrumentation;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.view.View;

import com.inHisPresence.view.FileListActivity;
import com.inHisPresence.view.SearchActivity;
import com.inHisPresence.view.MainActivity;
import com.inHisPresence.R;


/**
 * Tests for the MainActivity class.
 * Created by Betsy on 4/15/2016.
 */
public class MainActivityTests extends ActivityInstrumentationTestCase2<MainActivity> {

    private MainActivity m_activity;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private MainActivity.SectionsPagerAdapter fragmentPagerAdapter;
    private Toolbar toolbar;

    public MainActivityTests()
    {
        super(MainActivity.class);
    }

    @Override
    protected void setUp() {
        m_activity = getActivity();
        mViewPager = (ViewPager) m_activity.findViewById(R.id.container);
        mTabLayout = (TabLayout) m_activity.findViewById(R.id.tabs);
        fragmentPagerAdapter = (MainActivity.SectionsPagerAdapter) mViewPager.getAdapter();
        toolbar = (Toolbar) m_activity.findViewById(R.id.toolbar);
    }

    public void testActivityExists() {
        assertNotNull(m_activity);
    }

    public void testViewPagerExists() {
        assertNotNull(mViewPager);
    }

    public void testTabLayoutExists() {
        assertNotNull(mTabLayout);
    }

    public void testThreeTabsPresent() {
        assertTrue(mTabLayout.getTabCount() == 2);
    }

    public void testFirstTabIsDevotion() {
        assertTrue(mTabLayout.getTabAt(0).getText().toString().equals("Today".toUpperCase()));
    }

    public void testSecondTabIsTopics() {
        assertTrue(mTabLayout.getTabAt(1).getText().toString().equals("Topics".toUpperCase()));
    }

    public void testDevotionTabIsOpen(){
        //TouchUtils.clickView(this, mTabLayout.getChildAt(0));
        assertTrue(mViewPager.getCurrentItem() == 0);
    }

    private void selectTab(final int position) {
        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                mTabLayout.getTabAt(position).select();
            }
        });
    }

    public void testSecondTabOpensTopicsFragment() {
        selectTab(1);
        assertTrue(fragmentPagerAdapter.getCurrentFragment() instanceof TopicsFragment);
    }

    public void testFirstTabOpensDevotionFragment() {
        selectTab(0);
        assertTrue(fragmentPagerAdapter.getCurrentFragment() instanceof VerseFragment);
    }

    public void testToolbarActionItemSearchOpensSearchActivity() {
        View searchButton = toolbar.findViewById(R.id.action_search);

        // register next activity that need to be monitored.
        Instrumentation.ActivityMonitor activityMonitor = getInstrumentation()
                .addMonitor(SearchActivity.class.getName(), null, false);
        TouchUtils.clickView(MainActivityTests.this, searchButton);
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
        TouchUtils.clickView(MainActivityTests.this, favoritesButton);
        FileListActivity nextActivity = (FileListActivity) getInstrumentation().waitForMonitorWithTimeout(activityMonitor, 10000);

        // next activity is opened and captured.
        assertNotNull("File list activity is not launched", nextActivity);

        nextActivity .finish();

    }

}