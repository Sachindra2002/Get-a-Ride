package com.example.getaride.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.getaride.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DriverProfileFragment extends Fragment implements View.OnClickListener {
    TextView name, email, dob, address, phone, editProfile, vehicleType, vehilceNumber;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_driverprofile, container, false);
        name = v.findViewById(R.id.driverprofilename);
        email = v.findViewById(R.id.driverprofileemail);
        dob = v.findViewById(R.id.driverprofiledob);
        address = v.findViewById(R.id.driverprofileaddress);
        phone = v.findViewById(R.id.driverprofilephone);
        editProfile = v.findViewById(R.id.editprofile2);
        editProfile.setOnClickListener(this);
        vehicleType = v.findViewById(R.id.driverprofilevehicletype);
        vehilceNumber = v.findViewById(R.id.driverprofilevehicleNumber);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userid = user.getUid();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");
        ref.child(userid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String namee = dataSnapshot.child("fullName").getValue().toString();
                String Email = dataSnapshot.child("email").getValue().toString();
                String DOB = dataSnapshot.child("dob").getValue().toString();
                String Address = dataSnapshot.child("address").getValue().toString();
                String Phone = dataSnapshot.child("phone").getValue().toString();
                String vehicletype = dataSnapshot.child("vehicleType").getValue().toString();
                String vehiclenumber = dataSnapshot.child("vehicleNumber").getValue().toString();
                name.setText(namee);
                email.setText(Email);
                dob.setText(DOB);
                address.setText(Address);
                phone.setText(Phone);
                vehicleType.setText(vehicletype);
                vehilceNumber.setText(vehiclenumber);
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
            case R.id.editprofile2:
                break;
        }
    }
}
