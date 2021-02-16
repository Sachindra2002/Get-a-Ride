package com.example.getaride.Fragments;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
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
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.getaride.Recyclerviews.AllOngoingRidesDriverFragment;
import com.example.getaride.Models.Rides;
import com.example.getaride.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class StartRideFragment extends Fragment implements View.OnClickListener{
    private static final int CALL_REQUEST = 1;
    String pickup, dropoff, customerEmail, customerName, customernumber, vehicleNumber, vehicletype, price, drivername, logStart, logEnd, keyId, date, time, drivernumber;
    EditText logstart1;
    DatabaseReference db;
    DatabaseReference db1;
    String fullname;
    public StartRideFragment(String pickup, String dropoff, String customerEmail, String date, String time, String customerName, String customernumber,String driverNumber, String vehicleNumber, String vehicletype, String price, String drivername, String logStart, String logEnd, String keyId) {
        this.pickup = pickup;
        this.dropoff = dropoff;
        this.customerEmail = customerEmail;
        this.date = date;
        this.time = time;
        this.customerName = customerName;
        this.customernumber = customernumber;
        this.drivernumber = driverNumber;
        this.vehicleNumber = vehicleNumber;
        this.vehicletype = vehicletype;
        this.price = price;
        this.drivername = drivername;
        this.logStart = logStart;
        this.logEnd = logEnd;
        this.keyId = keyId;
    }
    Button callCustomer, startRide;
    TextView customername, customerNumber, customerPickup, customerDropOff, ridevehicleType, rideDate, rideTime;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_startride, container,false);
        callCustomer = v.findViewById(R.id.callcustomer);
        callCustomer.setOnClickListener(this);
        startRide = v.findViewById(R.id.startride);
        startRide.setOnClickListener(this);
        customername = v.findViewById(R.id.customernamestartride);
        customerNumber = v.findViewById(R.id.customernumberstartride);
        customerPickup = v.findViewById(R.id.customerpickupstartride);
        customerDropOff = v.findViewById(R.id.customerdestinationstartride);
        ridevehicleType = v.findViewById(R.id.customervehicletypestartride);
        rideDate = v.findViewById(R.id.customerdatestartride);
        rideTime = v.findViewById(R.id.customertimestartride);
        logstart1 = v.findViewById(R.id.logstart);
        customername.setText(customerName);
        customerNumber.setText(customernumber);
        customerPickup.setText(pickup);
        customerDropOff.setText(dropoff);
        ridevehicleType.setText(vehicletype);
        rideDate.setText(date);
        rideTime.setText(time);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userid = user.getUid();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");
        ref.child(userid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String name = dataSnapshot.child("fullName").getValue().toString();
                fullname = dataSnapshot.child("fullName").getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return v;
    }

    private void makeCall()
    {
        String number = customernumber;
        if(number.trim().length()>0)
        {
            if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED)
            {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE}, CALL_REQUEST);
            }
            else
            {
                String dial = "tel:"+number;
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
            }
        }
        else
        {
            Toast.makeText(getContext(), "Enter your phone number", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == CALL_REQUEST)
        {
            if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
            {
                makeCall();
            }
            else
            {
                Toast.makeText(getContext(), "Permission not granted make call", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.callcustomer:
                makeCall();
                break;

            case R.id.startride:
                startTheRide();
                break;
        }
    }

    private void startTheRide() {
        String logStart = logstart1.getText().toString();
        db = FirebaseDatabase.getInstance().getReference("Ongoing");
        db1 = FirebaseDatabase.getInstance().getReference("PendingRides");
        if(logStart.isEmpty())
        {
            logstart1.setError("Please enter mileage");
            logstart1.requestFocus();
            return;
        }
        String status = "Started";
        Rides ride = new Rides(customerName, customerEmail, pickup, dropoff, drivername, vehicletype, time, status, date, customernumber,drivernumber , vehicleNumber, logStart, logEnd, price);
        db.push().setValue(ride).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                {
                    Toast.makeText(getContext(), "Ride Started Successfully!", Toast.LENGTH_SHORT).show();
                    db1.child(keyId).removeValue();
                    Bundle bundle2 = new Bundle();
                    bundle2.putString("key", fullname);
                    AllOngoingRidesDriverFragment fragment2 = new AllOngoingRidesDriverFragment();
                    fragment2.setArguments(bundle2);
                    getFragmentManager().beginTransaction().replace(R.id.fragment_container3, fragment2).addToBackStack(null).commit();
                    //getFragmentManager().beginTransaction().replace(R.id.fragment_container3, new AllOngoingRidesDriverFragment()).addToBackStack(null).commit();
                }else
                {
                    Toast.makeText(getContext(), "Ride Could not be Started", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}
