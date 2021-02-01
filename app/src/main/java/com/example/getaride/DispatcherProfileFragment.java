package com.example.getaride;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DispatcherProfileFragment extends Fragment implements View.OnClickListener{
    TextView name, email, dob, address, phone, editProfile;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_dispatcherprofile, container, false);
        name = v.findViewById(R.id.dispatcherprofilename);
        email = v.findViewById(R.id.dispatcherprofileemail);
        dob = v.findViewById(R.id.dispatcherprofiledob);
        address = v.findViewById(R.id.dispatcherprofileaddress);
        phone = v.findViewById(R.id.dispatcherprofilephone);
        editProfile = v.findViewById(R.id.editprofile);
        editProfile.setOnClickListener(this);
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
                name.setText(namee);
                email.setText(Email);
                dob.setText(DOB);
                address.setText(Address);
                phone.setText(Phone);
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
            case R.id.editprofile:
                AppCompatActivity activity = (AppCompatActivity)v.getContext();
                activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container2, new DispatcherSettings()).addToBackStack(null).commit();
                break;
        }
    }
}
