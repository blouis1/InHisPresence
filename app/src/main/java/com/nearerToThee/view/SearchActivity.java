package com.nearerToThee.view;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nearerToThee.R;
import com.nearerToThee.controller.Controller;
import com.nearerToThee.data_access_layer.DatabaseHelper;
import com.nearerToThee.model.File;
import com.nearerToThee.utilities.RVAdapter;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    private DatabaseHelper mDbHelper;
    private AutoCompleteTextView mSearchBox;
    private RecyclerView mRecyclerView;
    private Button mSearchButton;
    private TextView mTvNoResults;
    private ArrayAdapter<String> mAdapter;
    public final static String FILE_NAME = "com.nearerToThee.FILE_NAME";
    private Controller mController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("NearerToThee");
        setSupportActionBar(toolbar);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setVisibility(View.GONE);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        View rootView = (RelativeLayout) findViewById(R.id.rootLayout);
        mController = Controller.getInstance();
        rootView.setBackgroundResource(mController.getImageForTheDay());
        //	Alpha Values 0-255, 0 means fully transparent, and 255 means fully opaque
        rootView.getBackground().setAlpha(75);
        mSearchBox = (AutoCompleteTextView) findViewById(R.id.autocomplete_keywords);

        mRecyclerView = (RecyclerView)findViewById(R.id.rv);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(llm);

        mTvNoResults = (TextView) findViewById(android.R.id.empty);

        mDbHelper = new DatabaseHelper(this.getApplicationContext());
        mSearchButton = (Button)rootView.findViewById(R.id.btnSearch);
        mSearchButton.setEnabled(false);

        mSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performSearch();
            }
        });

        // to show enter key on keyboard
        mSearchBox.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    performSearch();
                }
                return false;
            }
        });

        mSearchBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (mSearchBox.getText().toString().trim().length() == 0) {
                    mRecyclerView.setAdapter(null);
                }
                mSearchButton.setEnabled(mSearchBox.getText().toString().trim().length() != 0);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        // Get the string array of keywords
        ArrayList<String> keywordList = mDbHelper.getAllKeywords();
        String[] keywords = keywordList.toArray(new String[keywordList.size()]);
        // Create the mAdapter and set it to the AutoCompleteTextView
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, keywords);
        mSearchBox.setAdapter(mAdapter);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //here you can handle orientation change
    }

    public void performSearch() {
        String keyword = mSearchBox.getText().toString();
        mSearchBox.clearFocus();
        InputMethodManager in = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        in.hideSoftInputFromWindow(mSearchBox.getWindowToken(), 0);

        ArrayList<File> fileListByTag = mDbHelper.getFilesFromKeyword(keyword);

        RVAdapter adapter = new RVAdapter(fileListByTag, new RVAdapter.OnItemClickListener() {
            @Override public void onItemClick(File file) {
                Intent intent = new Intent(SearchActivity.this, ReadDevotionActivity.class);
                intent.putExtra(FILE_NAME, file.getFileName());
                startActivity(intent);
            }

            @Override
            public void onDelete(String fileName) {
                // do nothing
            }
        }, false);

        mRecyclerView.setAdapter(adapter);

        if (mRecyclerView.getAdapter().getItemCount() == 0) {
            Toast.makeText(this.getApplicationContext(), "No devotions available for that keyword", Toast.LENGTH_SHORT).show();
            mTvNoResults.setVisibility(View.VISIBLE);
        }
        else {
            mTvNoResults.setVisibility(View.GONE);
        }

    }

}
