package com.example.getaride;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddDriverFragment extends Fragment implements View.OnClickListener{
    Spinner spinner;
    private EditText fullname;
    private EditText email;
    private EditText phoneno;
    private EditText password;
    private EditText address;
    private EditText birthday;
    private String vehicleType;
    private EditText vehicleNumber;
    private ProgressBar progressbar;
    private Button buttonregister;
    private FirebaseAuth mAuth;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_adddriver, container, false);
        spinner = (Spinner) v.findViewById(R.id.vehicletype);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.vehicle_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        mAuth = FirebaseAuth.getInstance();
        fullname = v.findViewById(R.id.namedriver);
        email = v.findViewById(R.id.emaildriver);
        phoneno = v.findViewById(R.id.phonedriver);
        password = v.findViewById(R.id.passworddriver);
        progressbar = v.findViewById(R.id.progressBardriver);
        buttonregister = v.findViewById(R.id.signupbuttondriver);
        buttonregister.setOnClickListener(this);
        address = v.findViewById(R.id.addressdriver);
        vehicleType = spinner.getSelectedItem().toString();
        vehicleNumber = v.findViewById(R.id.vehicleNumber);

        final Calendar myCalendar = Calendar.getInstance();

        birthday= (EditText) v.findViewById(R.id.birthdaydriver);
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
                new DatePickerDialog(getContext(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.signupbuttondriver:
                registerDriver();
                break;
        }
    }

    private void registerDriver() {
        String name = fullname.getText().toString().trim();
        String Email = email.getText().toString().trim();
        String number = phoneno.getText().toString().trim();
        String pass = password.getText().toString().trim();
        String Address = address.getText().toString().trim();
        String bday = birthday.getText().toString().trim();
        String VehicalType = vehicleType;
        String VehicleNumber = vehicleNumber.getText().toString().trim();
        String role = "driver";
        String status = "Available";
        String currentLocation = "Negombo";

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
        if(!Patterns.EMAIL_ADDRESS.matcher(Email).matches())
        {
            email.setError("Please enter a valid email!");
            email.requestFocus();
            return;
        }
        if(number.isEmpty())
        {
            phoneno.setError("Phone number cannot be empty!");
            phoneno.requestFocus();
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
        if(VehicleNumber.isEmpty())
        {
            vehicleNumber.setError("Vehicle Number Cannot be empty");
            vehicleNumber.requestFocus();
            return;
        }
        if(pass.isEmpty())
        {
            password.setError("Password cannot be empty!");
            password.requestFocus();
            return;
        }

        if(pass.length()<6)
        {
            password.setError("Password cannot less then six characters!");
            password.requestFocus();
            return;
        }



        progressbar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(Email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Users user = new Users(name, Email, number, bday, Address, VehicleNumber, VehicalType, role, status, currentLocation);
                    FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().
                            getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful())
                            {
                                Toast.makeText(getContext(), "Driver registered Successfully", Toast.LENGTH_LONG).show();
                                progressbar.setVisibility(View.GONE);
                            }
                            else{
                                Toast.makeText(getContext(), "Driver not registered sucessfully", Toast.LENGTH_LONG).show();
                                progressbar.setVisibility(View.GONE);
                            }
                        }
                    });
                }else
                {
                    Toast.makeText(getContext(), "Driver not registered sucessfully", Toast.LENGTH_LONG).show();
                    progressbar.setVisibility(View.GONE);
                }
            }

        });
    }
}
