package com.nearerToThee.view;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nearerToThee.R;
import com.nearerToThee.controller.Controller;
import com.nearerToThee.data_access_layer.DatabaseHelper;
import com.nearerToThee.model.File;
import com.nearerToThee.utilities.RVAdapter;

import java.util.ArrayList;

public class FileListActivity extends AppCompatActivity {

    public final static String SELECTED_TAG = "com.nearerToThee.SELECTED_TAG";
    public final static String FAVORITES = "com.nearerToThee.FAVORITES";
    public final static String FILE_NAME = "com.nearerToThee.FILE_NAME";
    private DatabaseHelper mDbHelper;
    private boolean mShowRemoveButton;
    private int mLastFirstVisiblePosition;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_file_list);
        RelativeLayout rootView = (RelativeLayout) findViewById(R.id.rootLayout);
        Controller controller = Controller.getInstance();
        rootView.setBackgroundResource(controller.getImageForTheDay());
        //	Alpha Values 0-255, 0 means fully transparent, and 255 means fully opaque
        rootView.getBackground().setAlpha(75);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("NearerToThee");
        setSupportActionBar(toolbar);
        // Add up button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        
        loadRecyclerView();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setVisibility(View.GONE);
    }

    public void loadRecyclerView() {
        mRecyclerView = (RecyclerView)findViewById(R.id.rv);
        TextView emptyView = (TextView) findViewById(R.id.empty_view);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(llm);

        String titleText = "";
        ArrayList<File> fileList = new ArrayList<File>();
        mDbHelper = new DatabaseHelper(this.getApplicationContext());
        Intent intent = getIntent();
        if (intent.hasExtra(SELECTED_TAG)) {
            String selectedTagName = intent.getStringExtra(SELECTED_TAG);
            titleText = "Devotions about " + selectedTagName;
            fileList = mDbHelper.getAllFilesByTag(selectedTagName);
            mShowRemoveButton = false;
        }
        else if (intent.hasExtra(FAVORITES)) {
            titleText = intent.getStringExtra(FAVORITES);
            fileList = mDbHelper.getAllFavoriteFiles();
            mShowRemoveButton = true;
        }

        if (fileList.isEmpty()) {
            mRecyclerView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        } else {
            mRecyclerView.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
        }

        TextView tvSelectedTag = (TextView)findViewById(R.id.textView);
        tvSelectedTag.setText(titleText);

        RVAdapter adapter = new RVAdapter(fileList, new RVAdapter.OnItemClickListener() {
            @Override public void onItemClick(File file) {
                //Toast.makeText(FileListActivity.this.getApplicationContext(), "Item Clicked: " + file.getFileName(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(FileListActivity.this, ReadDevotionActivity.class);
                intent.putExtra(FILE_NAME, file.getFileName());
                startActivity(intent);
            }

            @Override
            public void onDelete(String fileName) {
                //Toast.makeText(FileListActivity.this.getApplicationContext(), "Item Removed: " + fileName, Toast.LENGTH_SHORT).show();
                int rowsUpdated = FileListActivity.this.mDbHelper.updateFile(fileName, 0);
                if (rowsUpdated < 0) {
                    String message = "Item may already be deleted from favorites. Try again later.";
                    Toast.makeText(FileListActivity.this.getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                }
            }
        }, mShowRemoveButton);
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onPause() {
        super.onPause();
        // save scroll position
        mLastFirstVisiblePosition = ((LinearLayoutManager) mRecyclerView.getLayoutManager()).findFirstCompletelyVisibleItemPosition();
    }

    @Override
    public void onResume() {
        super.onResume();
        // reload recyclerview to refresh favorites status of files
        loadRecyclerView();
        // set scroll position
        ((LinearLayoutManager) mRecyclerView.getLayoutManager()).scrollToPosition(mLastFirstVisiblePosition);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //here you can handle orientation change
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /*if (id == R.id.action_settings) {
            return true;
        }*/
        if (id == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        if (id == R.id.action_search) {
            Intent i = new Intent(this, SearchActivity.class);
            //i.putExtra(SEARCH_FRAGMENT, "FavoritesFragment");
            startActivity(i);
            //onSearchRequested();
        }
        if (id == R.id.action_favorite) {
            Intent intent = new Intent(this, FileListActivity.class);
            intent.putExtra(FAVORITES, "Your Favorites");
            startActivity(intent);
        }


        return super.onOptionsItemSelected(item);
    }

}
