package com.example.imgur.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;
import com.bumptech.glide.Glide;
import com.example.imgur.Activity.ImageActivity;
import com.example.imgur.Models.Image;
import com.example.imgur.NetworkUtils.ApiManager;
import com.example.imgur.NetworkUtils.VideoListener;
import com.example.imgur.R;
import com.google.gson.Gson;
import java.io.File;
import java.util.ArrayList;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageHolder> {

    private Context context;
    private ArrayList<Image> images;
    ApiManager apiManager;

    public ImageAdapter(Context context, ArrayList<Image> images) {
        this.context = context;
        this.images = images;
        apiManager = new ApiManager(context);
    }

    @NonNull
    @Override
    public ImageHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_gallery_image,viewGroup, false);
        return new ImageHolder(view, apiManager);
    }

    @Override
    public void onBindViewHolder(@NonNull final ImageHolder imageHolder, @SuppressLint("RecyclerView") final int i) {
        final Image image = images.get(i);
        Glide.with(context).load(image.getLink()).placeholder(R.drawable.placeholder).into(imageHolder.galleryImage);
        imageHolder.galleryText.setText(image.getTitle());
        imageHolder.image = image;
        if (image.getType().equals("image/gif") || image.getType().equals("image/mp4")) {
            if (imageHolder.galleryVideo.getDuration() == -1) {
                File path = Environment.getExternalStorageDirectory();
                File file = new File(path, image.getId() + ".mp4");
                MediaController mediaController = new MediaController(context);
                imageHolder.galleryVideo.setMediaController(mediaController);

                if (file.exists()) {
                    imageHolder.playVideo(file);
                }
                else {
                    imageHolder.downloadVideo(file);
                }
            }
            else {
                imageHolder.galleryVideo.resume();
            }
        }

        imageHolder.mainLayout.setOnClickListener(view -> {
            String s = image.getPrivacy();
            if (s.equalsIgnoreCase("empty")) {
                Gson gson = new Gson();
                String imageStringObject = gson.toJson(image);
                Intent ImageIntent = new Intent(context, ImageActivity.class);
                ImageIntent.putExtra("imageObject", imageStringObject);
                context.startActivity(ImageIntent);
            }
            else {
                Toast.makeText(context,"This Image is Private", Toast.LENGTH_SHORT).show();
            }

        });
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    public static class ImageHolder extends RecyclerView.ViewHolder {

        ImageView galleryImage;
        TextView galleryText;
        ConstraintLayout mainLayout;
        VideoView galleryVideo;
        ApiManager apiManager;
        Image image;
        boolean played = false;

        public ImageHolder(@NonNull View itemView, ApiManager apiManager) {
            super(itemView);
            galleryText = itemView.findViewById(R.id.gallery_title);
            galleryImage = itemView.findViewById(R.id.gallery_image);
            mainLayout = itemView.findViewById(R.id.row_main_layout);
            galleryVideo = itemView.findViewById(R.id.gallery_video);
            this.apiManager = apiManager;
        }

        @SuppressLint("StaticFieldLeak")
        public void downloadVideo(final File file) {
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... voids) {
                    apiManager.getMp4(image, file, new VideoListener() {
                        @Override
                        public void getVideo(File file) {
                            playVideo(file);
                        }
                    });
                    return null;
                }
            }.execute();
        }

        public void playVideo(File file) {
            played = true;
            galleryImage.setVisibility(View.INVISIBLE);
            galleryVideo.setVisibility(View.VISIBLE);
            galleryVideo.setVideoPath(file.getPath());
            galleryVideo.requestFocus();
            galleryVideo.start();
            galleryVideo.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    mediaPlayer.setLooping(true);
                }
            });
        }
    }
}
