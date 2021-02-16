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
import androidx.fragment.app.Fragment;

import com.example.getaride.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DispatcherSettings extends Fragment implements View.OnClickListener{
    EditText address, phone;
    TextView name, email, dob;
    Button update;
    String key;
    DatabaseReference databaseReference;
    String Address, Phone;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_dispatcherprofilesettings, container, false);
        name = v.findViewById(R.id.dispatchersettingsname);
        email = v.findViewById(R.id.dispatchersettingsemail);
        dob = v.findViewById(R.id.dispatchersettingsdob);
        address = v.findViewById(R.id.dispatchersettingsaddress);
        phone = v.findViewById(R.id.dispatchersettingsphone);
        update = v.findViewById(R.id.updatedispatcherprofile);
        update.setOnClickListener(this);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userid = user.getUid();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");
        ref.child(userid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String namee = dataSnapshot.child("fullName").getValue().toString();
                String Email = dataSnapshot.child("email").getValue().toString();
                String DOB = dataSnapshot.child("dob").getValue().toString();
                Address = dataSnapshot.child("address").getValue().toString();
                Phone = dataSnapshot.child("phone").getValue().toString();
                key = dataSnapshot.getKey();
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
            case R.id.updatedispatcherprofile:
                updateProfile();
                break;
        }

    }

    private void updateProfile() {
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        String Address = address.getText().toString();
        String Phone = phone.getText().toString();

        if(Address.isEmpty())
        {
            address.setError("Address Cannot be Empty!");
            address.requestFocus();
            return;
        }

        if(Phone.isEmpty())
        {
            phone.setError("Phone number cannot be empty");
            phone.requestFocus();
            return;
        }
        if(isAddressChanged())
        {
            Toast.makeText(getContext(), "Data has been updated", Toast.LENGTH_SHORT).show();
        }else
        {
            Toast.makeText(getContext(), "Data cannot be updated", Toast.LENGTH_SHORT).show();
        }
        if(isPhoneNumberChanged())
        {
            Toast.makeText(getContext(), "Data has been updated", Toast.LENGTH_SHORT).show();
        }else
        {
            Toast.makeText(getContext(), "Data cannot be updated", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isPhoneNumberChanged() {
        if(!Phone.equals(phone.getText().toString()))
        {
            databaseReference.child(key).child("phone").setValue(phone.getText().toString());
            return true;
        }else
        {
            return false;
        }
    }

    private boolean isAddressChanged() {
        if(!Phone.equals(phone.getText().toString()))
        {
            databaseReference.child(key).child("address").setValue(address.getText().toString());
            return true;
        }else
        {
            return false;
        }
    }
}
