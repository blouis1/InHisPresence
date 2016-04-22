package com.nearerToThee.utilities;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nearerToThee.R;
import com.nearerToThee.model.File;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Betsy on 4/22/2016.
 */
public class RVAdapter extends RecyclerView.Adapter<RVAdapter.FileViewHolder>{

    private ArrayList<File> files;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(File item);
    }

    public RVAdapter(ArrayList<File> fileList, OnItemClickListener listener){
        this.files = fileList;
        this.listener = listener;
    }

    @Override
    public int getItemCount() {
        return files.size();
    }

    @Override
    public FileViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_view, viewGroup, false);
        FileViewHolder pvh = new FileViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(FileViewHolder fileViewHolder, int position) {
        fileViewHolder.bind(files.get(position), listener);
        //fileViewHolder.isFavorite.setImageResource(R.drawable.ic_action_favorite);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public static class FileViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView fileTitle;
        ImageView isFavorite;

        FileViewHolder(View cardView) {
            super(cardView);
            cv = (CardView)cardView.findViewById(R.id.cv);
            fileTitle = (TextView)cardView.findViewById(R.id.file_title);
            isFavorite = (ImageView)cardView.findViewById(R.id.isfavorite);
        }

        public void bind(final File file, final OnItemClickListener listener) {
            fileTitle.setText(file.getFileTitle());
            isFavorite.setImageResource(R.drawable.ic_action_favorite);
            boolean isfav = file.getIsFavorite();
            if (file.getIsFavorite() == true) {
                int color = Color.parseColor("#b20000");
                isFavorite.setColorFilter(color);
            }

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(file);
                }
            });
        }

    }
}