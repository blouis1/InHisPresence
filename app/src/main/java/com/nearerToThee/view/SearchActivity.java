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
import com.nearerToThee.data_access_layer.DatabaseHelper;
import com.nearerToThee.model.File;
import com.nearerToThee.utilities.RVAdapter;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private AutoCompleteTextView searchBox;
    private RecyclerView recyclerView;
    private Button searchButton;
    private TextView tvNoResults;
    private ArrayAdapter<String> adapter;
    public final static String FILE_NAME = "com.nearerToThee.FILE_NAME";

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
        searchBox = (AutoCompleteTextView) findViewById(R.id.autocomplete_keywords);

        recyclerView = (RecyclerView)findViewById(R.id.rv);
        TextView emptyView = (TextView) findViewById(R.id.empty_view);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(llm);

        tvNoResults = (TextView) findViewById(android.R.id.empty);

        dbHelper = new DatabaseHelper(this.getApplicationContext());
        searchButton = (Button)rootView.findViewById(R.id.btnSearch);
        searchButton.setEnabled(false);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performSearch();
            }
        });

        // to show enter key on keyboard
        searchBox.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    performSearch();
                }
                return false;
            }
        });

        searchBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (searchBox.getText().toString().trim().length()==0) {
                    recyclerView.setAdapter(null);
                }
                searchButton.setEnabled(searchBox.getText().toString().trim().length()!=0);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        // Get the string array of keywords
        ArrayList<String> keywordList = dbHelper.getAllKeywords();
        String[] keywords = keywordList.toArray(new String[keywordList.size()]);
        // Create the adapter and set it to the AutoCompleteTextView
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, keywords);
        searchBox.setAdapter(adapter);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //here you can handle orientation change
    }

    public void performSearch() {
        String keyword = searchBox.getText().toString();
        searchBox.clearFocus();
        InputMethodManager in = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        in.hideSoftInputFromWindow(searchBox.getWindowToken(), 0);

        ArrayList<File> fileListByTag = dbHelper.getFilesFromKeyword(keyword);

        RVAdapter adapter = new RVAdapter(fileListByTag, new RVAdapter.OnItemClickListener() {
            @Override public void onItemClick(File file) {
                //Toast.makeText(SearchActivity.this.getApplicationContext(), "Item Clicked", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(SearchActivity.this, ReadDevotionActivity.class);
                intent.putExtra(FILE_NAME, file.getFileName());
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(adapter);
        // create a new ListView, set the adapter and item click listener

        recyclerView.setAdapter(adapter);
        if (recyclerView.getAdapter().getItemCount() == 0) {
            Toast.makeText(this.getApplicationContext(), "No devotions available for that keyword", Toast.LENGTH_SHORT).show();
            tvNoResults.setVisibility(View.VISIBLE);
        }
        else {
            tvNoResults.setVisibility(View.GONE);
        }

    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);

        SearchManager searchManager = (SearchManager)
                getSystemService(Context.SEARCH_SERVICE);
        MenuItem searchActionBarItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchActionBarItem);
        SearchableInfo searchableInfo = searchManager.getSearchableInfo(getComponentName());
        searchView.setSearchableInfo(searchableInfo);
        searchView.setIconifiedByDefault(false);
        return true;
    }*/

}
