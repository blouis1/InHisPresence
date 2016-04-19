package com.nearerToThee.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.nearerToThee.R;
import com.nearerToThee.data_access_layer.DatabaseHelper;
import com.nearerToThee.model.File;
import com.nearerToThee.utilities.FileArrayAdapter;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class SearchFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private DatabaseHelper dbHelper;
    private AutoCompleteTextView searchBox;
    private ListView listView;
    private Button searchButton;
    private TextView tvNoResults;
    private ArrayAdapter<String> adapter;
    public final static String FILE_NAME = "com.nearerToThee.FILE_NAME";

    public SearchFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static SearchFragment newInstance() {
        SearchFragment fragment = new SearchFragment();
        /*Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args); */
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_search, container, false);
        searchBox = (AutoCompleteTextView) rootView.findViewById(R.id.autocomplete_keywords);
        listView = (ListView) rootView.findViewById(R.id.lvSearchResults);
        tvNoResults = (TextView)rootView.findViewById(android.R.id.empty);
        listView.setEmptyView(tvNoResults);
        dbHelper = new DatabaseHelper(getActivity().getApplicationContext());
        searchButton = (Button)rootView.findViewById(R.id.btnSearch);
        searchButton.setEnabled(false);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performSearch();
            }
        });

        // to show enter key on keyboard
        searchBox.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    performSearch();
                }
                return false;
            }
        });

        searchBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (searchBox.getText().toString().trim().length()==0) {
                    listView.setAdapter(null);
                }
                searchButton.setEnabled(searchBox.getText().toString().trim().length()!=0);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        // Get the string array of keywords
        ArrayList<String> keywordList = dbHelper.getAllKeywords();
        String[] keywords = keywordList.toArray(new String[keywordList.size()]);
        // Create the adapter and set it to the AutoCompleteTextView
        adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_list_item_1, keywords);
        searchBox.setAdapter(adapter);
        return rootView;
    }

    public void performSearch() {
        String keyword = searchBox.getText().toString();
        searchBox.clearFocus();
        InputMethodManager in = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        in.hideSoftInputFromWindow(searchBox.getWindowToken(), 0);

        ArrayList<File> fileListByTag = dbHelper.getFilesFromKeyword(keyword);
        File[] fileArray = fileListByTag.toArray(new File[fileListByTag.size()]);

        FileArrayAdapter adapter = new FileArrayAdapter(getActivity(), R.layout.list_view_row_item, fileArray);
        // create a new ListView, set the adapter and item click listener

        listView.setAdapter(adapter);
        if (listView.getCount() == 0) {
            Toast.makeText(getActivity().getApplicationContext(), "No devotions available for that keyword", Toast.LENGTH_SHORT).show();

        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                TextView textViewItem = ((TextView) v.findViewById(R.id.textViewItem));

                // get the clicked item name
                String listItemText = textViewItem.getText().toString();

                // get the clicked item ID
                String listItemName = textViewItem.getTag().toString();

                // just toast it
                Toast.makeText(getActivity().getApplicationContext(), "Title: " + listItemText + ", File Name: " + listItemName, Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getActivity().getApplicationContext(), ReadDevotionActivity.class);
                intent.putExtra(FILE_NAME, listItemName);
                startActivity(intent);
            }
        });

    }
}
