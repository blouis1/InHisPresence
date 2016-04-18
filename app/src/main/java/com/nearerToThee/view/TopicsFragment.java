package com.nearerToThee.view;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nearerToThee.R;
import com.nearerToThee.data_access_layer.DatabaseHelper;
import com.nearerToThee.model.Tag;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class TopicsFragment extends Fragment {

    private DatabaseHelper dbHelper;

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

        //String[] textViewValues = {"Anxiety & Worry", "Joy", "Peace", "Love", "Despair", "Hope", "Classic"};

        View rootView = inflater.inflate(R.layout.fragment_topics, container, false);

        dbHelper = new DatabaseHelper(this.getActivity().getApplicationContext());
        ArrayList<Tag> tagList = dbHelper.getAllTags();

        GridView gridview = (GridView) rootView.findViewById(R.id.gridViewCustom);
        gridview.setAdapter(new TextViewAdapter(getActivity(), tagList));

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Toast.makeText(getActivity(), "" + position,
                        Toast.LENGTH_SHORT).show();
            }
        });

        return rootView;
    }

    public class TextViewAdapter extends BaseAdapter {
        private Context context;
        //private final String[] textViewValues;
        private final Tag[] tagList;

        public TextViewAdapter(Context context, ArrayList<Tag> tagList) {
            this.context = context;
            this.tagList = tagList.toArray(new Tag[tagList.size()]);
        }

        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View gridView;

            if (convertView == null) {

                gridView = new View(context);

                // get layout from mobile.xml
                gridView = inflater.inflate(R.layout.grid_row, null);

                // set value into textview
                TextView textView = (TextView) gridView
                        .findViewById(R.id.grid_item_label);
                textView.setText(tagList[position].getTagName());
            } else {
                gridView = (View) convertView;
            }

            return gridView;
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

    /*public class ImageAdapter extends BaseAdapter {
        private Context mContext;

        public ImageAdapter(Context c) {
            mContext = c;
        }

        public int getCount() {
            //return mThumbIds.length;
            return 10;
        }

        public Object getItem(int position) {
            return null;
        }

        public long getItemId(int position) {
            return 0;
        }

        // create a new ImageView for each item referenced by the Adapter
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView;
            if (convertView == null) {
                // if it's not recycled, initialize some attributes
                imageView = new ImageView(mContext);
                imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setPadding(8, 8, 8, 8);
            } else {
                imageView = (ImageView) convertView;
            }

            //imageView.setImageResource(mThumbIds[position]);
            imageView.setBackgroundColor(Color.BLUE);
            return imageView;
        }

        // references to our images
        /*private Integer[] mThumbIds = {
                R.drawable.sample_2, R.drawable.sample_3,
                R.drawable.sample_4, R.drawable.sample_5,
                R.drawable.sample_6, R.drawable.sample_7,
                R.drawable.sample_0, R.drawable.sample_1,
                R.drawable.sample_2, R.drawable.sample_3,
                R.drawable.sample_4, R.drawable.sample_5,
                R.drawable.sample_6, R.drawable.sample_7,
                R.drawable.sample_0, R.drawable.sample_1,
                R.drawable.sample_2, R.drawable.sample_3,
                R.drawable.sample_4, R.drawable.sample_5,
                R.drawable.sample_6, R.drawable.sample_7
        };
    } */
}
