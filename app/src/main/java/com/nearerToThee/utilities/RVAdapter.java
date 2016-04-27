package com.nearerToThee.utilities;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.nearerToThee.R;
import com.nearerToThee.model.File;

import java.util.ArrayList;

/**
 * RecyclerView adapter class.
 * Created by Betsy on 4/22/2016.
 */
public class RVAdapter extends RecyclerView.Adapter<RVAdapter.FileViewHolder>{

    private ArrayList<File> mFiles;
    private OnItemClickListener mListener;
    private boolean mShowButton;

    public interface OnItemClickListener {
        void onItemClick(File item);
        void onDelete(String fileName);
    }

    public RVAdapter(ArrayList<File> fileList, OnItemClickListener listener, boolean showButton){
        this.mFiles = fileList;
        this.mListener = listener;
        this.mShowButton = showButton;
    }

    @Override
    public int getItemCount() {
        return mFiles.size();
    }

    @Override
    public FileViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_view, viewGroup, false);
        FileViewHolder pvh = new FileViewHolder(v);
        if (!mShowButton) {
            pvh.removeFavorite.setVisibility(View.GONE);
        }
        return pvh;
    }

    @Override
    public void onBindViewHolder(FileViewHolder fileViewHolder, int position) {
        fileViewHolder.bind(mFiles.get(position), mListener);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public void delete(int position) { //removes the row
        mFiles.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, mFiles.size());
    }

    public class FileViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView fileTitle;
        ImageView isFavorite;
        ImageButton removeFavorite;

        FileViewHolder(View cardView) {
            super(cardView);
            cv = (CardView)cardView.findViewById(R.id.cv);
            fileTitle = (TextView)cardView.findViewById(R.id.file_title);
            isFavorite = (ImageView)cardView.findViewById(R.id.isfavorite);
            removeFavorite = (ImageButton)cardView.findViewById(R.id.removeFavButton);
            removeFavorite.setColorFilter(Color.parseColor("#b20000"));
        }

        public void bind(final File file, final OnItemClickListener listener) {
            fileTitle.setText(file.getFileTitle());
            isFavorite.setImageResource(R.drawable.ic_action_favorite);

            if (file.getIsFavorite() == true) {
                int color = Color.parseColor("#b20000");
                isFavorite.setColorFilter(color);
            } else {
                int color = Color.parseColor("#ffffff");
                isFavorite.setColorFilter(color);
            }

            removeFavorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onDelete(file.getFileName());
                    delete(getAdapterPosition());
                }
            });


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(file);
                }
            });
        }

    }
}