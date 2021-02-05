package com.example.getaride;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ViewPastRideDriver extends Fragment {
    String pickUp, dropOff, customername, totalPrice, logstart, logend, Date, Time;

    public ViewPastRideDriver(String pickup, String dropoff, String customerName, String price, String logStart, String logEnd, String date, String time) {
        this.pickUp = pickup;
        this.dropOff = dropoff;
        this.customername = customerName;
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
        View v = inflater.inflate(R.layout.fragment_viewfullcompltedridedriver, container, false);
        name = v.findViewById(R.id.customernamecompletedride);
        pickup = v.findViewById(R.id.customerpickupcompletedride);
        destination = v.findViewById(R.id.customerdestinationcompleteride);
        date = v.findViewById(R.id.customerdatecompletedride);
        time = v.findViewById(R.id.customertimecompleteride);
        startlog = v.findViewById(R.id.customerstartlogcompletedride);
        endlog = v.findViewById(R.id.customerendlogcompletedride);
        totalprice = v.findViewById(R.id.customertotalpricecompletedride);

        name.setText(customername);
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
