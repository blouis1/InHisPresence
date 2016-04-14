package com.nearerToThee;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * A placeholder fragment containing a simple view.
 */
public class TopicsFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    Button ClickMe;
    TextView tv;

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
        View rootView = inflater.inflate(R.layout.my_fragment, container, false);

        ClickMe = (Button) rootView.findViewById(R.id.button);
        tv = (TextView) rootView.findViewById(R.id.textView2);

        ClickMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(tv.getText().toString().contains("Hello")){
                    tv.setText("Hi");
                }else tv.setText("Hello");
            }
        });

        return rootView;
    }
}
