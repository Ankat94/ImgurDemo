package com.example.imgur.NetworkUtils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FirebaseManager {

    private FirebaseAuth firebaseAuth;
    private Context context;

    public FirebaseManager(Context context) {
        this.firebaseAuth = FirebaseAuth.getInstance();
        this.context = context;
    }

    public void createUser(String email, String pass, final TaskListener taskListener) {
        firebaseAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    taskListener.onComplete(user);
                }
                else {
                    Toast.makeText(context,task.getException().getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void signInUser(String email, String pass, final TaskListener taskListener) {
        firebaseAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    taskListener.onComplete(user);
                }
                else {
                    Toast.makeText(context,task.getException().getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}

