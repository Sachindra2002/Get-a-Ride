package com.example.getaride.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.getaride.R;
import com.example.getaride.Recyclerviews.ManageDriversFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class UpdateDriverFragment extends Fragment implements View.OnClickListener {
    private FirebaseAuth mAuth;
    TextView name, Email, Phone;
    EditText Address, VehicleType, VehicleNumber;
    String fullName, email, address, dob, phone, vehicleType, vehicleNumber, keyID;
    Button buttonupdate;
    DatabaseReference databaseReference;
    public UpdateDriverFragment(String fullName, String email, String address, String dob, String phone, String vehicleType, String vehicleNumber, String keyID) {
        this.fullName = fullName;
        this.email = email;
        this.address = address;
        this.dob = dob;
        this.phone = phone;
        this.vehicleType = vehicleType;
        this.vehicleNumber = vehicleNumber;
        this.keyID = keyID;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_updatedriver, container,false);

        name = v.findViewById(R.id.newdrivername);
        Email = v.findViewById(R.id.newdriveremail);
        Address = v.findViewById(R.id.newdriveraddress);
        Phone = v.findViewById(R.id.newdriverphone);
        VehicleType = v.findViewById(R.id.newdrivervehicletype);
        VehicleNumber = v.findViewById(R.id.newdrivervehiclenumber);
        buttonupdate = v.findViewById(R.id.update);
        buttonupdate.setOnClickListener(this);
        name.setText(fullName);
        Email.setText(email);
        Address.setText(address);
        Phone.setText(phone);
        VehicleType.setText(vehicleType);
        VehicleNumber.setText(vehicleNumber);
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        return v;
    }
    public void onBackPressed()
    {
        AppCompatActivity activity = (AppCompatActivity)getContext();
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container2, new ManageDriversFragment()).addToBackStack(null).commit();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.update:
                updateDriver();
                break;

        }

    }

    private void updateDriver() {
        //String newFullName = name.getText().toString().trim();
        //String newEmail = Email.getText().toString().trim();
        String newAddress = Address.getText().toString().trim();
        //String newPhone = Phone.getText().toString().trim();
        String newVehicleType = VehicleType.getText().toString().trim();
        String newVehicleNumber = VehicleNumber.getText().toString().trim();

        if(newAddress.isEmpty())
        {
            Address.setError("Address Cannot be Empty!");
            Address.requestFocus();
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
        if(isAddressChanged())
        {
            Toast.makeText(getContext(), "Data has been updated", Toast.LENGTH_SHORT).show();
        }else
        {
            Toast.makeText(getContext(), "Data cannot be updated", Toast.LENGTH_SHORT).show();
        }
        if(isNewVehicleTypeChanged())
        {
            Toast.makeText(getContext(), "Data has been updated", Toast.LENGTH_SHORT).show();
        }else
        {
            Toast.makeText(getContext(), "Data cannot be updated", Toast.LENGTH_SHORT).show();
        }
        if(isVehicleNumberChanged())
        {
            Toast.makeText(getContext(), "Data has been updated", Toast.LENGTH_SHORT).show();
        }else
        {
            Toast.makeText(getContext(), "Data cannot be updated", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isVehicleNumberChanged() {
        if(!address.equals(Address.getText().toString()))
        {
            databaseReference.child(keyID).child("vehicleNumber").setValue(VehicleNumber.getText().toString());
            return true;
        }else
        {
            return false;
        }
    }

    private boolean isNewVehicleTypeChanged() {
        if(!address.equals(Address.getText().toString()))
        {
            databaseReference.child(keyID).child("vehicleType").setValue(VehicleType.getText().toString());
            return true;
        }else
        {
            return false;
        }
    }

    private boolean isAddressChanged() {
        if(!address.equals(Address.getText().toString()))
        {
            databaseReference.child(keyID).child("address").setValue(Address.getText().toString());
            return true;
        }else
        {
            return false;
        }
    }


}
