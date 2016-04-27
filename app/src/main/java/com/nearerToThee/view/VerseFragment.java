package com.nearerToThee.view;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.nearerToThee.R;
import com.nearerToThee.controller.Controller;

import java.io.IOException;

/**
 * A fragment containing the verse for the day.
 */
public class VerseFragment extends Fragment {

    private View mRootView;
    private Controller mController;
    private TextView mVerse;
    private ImageButton mRead;
    public final static String FILE_NAME = "com.nearerToThee.FILE_NAME";

    public VerseFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static VerseFragment newInstance(int sectionNumber) {
        VerseFragment fragment = new VerseFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mRootView = inflater.inflate(R.layout.fragment_verse, container, false);
        FloatingActionButton fab = (FloatingActionButton) mRootView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ReadDevotionActivity.class);
                String fileName = mController.getTodaysFileName();
                intent.putExtra(FILE_NAME, fileName);
                startActivity(intent);
            }
        });
        mController = Controller.getInstance();

        mVerse = (TextView) mRootView.findViewById(R.id.tvVerse);

        setImage();
        setVerse();

        return mRootView;
    }

    public void setVerse() {

        Typeface typeFace = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Gabriela-Regular.ttf");
        mVerse.setTypeface(typeFace);
        try {
            mVerse.setText(Html.fromHtml(mController.getTodaysVerse()));
        }
        catch (IOException ioe) {
            mRead.setEnabled(false);
            new AlertDialog.Builder(this.getActivity())
                    .setTitle("File Not Found")
                    .setMessage("Could not load devotion.")
                    .setPositiveButton(android.R.string.ok, null)
                    .show();
        }
    }

    public void setImage() {
        int id = mController.getImageForTheDay();
        mRootView.setBackgroundResource(id);
        // Alpha Values 0-255, 0 means fully transparent, and 255 means fully opaque
        mRootView.getBackground().setAlpha(255);
    }

    @Override
    public void onResume() {
        super.onResume();
        mRootView.getBackground().setAlpha(255);
    }

}
