package com.project.michael;

import java.io.File;
import java.sql.Date;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
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
		 List<Item>dir = new ArrayList<Item>();
		 List<Item>fls = new ArrayList<Item>();
		 try{
			 for(File ff: dirs)
			 {
				 Date lastModDate = new Date(ff.lastModified());
				 DateFormat formater = DateFormat.getDateTimeInstance();
				 String modeifiedDate = formater.format(lastModDate);
				 if(ff.isDirectory()) {
                     File[] fbuf = ff.listFiles();
                     int buf = 0;
                     if (fbuf != null) {
                         buf = fbuf.length;
                     } else buf = 0;
                     String num_item = String.valueOf(buf);
                     if (buf == 0) num_item = num_item + " item";
                     else num_item = num_item + " items";
                     dir.add(new Item(ff.getName(), num_item, modeifiedDate, ff.getAbsolutePath(), "directory_icon"));
                 }else {
					fls.add(new Item(ff.getName(),ff.length() + " Byte",modeifiedDate,ff.getAbsolutePath(),"file_icon"));
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
}