package com.nearerToThee.viewTests;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.test.ActivityInstrumentationTestCase2;
import android.webkit.WebView;

import com.nearerToThee.R;
import com.nearerToThee.view.DevotionFragment;
import com.nearerToThee.view.MainActivity;
import com.nearerToThee.view.SearchFragment;
import com.nearerToThee.view.TodaysReadingActivity;
import com.nearerToThee.view.TopicsFragment;


/**
 * Tests for the TodaysReadingActivity class.
 * Created by Betsy on 4/15/2016.
 */
public class TodaysReadingActivityTests extends ActivityInstrumentationTestCase2<TodaysReadingActivity> {

    private TodaysReadingActivity mActivity;
    private WebView mWebView;

    public TodaysReadingActivityTests()
    {
        super(TodaysReadingActivity.class);
    }

    @Override
    protected void setUp() {
        mActivity = getActivity();
        mWebView = (WebView) mActivity.findViewById(R.id.wvReading);
    }

    public void testActivityExists() {
        assertNotNull(mActivity);
    }

    public void testWebViewExists() {
        assertNotNull(mWebView);
    }

}