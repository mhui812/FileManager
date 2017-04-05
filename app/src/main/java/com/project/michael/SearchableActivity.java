package com.project.michael;

import android.app.ListActivity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Michael.Hui@ibm.com on 4/2/17.
 */

public class SearchableActivity extends ListActivity {

    private FileArrayAdapter adapter;
    private FileManager fileManager = new FileManager();
    private File currentDirectory;

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
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {

        super.onListItemClick(l, v, position, id);
        Item item = adapter.getItem(position);
        if(item.getImage().equalsIgnoreCase("directory_icon")||item.getImage().equalsIgnoreCase("directory_up")){ //if it is a folder, generate the new view
            currentDirectory = new File(item.getPath());
            fill(currentDirectory);
        }
        else
        {
            onFileClick(item); //if it is file, display the name
        }
    }
    private void onFileClick(Item item)
    {
        Toast.makeText(this, "File Selected: "+ item.getName() + "\n" + item.getData() + " Byte" + "\n" + "Last Modified: " + item.getDate(), Toast.LENGTH_SHORT).show();
        //Intent intent = new Intent();
        //intent.putExtra("GetPath",currentDirectory.toString());
        //intent.putExtra("GetFileName",o.getName());
        //setResult(RESULT_OK, intent);
        //finish();
    }
    private void fill(File f)
    {
        File[]dirs = f.listFiles();
        this.setTitle("Current Directory: "+f.getName());
        List<Item> dir = new ArrayList<>();
        List<Item> fls = new ArrayList<>();
        try{
            for(File ff: dirs)
            {
                if(ff.isDirectory()) {
                    dir.add(new Item(ff.getName(), fileManager.getItemNum(ff), fileManager.getFileDate(ff), ff.getAbsolutePath(), "directory_icon"));
                }else {
                    fls.add(new Item(ff.getName(),ff.length() + " Byte",fileManager.getFileDate(ff),ff.getAbsolutePath(),"file_icon"));
                }
            }
        }catch(Exception e)
        {

        }
        Collections.sort(dir);
        Collections.sort(fls);
        dir.addAll(fls);
        if(!f.getName().equalsIgnoreCase("sdcard"))
            dir.add(0,new Item("..","Parent Directory","",f.getParent(),"directory_up"));
        adapter = new FileArrayAdapter(this,R.layout.file_view,dir);
        this.setListAdapter(adapter);
    }

}
