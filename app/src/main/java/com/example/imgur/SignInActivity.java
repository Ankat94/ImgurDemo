package com.example.imgur;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.imgur.Activity.MainActivity;
import com.example.imgur.NetworkUtils.FirebaseManager;
import com.example.imgur.NetworkUtils.TaskListener;
import com.google.firebase.auth.FirebaseUser;

public class SignInActivity extends AppCompatActivity {

    FirebaseManager firebaseManager;
    EditText emailText, passText;
    TextView signUpText;
    Button signInButton;
    boolean signUp = false;
    View backgroundView;

    String[] PERMISSIONS = {Manifest.permission.WRITE_EXTERNAL_STORAGE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        firebaseManager = new FirebaseManager(this);
        emailText = findViewById(R.id.sign_in_user);
        passText = findViewById(R.id.sign_in_pass);
        signUpText = findViewById(R.id.sign_in_sign_up);
        signInButton = findViewById(R.id.sign_in_button);

        backgroundView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.loading_view,null);


        signUpText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUpTextChange();
            }
        });

        checkPermission();
    }

    public void signInClicked(View view) {
        String email = emailText.getText().toString();
        String  pass = passText.getText().toString();

        if (email.isEmpty()) {
            Toast.makeText(this,"Enter Email ID", Toast.LENGTH_SHORT).show();
            return;
        }
        else if (!email.contains("@")) {
            Toast.makeText(this,"Enter Correct Email Id", Toast.LENGTH_SHORT).show();
            return;
        }

        if (pass.isEmpty()) {
            Toast.makeText(this,"Enter Password", Toast.LENGTH_SHORT).show();
            return;
        }

        String text = signInButton.getText().toString();

        if (text.equalsIgnoreCase("Sign In")) {
            firebaseManager.signInUser(email, pass, new TaskListener() {
                @Override
                public void onComplete(FirebaseUser user) {
                    Intent mainIntent = new Intent(SignInActivity.this, MainActivity.class);
                    startActivity(mainIntent);
                }
            });
        }
        else {
            firebaseManager.createUser(email, pass, new TaskListener() {
                @Override
                public void onComplete(FirebaseUser user) {
                    signUp = true;
                    signUpTextChange();
                    Toast.makeText(SignInActivity.this,"Account Created Succesfully. Try Logging!!", Toast.LENGTH_LONG).show();
                }
            });
        }


    }

    public void signUpTextChange() {
        emailText.setText("");
        passText.setText("");
        if (signUp) {
            signInButton.setText("Sign In");
            signUpText.setText("SignUp");
            signUp = false;
        }
        else {
            signInButton.setText("Sign Up");
            signUpText.setText("SignIn");
            signUp = true;
        }
    }

    public void checkPermission() {
        if (ContextCompat.checkSelfPermission(SignInActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(SignInActivity.this, PERMISSIONS, 10);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case 10 : {
                if (!(grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    Toast.makeText(SignInActivity.this,"Permission Require to Save Data", Toast.LENGTH_SHORT).show();
                    checkPermission();
                }
            }
        }

    }
}
