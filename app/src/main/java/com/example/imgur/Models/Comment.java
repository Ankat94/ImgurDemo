package com.example.imgur.Models;

public class Comment {

    private String id;
    private String comment;
    private String author;

    public Comment(String id, String comment, String author) {
        this.id = id;
        this.comment = comment;
        this.author = author;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
