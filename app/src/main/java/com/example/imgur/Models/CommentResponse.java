package com.example.imgur.Models;

import java.util.ArrayList;

public class CommentResponse {

    private ArrayList<Comment> data;
    private String success;
    private String status;

    public CommentResponse(ArrayList<Comment> data, String success, String status) {
        this.data = data;
        this.success = success;
        this.status = status;
    }

    public ArrayList<Comment> getData() {
        return data;
    }

    public void setData(ArrayList<Comment> data) {
        this.data = data;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
