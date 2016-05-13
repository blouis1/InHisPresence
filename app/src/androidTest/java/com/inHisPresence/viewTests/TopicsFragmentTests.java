package com.inHisPresence.viewTests;

import android.app.Instrumentation;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.GridView;

import com.inHisPresence.R;
import com.inHisPresence.view.FileListActivity;
import com.inHisPresence.view.MainActivity;

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

    public void testGridViewHas12Items() {
        assertTrue(mGridview.getAdapter().getCount() == 12);
    }

    public void testClickingGridViewItemOpensActivity() {
        // register next activity that need to be monitored.
        Instrumentation.ActivityMonitor activityMonitor = getInstrumentation()
                .addMonitor(FileListActivity.class.getName(), null, false);

        //mGridview.performItemClick(mGridview, 0, mGridview.getSelectedItemId());
        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                mGridview.setSelection(0);
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
