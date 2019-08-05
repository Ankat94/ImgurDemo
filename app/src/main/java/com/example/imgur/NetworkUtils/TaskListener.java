package com.example.imgur.NetworkUtils;

import com.google.firebase.auth.FirebaseUser;

public interface TaskListener {
    void onComplete(FirebaseUser user);
}
