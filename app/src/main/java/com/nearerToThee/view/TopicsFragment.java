package com.nearerToThee.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.nearerToThee.R;
import com.nearerToThee.controller.Controller;
import com.nearerToThee.data_access_layer.DatabaseHelper;
import com.nearerToThee.model.Tag;

import java.util.ArrayList;

/**
 * A fragment containing the topics to choose from.
 */
public class TopicsFragment extends Fragment {

    private DatabaseHelper mDbHelper;
    public final static String SELECTED_TAG = "com.nearerToThee.SELECTED_TAG";
    private Controller mController;
    private View mRootView;

    public TopicsFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static TopicsFragment newInstance() {
        TopicsFragment fragment = new TopicsFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mRootView = inflater.inflate(R.layout.fragment_topics, container, false);
        mController = Controller.getInstance();
        mRootView.setBackgroundResource(mController.getImageForTheDay());

        mDbHelper = new DatabaseHelper(this.getActivity().getApplicationContext());
        ArrayList<Tag> tagList = mDbHelper.getAllTags();

        GridView gridview = (GridView) mRootView.findViewById(R.id.gridViewCustom);
        gridview.setAdapter(new TextViewAdapter(getActivity(), tagList));

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                TextView textView = (TextView)v.findViewById(R.id.grid_item_label);
                String selectedTagName = textView.getText().toString();

                Intent intent = new Intent(getActivity(), FileListActivity.class);
                intent.putExtra(SELECTED_TAG, selectedTagName);
                startActivity(intent);
            }
        });

        return mRootView;
    }

    public class TextViewAdapter extends BaseAdapter {
        private Context context;
        private final Tag[] tagList;

        public TextViewAdapter(Context context, ArrayList<Tag> tagList) {
            this.context = context;
            this.tagList = tagList.toArray(new Tag[tagList.size()]);
        }

        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View gridViewItem;

            if (convertView == null) {

                // get layout from grid_item.xml
                gridViewItem = inflater.inflate(R.layout.grid_item, null);

                // set value into textview
                TextView textView = (TextView) gridViewItem
                        .findViewById(R.id.grid_item_label);
                Typeface typeFace = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Gabriela-Regular.ttf");
                textView.setTypeface(typeFace);
                textView.setText(tagList[position].getTagName());
            } else {
                gridViewItem = (View) convertView;
            }

            return gridViewItem;
        }

        @Override
        public int getCount() {
            return tagList.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

    }

}
