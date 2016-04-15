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
        Calendar calendar;
        calendar = Calendar.getInstance();
        int day_of_week = calendar.get(Calendar.DAY_OF_WEEK); //(1-7 means sunday - saturday)
        switch(day_of_week) {

            case 1: //Sun
                mRootView.setBackgroundResource(R.drawable.scenery2);
                //mRootView.getBackground().setAlpha(75);
                break;

            case 2: //Mon
                mRootView.setBackgroundResource(R.drawable.flower1);
                //mRootView.getBackground().setAlpha(120);
                break;

            case 3: //Tue
                mRootView.setBackgroundResource(R.drawable.orchid);
                //mRootView.getBackground().setAlpha(75);
                break;

            case 4: //Wed
                mRootView.setBackgroundResource(R.drawable.flower);
                //mRootView.getBackground().setAlpha(120);
                break;

            case 5: //Thu
                mRootView.setBackgroundResource(R.drawable.swan);
                //mRootView.getBackground().setAlpha(90);
                break;

            case 6: //Fri
                mRootView.setBackgroundResource(R.drawable.tulip_field);
                mRootView.setTag(R.drawable.tulip_field);
                //mRootView.getBackground().setAlpha(75);
                break;

            case 7: //Sat
                mRootView.setBackgroundResource(R.drawable.pink_flower2);
                //mRootView.getBackground().setAlpha(90);
                break;

            default:
                mRootView.setBackgroundResource(R.drawable.flower);
                //mRootView.getBackground().setAlpha(120);
        }

    }
}
