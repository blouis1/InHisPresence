package com.nearerToThee.viewTests;

import android.app.Instrumentation;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.nearerToThee.R;
import com.nearerToThee.view.VerseFragment;
import com.nearerToThee.view.MainActivity;
import com.nearerToThee.view.ReadDevotionActivity;

/**
 * Tests for the Devotion Fragment class.
 * Created by Betsy on 4/15/2016.
 */
public class VerseFragmentTests extends ActivityInstrumentationTestCase2<MainActivity> {

    private VerseFragment verseFragment;
    private MainActivity m_activity;
    private ViewPager mViewPager;
    private MainActivity.SectionsPagerAdapter fragmentPagerAdapter;
    private View mRootView;
    private TextView mVerse;
    private ImageButton mRead;

    public VerseFragmentTests()
    {
        super(MainActivity.class);
    }

    @Override
    protected void setUp() {
        verseFragment = new VerseFragment();
        m_activity = getActivity();
        mViewPager = (ViewPager) m_activity.findViewById(R.id.container);
        fragmentPagerAdapter = (MainActivity.SectionsPagerAdapter) mViewPager.getAdapter();
        VerseFragment devotionFragment = (VerseFragment) fragmentPagerAdapter.getCurrentFragment();
        mRootView = devotionFragment.getView().getRootView();
        mVerse = (TextView) mRootView.findViewById(R.id.tvVerse);
        mRead = (ImageButton)mRootView.findViewById(R.id.ibRead);
    }

    public void testFragmentIsNotNull() {
        assertNotNull(verseFragment);
    }

    public void testTextViewIsVisible() {
        assertTrue(mVerse.getVisibility() == View.VISIBLE);
    }

    public void testImageButtonIsEnabled() {
        assertTrue(mRead.isEnabled());
    }

    public void testClickingImageButtonOpensTodaysReadingActivity() {
        // register next activity that need to be monitored.
        Instrumentation.ActivityMonitor activityMonitor = getInstrumentation()
                .addMonitor(ReadDevotionActivity.class.getName(), null, false);
        // Tap read button
        TouchUtils.clickView(this, mRead);
        ReadDevotionActivity nextActivity = (ReadDevotionActivity)getInstrumentation().waitForMonitorWithTimeout(activityMonitor, 10000);
        // next activity is opened and captured.
        assertNotNull("Today's reading activity is not launched", nextActivity);
        nextActivity .finish();
    }

}

