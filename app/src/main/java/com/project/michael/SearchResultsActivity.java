package com.project.michael;

import android.app.ListActivity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Michael.Hui@ibm.com on 4/2/17.
 */

public class SearchResultsActivity extends ListActivity {

    private FileArrayAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_result_view);
        List<Item> dir = new ArrayList<>();
        handleIntent(getIntent(), dir);
        adapter = new FileArrayAdapter(this,R.layout.file_view,dir);
        this.setListAdapter(adapter);
    }


    private void handleIntent(Intent intent, List<Item> dir) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            //use the query to search your data somehow
        }
    }

}
