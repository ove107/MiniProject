package com.example.tabianconsulting;

import android.net.Uri;

public class UploadImageInfo {
    String name;
    String ImageUrl;
    String timeStamp;


    public UploadImageInfo(String name, String imageUrl,String timeStamp) {
        this.name = name;
        ImageUrl = imageUrl;
        this.timeStamp = timeStamp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }
}
