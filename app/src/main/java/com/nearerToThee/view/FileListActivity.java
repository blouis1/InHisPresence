package com.nearerToThee.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.nearerToThee.R;
import com.nearerToThee.data_access_layer.DatabaseHelper;
import com.nearerToThee.model.File;

import java.util.ArrayList;

public class FileListActivity extends AppCompatActivity {

    public final static String SELECTED_TAG = "com.nearerToThee.SELECTED_TAG";
    private DatabaseHelper dbHelper;
    AlertDialog alertDialogStores;

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

        ArrayAdapterItem adapter = new ArrayAdapterItem(this, R.layout.list_view_row_item, fileArray);
        // create a new ListView, set the adapter and item click listener
        ListView listViewItems = (ListView) findViewById(R.id.listview);
        listViewItems.setAdapter(adapter);
        listViewItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                TextView textViewItem = ((TextView) v.findViewById(R.id.textViewItem));

                // get the clicked item name
                String listItemText = textViewItem.getText().toString();

                // get the clicked item ID
                String listItemId = textViewItem.getTag().toString();

                // just toast it
                Toast.makeText(getApplicationContext(), "Item: " + listItemText + ", Item ID: " + listItemId, Toast.LENGTH_SHORT).show();


            }
        });

        //Toast.makeText(this, "Received: " + selectedTagName, Toast.LENGTH_SHORT).show();

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

    static class ViewHolder {
        public TextView text;
    }

    // here's our beautiful adapter
    public class ArrayAdapterItem extends ArrayAdapter<File> {

        Context mContext;
        int layoutResourceId;
        File file[] = null;

        public ArrayAdapterItem(Context mContext, int layoutResourceId, File[] data) {

            super(mContext, layoutResourceId, data);

            this.layoutResourceId = layoutResourceId;
            this.mContext = mContext;
            this.file = data;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

        /*
         * The convertView argument is essentially a "ScrapView" as described is Lucas post
         * http://lucasr.org/2012/04/05/performance-tips-for-androids-listview/
         * It will have a non-null value when ListView is asking you recycle the row layout.
         * So, when convertView is not null, you should simply update its contents instead of inflating a new row layout.
         */
            if (convertView == null) {
                // inflate the layout
                LayoutInflater inflater = (LayoutInflater) mContext
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(layoutResourceId, parent, false);
            }

            // object item based on the position
            File File = file[position];

            // get the TextView and then set the text (item name) and tag (item ID) values
            TextView textViewItem = (TextView) convertView.findViewById(R.id.textViewItem);
            textViewItem.setText(File.getFileTitle());
            textViewItem.setTag(File.getId());

            return convertView;

        }

        /*
     * Here you can control what to do next when the user selects an item
     */
        public class OnItemClickListenerListViewItem implements AdapterView.OnItemClickListener {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Context context = view.getContext();

                TextView textViewItem = ((TextView) view.findViewById(R.id.textViewItem));

                // get the clicked item name
                String listItemText = textViewItem.getText().toString();

                // get the clicked item ID
                String listItemId = textViewItem.getTag().toString();

                // just toast it
                Toast.makeText(context, "Item: " + listItemText + ", Item ID: " + listItemId, Toast.LENGTH_SHORT).show();

                ((FileListActivity) context).alertDialogStores.cancel();

            }

        }

    }
}



