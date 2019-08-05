package com.example.imgur.Models;

public class ImageResponse {

    private Image data;
    private Boolean success;
    private String status;

    public ImageResponse(Image data, Boolean success, String status) {
        this.data = data;
        this.success = success;
        this.status = status;
    }

    public Image getData() {
        return data;
    }

    public void setData(Image data) {
        this.data = data;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

