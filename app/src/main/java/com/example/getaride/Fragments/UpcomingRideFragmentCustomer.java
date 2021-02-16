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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.getaride.R;
import com.google.firebase.database.DatabaseReference;

public class UpcomingRideFragmentCustomer extends Fragment implements View.OnClickListener{
    private static final int CALL_REQUEST = 1;
    String pickup, dropoff, customerEmail, customerName, customernumber, vehicleNumber, vehicletype, price, drivername, logStart, logEnd, keyId, date, time, drivernumber;
    DatabaseReference db;
    DatabaseReference db1;
    Button calldriver;
    TextView driverName, driverNumber, customerPickup, customerDropOff, ridevehicleNumber, rideDate, rideTime;
    public UpcomingRideFragmentCustomer(String pickup, String dropoff, String customerEmail, String date, String time, String customerName, String customernumber, String vehicleNumber, String vehicletype, String price, String drivername, String status, String driverNumber, String logStart, String logEnd, String keyId) {
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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_viewupcomingridecustomer, container,false);
        calldriver = v.findViewById(R.id.calldriver);
        calldriver.setOnClickListener(this);
        driverName = v.findViewById(R.id.drivernameupcomingcustomer);
        driverNumber = v.findViewById(R.id.drivernumberupcomingcustomer);
        customerPickup = v.findViewById(R.id.customerpickupupcomincustomer);
        customerDropOff = v.findViewById(R.id.customerdestinationupcomingcustomer);
        ridevehicleNumber = v.findViewById(R.id.customervehicletypeupcomingcustomer);
        rideDate = v.findViewById(R.id.customerdateupcomingcustomer);
        rideTime = v.findViewById(R.id.customertimeupcomingcustomer);
        driverName.setText(drivername);
        driverNumber.setText(drivernumber);
        customerPickup.setText(pickup);
        customerDropOff.setText(dropoff);
        ridevehicleNumber.setText(vehicleNumber);
        rideDate.setText(date);
        rideTime.setText(time);
        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.calldriver:
                makeCall();
                break;

        }

    }
    private void makeCall()
    {
        String number = drivernumber;
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
}
