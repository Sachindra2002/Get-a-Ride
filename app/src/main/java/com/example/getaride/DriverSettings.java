package com.example.getaride;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DriverSettings extends Fragment implements View.OnClickListener {
    EditText phoneNumber, vehicleType, vehicleNumber;
    String phonenumber, vehicletype, vehiclenumber, key;
    Button update;
    DatabaseReference databaseReference;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_driverprofilesettings, container, false);
        phoneNumber = v.findViewById(R.id.driversettingsnumber);
        vehicleType = v.findViewById(R.id.driversettingsvehicletype);
        vehicleNumber = v.findViewById(R.id.driversettingsvehiclenumber);
        update = v.findViewById(R.id.updateddriverprofile);
        update.setOnClickListener(this);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userid = user.getUid();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");
        ref.child(userid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                phonenumber = dataSnapshot.child("phone").getValue().toString();
                vehicletype = dataSnapshot.child("vehicleType").getValue().toString();
                vehiclenumber = dataSnapshot.child("vehicleNumber").getValue().toString();
                key = dataSnapshot.getKey();
                phoneNumber.setText(phonenumber);
                vehicleType.setText(vehicletype);
                vehicleNumber.setText(vehiclenumber);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.updateddriverprofile:
                updateProfile();
                break;
        }
    }

    private void updateProfile() {
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        String number = phoneNumber.getText().toString();
        String vehicletype = vehicleType.getText().toString();
        String vehiclenumber = vehicleNumber.getText().toString();

        if(number.isEmpty())
        {
            phoneNumber.setError("Phone number Cannot be Empty!");
            phoneNumber.requestFocus();
            return;
        }
        if(vehicletype.isEmpty())
        {
            vehicleType.setError("Vehicle Type Cannot be Empty!");
            vehicleType.requestFocus();
            return;
        }
        if(vehiclenumber.isEmpty())
        {
            vehicleNumber.setError("Vehicle number Cannot be Empty!");
            vehicleNumber.requestFocus();
            return;
        }

        if(isPhoneNumberChanged())
        {
            Toast.makeText(getContext(), "Data has been updated", Toast.LENGTH_SHORT).show();
        }else
        {
            Toast.makeText(getContext(), "Data cannot be updated", Toast.LENGTH_SHORT).show();
        }
        if(isVehicleTypeChanged())
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
        if(!phonenumber.equals(phoneNumber.getText().toString()))
        {
            databaseReference.child(key).child("phone").setValue(phoneNumber.getText().toString());
            return true;
        }else
        {
            return false;
        }
    }

    private boolean isVehicleTypeChanged() {
        if(!phonenumber.equals(phoneNumber.getText().toString()))
        {
            databaseReference.child(key).child("vehicleType").setValue(vehicleType.getText().toString());
            return true;
        }else
        {
            return false;
        }
    }

    private boolean isPhoneNumberChanged() {
        if(!phonenumber.equals(phoneNumber.getText().toString()))
        {
            databaseReference.child(key).child("vehicleNumber").setValue(vehicleNumber.getText().toString());
            return true;
        }else
        {
            return false;
        }
    }
}
