package com.nearerToThee.view;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.nearerToThee.R;
import com.nearerToThee.controller.Controller;
import com.nearerToThee.utilities.ApplicationClass;

import java.io.IOException;
import java.util.Calendar;

/**
 * A placeholder fragment containing a simple view.
 */
public class DevotionFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    //private static final String ARG_SECTION_NUMBER = "section_number";

    private View mRootView;
    private Controller mController;
    private TextView mVerse;
    private ImageButton mRead;

    public DevotionFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static DevotionFragment newInstance(int sectionNumber) {
        DevotionFragment fragment = new DevotionFragment();
        //Bundle args = new Bundle();
        //args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        //fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_devotion, container, false);
        mController.initialize(this.getActivity().getApplicationContext());
        try {
            mController = Controller.getInstance();
        }
        catch (IOException ioe) {
            new AlertDialog.Builder(this.getActivity())
                    .setTitle("File Not Found")
                    .setMessage("Could not load today's devotion.")
                    .setPositiveButton(android.R.string.ok, null)
                    .show();
        }

        //ApplicationClass appClass = (ApplicationClass)getActivity().getApplicationContext();

        //appClass.setController(mController);
        mVerse = (TextView) mRootView.findViewById(R.id.tvVerse);
        mRead = (ImageButton)mRootView.findViewById(R.id.ibRead);
        mRead.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TodaysReadingActivity.class);
                startActivity(intent);
            }
        });
        setImage();
        setVerse();

        return mRootView;
    }

    public void setVerse() {
        mVerse.setText(Html.fromHtml(mController.getVerse()));
    }

    public void setImage() {
        int id = mController.getImageForTheDay();
        mRootView.setBackgroundResource(id);
        //	Alpha Values 0-255, 0 means fully transparent, and 255 means fully opaque
        mRootView.getBackground().setAlpha(255);
    }
}
