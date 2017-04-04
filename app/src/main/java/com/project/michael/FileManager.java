package com.project.michael;

import java.io.File;
import java.sql.Date;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.app.Activity;
import android.app.ListActivity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

public class FileManager extends ListActivity {
	
    private File currentDirectory;
    private FileArrayAdapter adapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//		setContentView(R.layout.main);
		currentDirectory = new File("/sdcard/");
        fill(currentDirectory);
    }
    private void fill(File f)
    {
    	File[]dirs = f.listFiles();
		 this.setTitle("Current Directory: "+f.getName());
		 List<Item>dir = new ArrayList<>();
		 List<Item>fls = new ArrayList<>();
		 try{
			 for(File ff: dirs)
			 {
				 if(ff.isDirectory()) {
                     dir.add(new Item(ff.getName(), getItemNum(ff), getFileDate(ff), ff.getAbsolutePath(), "directory_icon"));
                 }else {
					fls.add(new Item(ff.getName(),ff.length() + " Byte",getFileDate(ff),ff.getAbsolutePath(),"file_icon"));
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
		 adapter = new FileArrayAdapter(FileManager.this,R.layout.file_view,dir);
		 this.setListAdapter(adapter);
    }
    @Override
	protected void onListItemClick(ListView l, View v, int position, long id) {

		super.onListItemClick(l, v, position, id);
		Item o = adapter.getItem(position);
		if(o.getImage().equalsIgnoreCase("directory_icon")||o.getImage().equalsIgnoreCase("directory_up")){ //if it is a folder, generate the new view
			currentDirectory = new File(o.getPath());
			fill(currentDirectory);
		}
		else
		{
			onFileClick(o); //if it is file, display the name
		}
	}
    private void onFileClick(Item o)
    {
    	Toast.makeText(this, "File Selected: "+ o.getName() + "\n" + o.getData() + " Byte" + "\n" + "Last Modified: " + o.getDate(), Toast.LENGTH_SHORT).show();
		//Intent intent = new Intent();
		//intent.putExtra("GetPath",currentDirectory.toString());
		//intent.putExtra("GetFileName",o.getName());
		//setResult(RESULT_OK, intent);
		//finish();
    }
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main_menu, menu);
		SearchManager searchManager =
				(SearchManager) getSystemService(Context.SEARCH_SERVICE);
		SearchView searchView =
				(SearchView) menu.findItem(R.id.action_search).getActionView();
		searchView.setSearchableInfo(
				searchManager.getSearchableInfo(getComponentName()));

		return true;

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		switch (id) {
			case R.id.action_search:
				toast("Search action ...");
				break;
			case R.id.action_settings:
				toast("Settings action ...");
				break;
			case R.id.action_about:
				toast("About action ...");
				break;
			case android.R.id.home:
				toast("Home button ...");
				break;
			default:
				toast("unknown action ...");
		}

		return true;
	}

	private void toast(String msg) {
		Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
	}

	private void handleIntent(Intent intent) {

		if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
			String query = intent.getStringExtra(SearchManager.QUERY);
			//use the query to search your data somehow
		}
	}
	public String getFileDate(File file){
		Date lastModDate = new Date(file.lastModified());
		DateFormat formater = DateFormat.getDateTimeInstance();
		return formater.format(lastModDate);
	}

	public String getItemNum(File file){
		File[] fbuf = file.listFiles();
		int buf = 0;
		if (fbuf != null) {
			buf = fbuf.length;
		} else buf = 0;
		String num_item = String.valueOf(buf);
		if (buf == 0) num_item = num_item + " item";
		else num_item = num_item + " items";
		return num_item;
	}

}