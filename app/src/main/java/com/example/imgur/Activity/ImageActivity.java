package com.example.imgur.Activity;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.example.imgur.Adapter.CommentAdapter;
import com.example.imgur.Models.Comment;
import com.example.imgur.Models.Image;
import com.example.imgur.NetworkUtils.ApiManager;
import com.example.imgur.NetworkUtils.CommentListener;
import com.example.imgur.NetworkUtils.CommentSuccessListener;
import com.example.imgur.NetworkUtils.VideoListener;
import com.example.imgur.R;
import com.google.gson.Gson;

import java.io.File;
import java.util.ArrayList;

public class ImageActivity extends AppCompatActivity {

    Image image;
    TextView imageTitle, imageId, imageDescrip;
    ImageView galleryImage;
    RecyclerView commentRecycle;
    EditText commentText;
    ImageButton sendButton;
    VideoView galleryVideo;

    ApiManager apiManager;
    ArrayList<Comment> comments = new ArrayList<>();
    CommentAdapter commentAdapter;
    MediaController mediaController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_view);

        imageTitle = findViewById(R.id.image_title);
        imageId = findViewById(R.id.image_id);
        imageDescrip = findViewById(R.id.image_description);
        galleryImage = findViewById(R.id.imageview);
        commentRecycle = findViewById(R.id.image_comment_recycle);
        commentText = findViewById(R.id.gallery_comment_text);
        sendButton = findViewById(R.id.imageButton);
        galleryVideo = findViewById(R.id.videoView);

        Gson gson = new Gson();
        String imageObject = getIntent().getStringExtra("imageObject");
        image = gson.fromJson(imageObject, Image.class);
        apiManager = new ApiManager(ImageActivity.this);

        imageTitle.setText(image.getTitle());
        imageId.setText(image.getId());
        imageDescrip.setText(image.getDescription());

        Glide.with(this).asGif().load(image.getLink()).placeholder(R.drawable.placeholder).into(galleryImage);
        if (image.getType().equals("image/gif")) {
            File path = Environment.getExternalStorageDirectory();
            File file = new File(path, image.getId() + ".mp4");
            if (file.exists()) {
                playVideo(file);
                return;
            }
            else {
                downloadVideo(file);
            }
            mediaController = new MediaController(this);
            galleryVideo.setMediaController(mediaController);
        }

        downloadComment();

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                commentSendClick();
            }
        });

        commentAdapter = new CommentAdapter(comments,ImageActivity.this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(ImageActivity.this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        commentRecycle.setLayoutManager(layoutManager);
        commentRecycle.setAdapter(commentAdapter);
    }

    public void commentSendClick() {
        String comment = commentText.getText().toString();

        if (comment.isEmpty()) {
            Toast.makeText(ImageActivity.this,"Please Write Some comment", Toast.LENGTH_SHORT).show();
            return;
        }
        uploadComment(comment);
    }

    public void uploadComment(final String comment) {

        apiManager.postComment("Cyq6dtR", comment, new CommentSuccessListener() {
            @Override
            public void onSucces(String id) {
                Comment comment1 = new Comment(id,comment,"Default");
                comments.add(comments.size() - 1,comment1);
                commentAdapter.notifyDataSetChanged();
                commentText.setText("");
            }
        });
    }

    @SuppressLint("StaticFieldLeak")
    public void downloadComment() {
        new AsyncTask<Void, Void, Boolean>() {

            @Override
            protected Boolean doInBackground(Void... voids) {
                apiManager.getComments(image.getId(), new CommentListener() {
                    @Override
                    public void commentReceived(ArrayList<Comment> value) {
                        comments.addAll(value);
                        commentAdapter.notifyDataSetChanged();
                    }
                });
                return null;
            }
        }.execute();
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
