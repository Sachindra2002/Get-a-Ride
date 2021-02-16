package com.example.getaride;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.getaride.Models.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    TextView register;
    EditText email, password;
    Button loginButton;
    FirebaseAuth mAuth;
    ProgressBar progressBar;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        register = findViewById(R.id.register);
        register.setOnClickListener(this);
        mAuth = FirebaseAuth.getInstance();
        email = findViewById(R.id.editTextTextEmailAddress);
        password = findViewById((R.id.editTextTextPassword));
        loginButton = findViewById(R.id.loginbutton);
        progressBar = findViewById(R.id.progressBar);

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mFirebaseUser = mAuth.getCurrentUser();
                if(mFirebaseUser != null)
                {
                    check();
                }else
                {
                    Toast.makeText(LoginActivity.this, "Please Login", Toast.LENGTH_SHORT).show();
                }
            }

        };
    }
    private void check()
    {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userId = user.getUid();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        progressBar.setVisibility(View.VISIBLE);
        reference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Users userProfile = snapshot.getValue(Users.class);
                if(userProfile!=null)
                {
                    String role = userProfile.getRole();
                    if(role.equals("customer"))
                    {
                        startActivity(new Intent(LoginActivity.this, Customer.class));
                        progressBar.setVisibility(View.GONE);
                    }else if(role.equals("driver"))
                    {
                        startActivity(new Intent(LoginActivity.this, Driver.class));
                        progressBar.setVisibility(View.GONE);
                    }else if(role.equals("dispatcher"))
                    {
                        startActivity(new Intent(LoginActivity.this, Dispatcher.class));
                        progressBar.setVisibility(View.GONE);
                    }
                }else
                {
                    Toast.makeText(LoginActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.register:
                startActivity(new Intent(this, RegisterActivity.class));
                finish();
                break;
            case R.id.loginbutton:
                loginUser();
                break;
        }
    }
    private void loginUser() {
        String Email = email.getText().toString().trim();
        String Pass = password.getText().toString().trim();

        if(Email.isEmpty())
        {
            email.setError("Email cannot be empty!");
            email.requestFocus();
            return;
        }
        if(Pass.isEmpty())
        {
            password.setError("Password cannot be empty!");
            password.requestFocus();
            return;
        }
        mAuth.signInWithEmailAndPassword(Email, Pass).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(!task.isSuccessful())
                {
                    Toast.makeText(LoginActivity.this, "Email or Password Incorrect!", Toast.LENGTH_SHORT).show();
                }else
                {
                    check();
                }
            }

        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            Toast.makeText(this, "Please log in", Toast.LENGTH_SHORT).show();
        } else {
            check();
        }

    }
}