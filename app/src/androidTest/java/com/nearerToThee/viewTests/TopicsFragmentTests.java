package com.nearerToThee.viewTests;

import android.app.Instrumentation;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.view.View;
import android.widget.GridView;

import com.nearerToThee.R;
import com.nearerToThee.view.FileListActivity;
import com.nearerToThee.view.MainActivity;
import com.nearerToThee.view.ReadDevotionActivity;
import com.nearerToThee.view.TopicsFragment;

/**
 * Created by Betsy on 4/20/2016.
 */
public class TopicsFragmentTests extends ActivityInstrumentationTestCase2<MainActivity> {
    private TopicsFragment topicsFragment;
    private MainActivity m_activity;
    private ViewPager mViewPager;
    private MainActivity.SectionsPagerAdapter fragmentPagerAdapter;
    private GridView mGridview;
    private TabLayout mTabLayout;
    public final static String SELECTED_TAG = "com.nearerToThee.SELECTED_TAG";

    public TopicsFragmentTests()
    {
        super(MainActivity.class);
    }

    @Override
    protected void setUp() {
        m_activity = getActivity();
        mViewPager = (ViewPager) m_activity.findViewById(R.id.container);
        fragmentPagerAdapter = (MainActivity.SectionsPagerAdapter) mViewPager.getAdapter();
        mTabLayout = (TabLayout) m_activity.findViewById(R.id.tabs);
        selectTab(1);
        topicsFragment = (TopicsFragment) fragmentPagerAdapter.getCurrentFragment();
        mGridview = (GridView) m_activity.findViewById(R.id.gridViewCustom);

    }

    private void selectTab(final int position) {
        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                //String title = fragmentPagerAdapter.getPageTitle(position).toString();
                //String tab = mTabLayout.getTabAt(position).getText().toString();
                mTabLayout.getTabAt(position).select();
            }
        });
    }

    public void testFragmentIsNotNull() {
        assertNotNull(topicsFragment);
    }

    public void testGridViewIsNotNull() {
        assertNotNull(mGridview);
    }

    public void testGridViewHasItems() {
        assertTrue(mGridview.getAdapter().getCount() > 0);
    }

    public void testClickingGridViewItemOpensActivity() {
        // register next activity that need to be monitored.
        Instrumentation.ActivityMonitor activityMonitor = getInstrumentation()
                .addMonitor(FileListActivity.class.getName(), null, false);
        // Tap read button
        mGridview.setSelection(0);
        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                mGridview.performItemClick(mGridview, 0, mGridview.getSelectedItemId());
            }
        });
        try {
            Thread.sleep(5000);
        } catch (Exception e) {
            e.getLocalizedMessage();
        }
    //TouchUtils.clickView(this, mGridview.getChildAt(0));
    FileListActivity nextActivity = (FileListActivity) getInstrumentation().waitForMonitorWithTimeout(activityMonitor, 10000);

    // next activity is opened and captured.
    assertNotNull("File list activity is not launched", nextActivity);

    nextActivity .finish();
    }

}