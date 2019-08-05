package com.example.imgur.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.imgur.Models.Comment;
import com.example.imgur.R;

import java.util.ArrayList;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentHolder> {

    ArrayList<Comment> comments;
    Context context;

    public CommentAdapter(ArrayList<Comment> comments, Context context) {
        this.comments = comments;
        this.context = context;
    }

    @NonNull
    @Override
    public CommentHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_image_comment,viewGroup,false);
        return new CommentHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentHolder commentHolder, int i) {
        commentHolder.name.setText(comments.get(i).getAuthor());
        commentHolder.comment.setText(comments.get(i).getComment());
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    public static class CommentHolder extends RecyclerView.ViewHolder {

        TextView name, comment;

        public CommentHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.comment_owner);
            comment = itemView.findViewById(R.id.comment_description);
        }
    }
}
