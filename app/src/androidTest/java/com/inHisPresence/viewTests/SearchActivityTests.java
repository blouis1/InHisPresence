package com.inHisPresence.viewTests;

import android.support.v7.widget.RecyclerView;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import com.inHisPresence.R;
import com.inHisPresence.view.SearchActivity;

/**
 * Created by Betsy on 4/19/2016.
 */
public class SearchActivityTests extends ActivityInstrumentationTestCase2<SearchActivity> {

    private SearchActivity m_activity;
    private AutoCompleteTextView mSearchBox;
    private Button mSearchButton;
    private RecyclerView mRecyclerView;

    public SearchActivityTests()
    {
        super(SearchActivity.class);
    }

    @Override
    protected void setUp() {
        m_activity = getActivity();
        mSearchBox = (AutoCompleteTextView) m_activity.findViewById(R.id.autocomplete_keywords);
        mSearchButton = (Button) m_activity.findViewById(R.id.btnSearch);
        mRecyclerView = (RecyclerView) m_activity.findViewById(R.id.rv);
    }

    public void testActivityIsNotNull() {
        assertNotNull(m_activity);
    }

    public void testRecyclerViewIsNotNull() {
        assertNotNull(mRecyclerView);
    }

    public void testTextViewIsVisible() {
        assertTrue(mSearchBox.getVisibility() == View.VISIBLE);
    }

    public void testButtonIsNotEnabled() {
        assertFalse(mSearchButton.isEnabled());
    }

    private void enterSearchText(final String text) {
        // Simulates value entered into search field
        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                SearchActivityTests.this.mSearchBox.setText(text);
            }
        });
    }

    private void clickSearchButton() {
        TouchUtils.clickView(this, mSearchButton);
    }

    public void testButtonIsEnabledWhenSearchBoxHasText() {
        enterSearchText("Peace");
        assertTrue(mSearchButton.isEnabled());
    }

}
