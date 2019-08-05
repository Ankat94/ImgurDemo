package com.example.imgur.NetworkUtils;

import com.example.imgur.Models.Comment;

import java.util.ArrayList;

public interface CommentListener {
    void commentReceived(ArrayList<Comment> comments);
}
