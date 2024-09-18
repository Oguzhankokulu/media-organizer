package com.oguzhan;


import java.io.Serializable;

import javafx.scene.image.Image;

public class ItemView implements Serializable{
    private String name, runTime, notes, rating, page, posterUrl;
    public ItemView(String name, String runTime, String rating, String notes, String page) {
        this.name = name;
        this.runTime = runTime;
        this.rating = rating;
        this.notes = notes;
        this.page = page;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRunTime() {
        return runTime;
    }

    public void setRunTime(String runTime) {
        this.runTime = runTime;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
    
    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public Image getPoster(){
        if (posterUrl == null) {
            return null;
        } else {
            return new Image(posterUrl);
        }
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }


    public String getPage() {
        return page;
    }
}
