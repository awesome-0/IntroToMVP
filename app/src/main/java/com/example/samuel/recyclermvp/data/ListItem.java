package com.example.samuel.recyclermvp.data;

import java.io.Serializable;

/**
 * Created by Samuel on 23/12/2017.
 */

public class ListItem implements Serializable{
    private String message,dateTime;
    private int colorResource;


    public ListItem(String message, String dateTime, int colorResource) {
        this.message = message;
        this.dateTime = dateTime;
        this.colorResource = colorResource;
    }

    public ListItem() {
    }

    @Override
    public String toString() {
        return "ListItem{" +
                "message='" + message + '\'' +
                ", dateTime='" + dateTime + '\'' +
                ", colorResource=" + colorResource +
                '}';
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public int getColorResource() {
        return colorResource;
    }

    public void setColorResource(int colorResource) {
        this.colorResource = colorResource;
    }
}
