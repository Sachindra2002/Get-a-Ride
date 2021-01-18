package com.example.getaride;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText fullname, email, phoneno, password, address, birthday;
    private ProgressBar     progressbar;
    private Button buttonregister;
    private FirebaseAuth mAuth;
    TextView login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        login = findViewById(R.id.login);
        login.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
        fullname = findViewById(R.id.name);
        email = findViewById(R.id.email);
        phoneno = findViewById(R.id.phone);
        password = findViewById(R.id.password);
        progressbar = findViewById(R.id.progressBar);
        buttonregister = findViewById(R.id.signupbutton);
        buttonregister.setOnClickListener(this);
        address = findViewById(R.id.address);

        final Calendar myCalendar = Calendar.getInstance();

        birthday= (EditText) findViewById(R.id.birthday);
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

            private void updateLabel() {
                String myFormat = "MM/dd/yyyy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                birthday.setText(sdf.format(myCalendar.getTime()));
            }

        };

        birthday.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(RegisterActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.signupbutton:
                registerUser();
                break;
            case R.id.login:
                startActivity(new Intent(this, LoginActivity.class));
                finish();
                break;
        }

    }
    public void registerUser() {
        String name = fullname.getText().toString().trim();
        String Email = email.getText().toString().trim();
        String number = phoneno.getText().toString().trim();
        String pass = password.getText().toString().trim();
        String Address = address.getText().toString().trim();
        String bday = birthday.getText().toString().trim();
        String role = "customer";
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
        if(Address.isEmpty())
        {
            address.setError("Address cannot be empty!");
            address.requestFocus();
            return;
        }
        if(bday.isEmpty())
        {
            birthday.setError("Birthday cannot be empty!");
            birthday.requestFocus();
            return;
        }

        //get-a-ride-afb65-default-rtdb
        progressbar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(Email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Users user = new Users(name, Email, number, bday, Address, role);
                    FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().
                            getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful())
                            {
                                Toast.makeText(RegisterActivity.this, "User registered Successfully", Toast.LENGTH_LONG).show();
                                progressbar.setVisibility(View.GONE);
                            }
                            else{
                                Toast.makeText(RegisterActivity.this, "User not registered sucessfully", Toast.LENGTH_LONG).show();
                                progressbar.setVisibility(View.GONE);
                            }
                        }
                    });
                }else
                {
                    Toast.makeText(RegisterActivity.this, "User not registered sucessfully", Toast.LENGTH_LONG).show();
                    progressbar.setVisibility(View.GONE);
                }
            }

        });

    }
}