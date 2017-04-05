package com.project.michael;

import android.app.ListActivity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Michael.Hui@ibm.com on 4/2/17.
 */

public class SearchableActivity extends ListActivity {

    private FileArrayAdapter adapter;
    private FileManager fileManager = new FileManager();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.search_result_view);
        List<Item> searchResult = new ArrayList<>();
        handleIntent(getIntent(), searchResult);
        adapter = new FileArrayAdapter(this,R.layout.file_view,searchResult);
        this.setListAdapter(adapter);
    }


    private void handleIntent(Intent intent, List<Item> searchResult) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            search_file("/sdcard", query, searchResult);
        }
    }

    private void search_file(String dir, String fileName, List<Item> searchResult) {
        File root_dir = new File(dir);
        String[] list = root_dir.list();

        if(list != null && root_dir.canRead()) {
            int len = list.length;

            for (int i = 0; i < len; i++) {
                File check = new File(dir + "/" + list[i]);
                String name = check.getName();

                if(check.isFile() && name.toLowerCase().
                        contains(fileName.toLowerCase())) {
                    Item item = new Item(name, check.length() + "Byte", fileManager.getFileDate(check), check.getAbsolutePath(), "file_icon" );
                    searchResult.add(item);
                }
                else if(check.isDirectory()) {
                    if(name.toLowerCase().contains(fileName.toLowerCase())){
                        Item item = new Item(name, fileManager.getFileDate(check), fileManager.getFileDate(check), check.getAbsolutePath(), "directory_icon");
                        searchResult.add(item);
                    }else if(check.canRead() && !dir.equals("/")){
                        search_file(check.getAbsolutePath(), fileName, searchResult);
                    }
                }
            }
        }
    }

}
