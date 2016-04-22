package com.nearerToThee.view;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.nearerToThee.R;
import com.nearerToThee.data_access_layer.DatabaseHelper;
import com.nearerToThee.model.File;
import com.nearerToThee.utilities.RVAdapter;

import java.util.ArrayList;

public class FileListActivity extends AppCompatActivity {

    public final static String SELECTED_TAG = "com.nearerToThee.SELECTED_TAG";
    public final static String FAVORITES = "com.nearerToThee.FAVORITES";
    public final static String FILE_NAME = "com.nearerToThee.FILE_NAME";
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_file_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("NearerToThee");
        setSupportActionBar(toolbar);
        // Add up button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.rv);
        TextView emptyView = (TextView) findViewById(R.id.empty_view);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(llm);

        String titleText = "";
        ArrayList<File> fileList = new ArrayList<File>();
        dbHelper = new DatabaseHelper(this.getApplicationContext());
        Intent intent = getIntent();
        if (intent.hasExtra(SELECTED_TAG)) {
            String selectedTagName = intent.getStringExtra(SELECTED_TAG);
            titleText = "Devotions about " + selectedTagName;
            fileList = dbHelper.getAllFilesByTag(selectedTagName);
        }
        else if (intent.hasExtra(FAVORITES)) {
            titleText = intent.getStringExtra(FAVORITES);
            fileList = dbHelper.getAllFavoriteFiles();
        }

        if (fileList.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
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
        });
        recyclerView.setAdapter(adapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setVisibility(View.GONE);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //here you can handle orientation change
    }
}
