package com.example.imgur.Models;

import android.widget.Toast;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

public class Image {

    private String id;
    private String title;
    private String description;
    private String link;
    private String privacy;
    private String type;
    private String mp4;
    private String error;

    public Image(String id, String title, String description, String link, String privacy, String type, String mp4) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.link = link;
        this.privacy = privacy;
        this.type = type;
        this.mp4 = mp4;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getPrivacy() {
        if (privacy == null) {
            return "empty";
        }
        else {
            return privacy;
        }
    }

    public void setPrivacy(String privacy) {
        this.privacy = privacy;
    }

    public String getType() {
        if (type == null) {
            return "empty";
        }
        else {
            return type;
        }
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMp4() {
        if (mp4 == null){
            return "empty";
        }
        else {
            return mp4;
        }
    }

    public void setMp4(String mp4) {
        this.mp4 = mp4;
    }

    public String getError() {
        if (error == null){
            return "empty";
        }
        else {
            return error;
        }
    }

    public void setError(String error) {
        this.error = error;
    }
}
