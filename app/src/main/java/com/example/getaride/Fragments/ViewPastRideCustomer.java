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

public class ViewPastRideCustomer extends Fragment {
    String pickUp, dropOff, drivername, totalPrice, logstart, logend, Date, Time;
    public ViewPastRideCustomer(String pickup, String dropoff, String drivername, String price, String logStart, String logEnd, String date, String time) {
        this.pickUp = pickup;
        this.dropOff = dropoff;
        this.drivername = drivername;
        this.totalPrice = price;
        this.logstart = logStart;
        this.logend = logEnd;
        this.Date = date;
        this.Time = time;
    }
    TextView name, pickup, destination, date, time, startlog, endlog, totalprice;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_completedridecustomer, container, false);
        name = v.findViewById(R.id.drivernamecompletedride);
        pickup = v.findViewById(R.id.customerpickupcompletedridecustomer);
        destination = v.findViewById(R.id.customerdestinationcompleteridecustomer);
        date = v.findViewById(R.id.customerdatecompletedridecustomer);
        time = v.findViewById(R.id.customertimecompleteridecustomer);
        startlog = v.findViewById(R.id.customerstartlogcompletedridecustomer);
        endlog = v.findViewById(R.id.customerendlogcompletedridecustomer);
        totalprice = v.findViewById(R.id.customertotalpricecompletedridecustomer);

        name.setText(drivername);
        pickup.setText(pickUp);
        destination.setText(dropOff);
        date.setText(Date);
        time.setText(Time);
        startlog.setText(logstart+" KM");
        endlog.setText(logend+" KM");
        totalprice.setText("LKR : "+totalPrice);
        return v;
    }
}
