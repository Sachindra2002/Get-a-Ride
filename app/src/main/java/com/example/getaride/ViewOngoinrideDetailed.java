package com.example.getaride;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ViewOngoinrideDetailed extends Fragment {
    String pickup, dropoff, customerEmail, customerName, date, vehicleNumber, vehicletype, Status, drivername, logStart, logEnd, time;
    TextView pickUp, dropOff, customeremail, customername, Date, vehiclenumber, vehicleType, status, driverName, logstart, logend, Time;
    public ViewOngoinrideDetailed(String pickup, String dropoff, String customerName, String status, String drivername ,String logStart, String logEnd, String date, String time) {
        this.pickup = pickup;
        this.dropoff = dropoff;
        this.customerEmail = customerEmail;
        this.customerName = customerName;
        this.date = date;
        this.vehicleNumber = vehicleNumber;
        this.vehicletype = vehicletype;
        this.Status = status;
        this.drivername = drivername;
        this.logStart = logStart;
        this.logEnd = logEnd;
        this.time = time;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.viewongoingridedetailed, container,false);
        pickUp = v.findViewById(R.id.customerpickupongoingdispatcher);
        dropOff = v.findViewById(R.id.customerdestinationongoingdispatcher);
        customername = v.findViewById(R.id.customernameongoingridedispatcher);
        driverName = v.findViewById(R.id.drivernameongoingdispatcher);
        Date = v.findViewById(R.id.customerdateongoingdispatcher);
        Time = v.findViewById(R.id.customertimeongoingdispatcher);
        logstart = v.findViewById(R.id.customerstartlogongoingdispatcher);
        logend = v.findViewById(R.id.customerendlogongoingdispatcher);
        status = v.findViewById(R.id.customerstatusongoingdispatcher);

        pickUp.setText(pickup);
        dropOff.setText(dropoff);
        customername.setText(customerName);
        driverName.setText(drivername);
        Date.setText(date);
        Time.setText(time);
        logstart.setText(logStart+" KM");
        logend.setText("Log End");
        status.setText(Status);

        return v;
    }
}
