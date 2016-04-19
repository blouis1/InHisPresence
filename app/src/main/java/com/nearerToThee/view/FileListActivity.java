package com.nearerToThee.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.nearerToThee.R;
import com.nearerToThee.data_access_layer.DatabaseHelper;
import com.nearerToThee.model.File;
import com.nearerToThee.utilities.FileArrayAdapter;

import java.util.ArrayList;

public class FileListActivity extends AppCompatActivity {

    public final static String SELECTED_TAG = "com.nearerToThee.SELECTED_TAG";
    public final static String FILE_NAME = "com.nearerToThee.FILE_NAME";
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_list);

        Intent intent = getIntent();
        String selectedTagName = intent.getStringExtra(SELECTED_TAG);

        TextView tvSelectedTag = (TextView)findViewById(R.id.textView);
        tvSelectedTag.setText("Devotions about " + selectedTagName);

        dbHelper = new DatabaseHelper(this.getApplicationContext());
        ArrayList<File> fileListByTag = dbHelper.getAllFilesByTag(selectedTagName);
        File[] fileArray = fileListByTag.toArray(new File[fileListByTag.size()]);

        FileArrayAdapter adapter = new FileArrayAdapter(this, R.layout.list_view_row_item, fileArray);
        // create a new ListView, set the adapter and item click listener
        ListView listViewItems = (ListView) findViewById(R.id.listview);
        listViewItems.setAdapter(adapter);
        listViewItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                TextView textViewItem = ((TextView) v.findViewById(R.id.textViewItem));

                // get the clicked item name
                String listItemText = textViewItem.getText().toString();

                // get the clicked item name
                String listItemName = textViewItem.getTag().toString();

                // just toast it
                Toast.makeText(getApplicationContext(), "Title: " + listItemText + ", File Name: " + listItemName, Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getApplicationContext(), ReadDevotionActivity.class);
                intent.putExtra(FILE_NAME, listItemName);
                startActivity(intent);


            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }




}



