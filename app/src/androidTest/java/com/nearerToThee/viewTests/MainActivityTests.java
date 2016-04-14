package com.nearerToThee.viewTests;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.test.ActivityInstrumentationTestCase2;

import com.nearerToThee.DevotionFragment;
import com.nearerToThee.MainActivity;
import com.nearerToThee.R;
import com.nearerToThee.SearchFragment;
import com.nearerToThee.TopicsFragment;


/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class MainActivityTests extends ActivityInstrumentationTestCase2<MainActivity> {

    private MainActivity m_activity;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private MainActivity.SectionsPagerAdapter fragmentPagerAdapter;

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
        assertTrue(mTabLayout.getTabCount() == 3);
    }

    public void testFirstTabIsDevotion() {
        assertTrue(mTabLayout.getTabAt(0).getText().toString().equals("Devotion".toUpperCase()));
    }

    public void testSecondTabIsTopics() {
        assertTrue(mTabLayout.getTabAt(1).getText().toString().equals("Topics".toUpperCase()));
    }

    public void testThirdTabIsSearch() {
        assertTrue(mTabLayout.getTabAt(2).getText().toString().equals("Search".toUpperCase()));
    }

    public void testDevotionTabIsOpen(){
        //TouchUtils.clickView(this, mTabLayout.getChildAt(0));
        assertTrue(mViewPager.getCurrentItem() == 0);
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

    public void testThirdTabOpensSearchFragment() {
        selectTab(2);
        assertTrue(fragmentPagerAdapter.getCurrentFragment() instanceof SearchFragment);
    }

    public void testSecondTabOpensTopicsFragment() {
        selectTab(1);
        assertTrue(fragmentPagerAdapter.getCurrentFragment() instanceof TopicsFragment);
    }

    public void testFirstTabOpensDevotionFragment() {
        selectTab(0);
        assertTrue(fragmentPagerAdapter.getCurrentFragment() instanceof DevotionFragment);
    }




}