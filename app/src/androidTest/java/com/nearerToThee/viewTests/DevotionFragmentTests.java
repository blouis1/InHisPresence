package com.nearerToThee.viewTests;

import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.nearerToThee.R;
import com.nearerToThee.view.DevotionFragment;
import com.nearerToThee.view.MainActivity;

/**
 * Tests for the Devotion Fragment class.
 * Created by Betsy on 4/15/2016.
 */
public class DevotionFragmentTests extends ActivityInstrumentationTestCase2<MainActivity> {

    private DevotionFragment devotionFragment;
    FragmentManager mFragmentManager;
    private MainActivity m_activity;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private MainActivity.SectionsPagerAdapter fragmentPagerAdapter;
    private View mRootView;
    private TextView mVerse;
    private ImageButton mRead;

    public DevotionFragmentTests()
    {
        super(MainActivity.class);
    }

    @Override
    protected void setUp() {
        devotionFragment = new DevotionFragment();
        m_activity = getActivity();
        mViewPager = (ViewPager) m_activity.findViewById(R.id.container);
        fragmentPagerAdapter = (MainActivity.SectionsPagerAdapter) mViewPager.getAdapter();
        DevotionFragment devotionFragment = (DevotionFragment) fragmentPagerAdapter.getCurrentFragment();
        mRootView = devotionFragment.getView().getRootView();
        mVerse = (TextView) mRootView.findViewById(R.id.tvVerse);
        mRead = (ImageButton)mRootView.findViewById(R.id.ibRead);

        mTabLayout = (TabLayout) m_activity.findViewById(R.id.tabs);

    }

    public void testFragmentIsNotNull() {
        assertNotNull(devotionFragment);
    }

    public void testTextViewIsVisible() {
        assertTrue(mVerse.getVisibility() == View.VISIBLE);
    }

    public void testImageButtonIsEnabled() {
        assertTrue(mRead.isEnabled());
    }

}

