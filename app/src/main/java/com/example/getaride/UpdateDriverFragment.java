package com.example.getaride;

import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class UpdateDriverFragment extends Fragment implements View.OnClickListener {
    private FirebaseAuth mAuth;
    EditText name, Email, Address, Phone, VehicleType, VehicleNumber;
    String fullName, email, address, dob, phone, vehicleType, vehicleNumber;
    TextView UID;
    Spinner spinner;
    DatabaseReference databaseReference;
    public UpdateDriverFragment(String fullName, String email, String address, String dob, String phone, String vehicleType, String vehicleNumber) {
        this.fullName = fullName;
        this.email = email;
        this.address = address;
        this.dob = dob;
        this.phone = phone;
        this.vehicleType = vehicleType;
        this.vehicleNumber = vehicleNumber;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_updatedriver, container,false);
        /*name = v.findViewById(R.id.newdrivername);
        Email = v.findViewById(R.id.newdriveremail);
        Address = v.findViewById(R.id.newdriveraddress);
        Phone = v.findViewById(R.id.newdriverphone);
        VehicleType = v.findViewById(R.id.newdrivervehicletype);
        VehicleNumber = v.findViewById(R.id.newdrivervehicleNumber);*/
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        name.setText(fullName);
        Email.setText(email);
        Address.setText(address);
        Phone.setText(phone);
        VehicleType.setText(vehicleType);
        VehicleNumber.setText(vehicleNumber);
        return v;
    }
    public void onBackPressed()
    {
        AppCompatActivity activity = (AppCompatActivity)getContext();
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container2, new ManageDriversFragment()).addToBackStack(null).commit();
    }


    @Override
    public void onClick(View v) {
        /*switch (v.getId())
        {
            case R.id.updatedriverbutton:
                updateDriver();
                break;
        }*/

    }

    private void updateDriver() {

        String newFullName = name.getText().toString().trim();
        String newEmail = Email.getText().toString().trim();
        String newAddress = Address.getText().toString().trim();
        String newPhone = Phone.getText().toString().trim();
        String newVehicleType = VehicleType.getText().toString().trim();
        String newVehicleNumber = VehicleNumber.getText().toString().trim();

        if(newFullName.isEmpty())
        {
            name.setError("Full Name Cannot be Empty!");
            name.requestFocus();
            return;
        }
        if(newEmail.isEmpty())
        {
            Email.setError("Email Cannot be empty");
            Email.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(newEmail).matches())
        {
            Email.setError("Please enter valid email!");
            Email.requestFocus();
            return;
        }
        if(newAddress.isEmpty())
        {
            Address.setError("Address Cannot be Empty!");
            Address.requestFocus();
            return;
        }
        if(newPhone.isEmpty())
        {
            Phone.setError("Phone Cannot be Empty!");
            Phone.requestFocus();
            return;
        }
        if(newPhone.length()<10 || newPhone.length()>10)
        {
            Phone.setError("Please enter valid phone number!");
            Phone.requestFocus();
            return;
        }
        if(newVehicleType.isEmpty())
        {
            VehicleType.setError("Please enter vehicle type!");
            VehicleType.requestFocus();
            return;
        }
        if(newVehicleNumber.isEmpty())
        {
            VehicleNumber.setError("Please enter vehicle number!");
            VehicleNumber.requestFocus();
            return;
        }
    }
}
