package com.example.imgur.NetworkUtils;

import com.example.imgur.Models.Image;

import java.util.ArrayList;

public interface GalleryListener {
    void galleryReceived(ArrayList<Image> images);
}
