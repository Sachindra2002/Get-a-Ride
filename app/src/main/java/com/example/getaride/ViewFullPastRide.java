package com.example.getaride;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ViewFullPastRide extends Fragment {
    String pickup, dropoff, customerEmail, customerName, date, vehicleNumber, vehicletype, price, drivername, logStart, logEnd, time;
    TextView pickUp, dropOff, customeremail, customername, Date, vehiclenumber, vehicleType, totalPrice, driverName, logstart, logend, Time;
    public ViewFullPastRide(String pickup, String dropoff, String customerEmail, String customerName, String date, String vehicleNumber, String vehicletype, String price, String drivername, String logStart, String logEnd, String time) {
        this.pickup = pickup;
        this.dropoff = dropoff;
        this.customerEmail = customerEmail;
        this.customerName = customerName;
        this.date = date;
        this.vehicleNumber = vehicleNumber;
        this.vehicletype = vehicletype;
        this.price = price;
        this.drivername = drivername;
        this.logStart = logStart;
        this.logEnd = logEnd;
        this.time = time;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_completedridedispatcher, container,false);
        pickUp = v.findViewById(R.id.customerpickupcompleteddispatcher);
        dropOff = v.findViewById(R.id.customerdestinationcompleteddispatcher);
        customername = v.findViewById(R.id.customernamecompletedridedispatcher);
        driverName = v.findViewById(R.id.drivernamecompleteddispatcher);
        Date = v.findViewById(R.id.customerdatecompleteddispatcher);
        Time = v.findViewById(R.id.customertimecompleteddispatcher);
        logstart = v.findViewById(R.id.customerstartlogcompleteddispatcher);
        logend = v.findViewById(R.id.customerendlogcompleteddispatcher);
        totalPrice = v.findViewById(R.id.customertotalpricecompleteddispatcher);

        pickUp.setText(pickup);
        dropOff.setText(dropoff);
        customername.setText(customerName);
        driverName.setText(drivername);
        Date.setText(date);
        Time.setText(time);
        logstart.setText(logStart+" KM");
        logend.setText(logEnd+" KM");
        totalPrice.setText("LKR : "+price);
        return v;
    }
}
