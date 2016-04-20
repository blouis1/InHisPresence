package com.nearerToThee.viewTests;

import android.test.ActivityInstrumentationTestCase2;
import android.webkit.WebView;

import com.nearerToThee.R;
import com.nearerToThee.view.ReadDevotionActivity;


/**
 * Tests for the ReadDevotionActivity class.
 * Created by Betsy on 4/15/2016.
 */
public class ReadDevotionActivityTests extends ActivityInstrumentationTestCase2<ReadDevotionActivity> {

    private ReadDevotionActivity mActivity;
    private WebView mWebView;

    public ReadDevotionActivityTests()
    {
        super(ReadDevotionActivity.class);
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