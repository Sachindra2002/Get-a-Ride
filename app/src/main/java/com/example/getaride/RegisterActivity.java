package com.example.getaride;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText fullname, email, phoneno, password, password2;
    private ProgressBar progressbar;
    private Button buttonregister;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();
        fullname = findViewById(R.id.name);
        email = findViewById(R.id.email);
        phoneno = findViewById(R.id.phone);
        password = findViewById(R.id.password);
        password2 = findViewById(R.id.password2);
        progressbar = findViewById(R.id.progressBar);
        buttonregister = findViewById(R.id.signupbutton);
        buttonregister.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.signupbutton:
                registerUser();
                break;
        }

    }
    public void registerUser() {
        String name = fullname.getText().toString().trim();
        String Email = email.getText().toString().trim();
        String number = phoneno.getText().toString().trim();
        String pass = password.getText().toString().trim();
        String pass2 = password2.getText().toString().trim();

        if(name.isEmpty())
        {
            fullname.setError("Full name cannot be empty!");
            fullname.requestFocus();
            return;
        }
        if(Email.isEmpty())
        {
            email.setError("Email cannot be empty!");
            email.requestFocus();
            return;
        }
        if(number.isEmpty())
        {
            phoneno.setError("Phone number cannot be empty!");
            phoneno.requestFocus();
            return;
        }
        if(pass.isEmpty())
        {
            password.setError("Password cannot be empty!");
            password.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(Email).matches())
        {
            email.setError("Please enter a valid email!");
            email.requestFocus();
            return;
        }
        if(pass.length()<6)
        {
            password.setError("Password cannot less then six characters!");
            password.requestFocus();
            return;
        }
        if(pass2.isEmpty())
        {
            password2.setError("Please re-enter password");
            password2.requestFocus();
            return;
        }
        if(pass != pass2)
        {
            password2.setError("Passwords do not match!");
            password2.requestFocus();
            return;
        }

    }
}