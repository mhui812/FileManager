package com.project.michael;


import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class FileArrayAdapter extends ArrayAdapter<Item>{

	private Context c;
	private int id;
	private List<Item>items;
	
	public FileArrayAdapter(Context context, int textViewResourceId,
			List<Item> objects) {
		super(context, textViewResourceId, objects);
		c = context;
		id = textViewResourceId;
		items = objects;
	}
	public Item getItem(int i)
	 {
		 return items.get(i);
	 }
	@Override
    public View getView(int position, View convertView, ViewGroup parent) {
	   View v = convertView;
	   if (v == null) { //if null, display nothing
		   LayoutInflater vi = (LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		   v = vi.inflate(id, null);
	   }
	   final Item o = items.get(position);
	   if (o != null) {
		   TextView t1 = (TextView) v.findViewById(R.id.TextView01);
		   TextView t2 = (TextView) v.findViewById(R.id.TextView02);
		   TextView t3 = (TextView) v.findViewById(R.id.TextViewDate);

		   ImageView imageCity = (ImageView) v.findViewById(R.id.fd_Icon1);
		   String uri = "raw/" + o.getImage();
		   int imageResource = c.getResources().getIdentifier(uri, null, c.getPackageName());
		   Drawable image = c.getResources().getDrawable(imageResource, null);
		   imageCity.setImageDrawable(image);

		   if(t1!=null) { //display name
			   t1.setText(o.getName());
		   }
		   if(t2!=null) { //display type and date
			   t2.setText(o.getData());
		   }
		   if(t3!=null) {
			   t3.setText(o.getDate());
		   }
	   }
		   return v;
    }

}
