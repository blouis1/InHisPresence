package com.nearerToThee.view;

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
        mController = new Controller(mRootView.getContext());
        ApplicationClass appClass = (ApplicationClass)getActivity().getApplicationContext();
        appClass.setController(mController);
        mVerse = (TextView) mRootView.findViewById(R.id.tvVerse);
        setImage();
        setVerse();
        mRead = (ImageButton)mRootView.findViewById(R.id.ibRead);
        mRead.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TodaysReadingActivity.class);
                startActivity(intent);
            }
        });
        return mRootView;
    }

    public void setVerse() {
        try {
            String text_string = mController.getVerse();
            Spanned text = Html.fromHtml(text_string);
            mVerse.setText(text);        }
        catch (IOException ioe){
            // show error
            Log.d("FILE_ERROR", ioe.getMessage());
        }
    }

    public void setImage() {
        mRootView.setBackgroundResource(mController.getImageForTheDay());

    }
}
