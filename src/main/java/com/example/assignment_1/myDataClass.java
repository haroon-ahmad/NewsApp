package com.example.assignment_1;

import java.io.Serializable;

/**
 * Created by Haroon on 10/15/2015.
 */
public class myDataClass implements  Serializable {
    int ID;
    String title;
    String des;
    //String image;
    String link;
    String date;
    public myDataClass()
    {
        date="";
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    //public String getImage() {
     //   return image;

    public myDataClass(int ID, String title, String des, String link, String date) {
        this.ID = ID;
        this.title = title;
        this.des = des;
        this.link = link;
        this.date = date;
    }

    //}

   // public void setImage(String image) {
      //  this.image = image;
    //}

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public myDataClass(String title, String des, String link, String date) {
        this.title = title;
        this.des = des;
      //  this.image = image;
        this.link = link;
        this.date = date;
    }
}
