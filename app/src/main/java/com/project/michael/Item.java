package com.project.michael;

/**
 * Created by huim2 on 2/18/2017.
 */


public class Item implements Comparable<Item>{
    private String name;
    private String data;
    private String date;
    private String path;
    private String image;

    public Item(String n, String d1, String d2, String p, String i)
    {
        name = n;
        data = d1;
        date = d2;
        path = p;
        image = i;
    }

    public String getName()
    {
        return name;
    }
    public String getData()
    {
        return data;
    }
    public String getDate()
    {
        return date;
    }
    public String getPath()
    {
        return path;
    }
    public String getImage() {
        return image;
    }

    public int compareTo(Item o) { //to check names by ignoring case
        if(this.name != null)
            return this.name.toLowerCase().compareTo(o.getName().toLowerCase());
        else
            throw new IllegalArgumentException();
    }
}

