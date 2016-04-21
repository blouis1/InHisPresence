package com.nearerToThee.utilities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.nearerToThee.R;
import com.nearerToThee.model.File;

/**
 * Created by Betsy on 4/19/2016.
 */
public class FileArrayAdapter extends ArrayAdapter<File> {

    Context mContext;
    int layoutResourceId;
    File file[] = null;

    public FileArrayAdapter(Context mContext, int layoutResourceId, File[] data) {

        super(mContext, layoutResourceId, data);

        this.layoutResourceId = layoutResourceId;
        this.mContext = mContext;
        this.file = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        /*
         * The convertView argument is essentially a "ScrapView"
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
        long file_id = File.getId();
        String title = File.getFileTitle();
        boolean isfav = File.getIsFavorite();

        // get the TextView and then set the text (item name) and tag (item ID) values
        TextView textViewItem = (TextView) convertView.findViewById(R.id.textViewItem);
        String fileName = File.getFileTitle();
        textViewItem.setText(File.getFileTitle());
        textViewItem.setTag(File.getFileName());

        return convertView;

    }

    static class ViewHolder {
        public TextView text;
    }


}
