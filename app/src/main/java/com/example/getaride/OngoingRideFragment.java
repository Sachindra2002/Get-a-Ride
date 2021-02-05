package com.example.getaride;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class OngoingRideFragment extends Fragment implements View.OnClickListener{
    String pickup, status,dropoff, customerEmail, customerName, customernumber, vehicleNumber, vehicletype, price, drivername, logStart, logEnd, keyId, date, time, drivernumber;
    private static final int CALL_REQUEST = 1;
    DatabaseReference db;
    DatabaseReference db2;
    DatabaseReference db3;
    public OngoingRideFragment(String pickup, String dropoff, String customerEmail, String date, String time, String customerName, String customernumber, String vehicleNumber, String vehicletype, String price, String drivername, String status, String driverNumber, String logStart, String logEnd, String keyId) {
        this.pickup = pickup;
        this.dropoff = dropoff;
        this.customerEmail = customerEmail;
        this.date = date;
        this.time = time;
        this.customerName = customerName;
        this.customernumber = customernumber;
        this.vehicleNumber = vehicleNumber;
        this.vehicletype = vehicletype;
        this.price = price;
        this.drivername = drivername;
        this.status = status;
        this.drivernumber = driverNumber;
        this.logStart = logStart;
        this.logEnd = logEnd;
        this.keyId = keyId;

    }
    TextView customername, pickUp, destination;
    Button call, stop, ride_status;
    EditText mileage;
    Spinner update_ride_status;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_ongoingride, container,false);
        update_ride_status = v.findViewById(R.id.updateridestatusspinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.cab_status, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        update_ride_status.setAdapter(adapter);
        ride_status = v.findViewById(R.id.updateridestatus);
        ride_status.setOnClickListener(this);
        customername = v.findViewById(R.id.customernameongoingride2);
        customername.setText(customerName);
        pickUp = v.findViewById(R.id.customerpickupongoingride2);
        pickUp.setText(pickup);
        destination = v.findViewById(R.id.customerdestinationongoingride2);
        destination.setText(dropoff);
        call = v.findViewById(R.id.callcustomer2);
        call.setOnClickListener(this);
        stop = v.findViewById(R.id.endride);
        stop.setOnClickListener(this);
        mileage = v.findViewById(R.id.logend);
        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.callcustomer2:
                makeCall();
                break;

            case R.id.endride:
                EndRide();
                break;

            case R.id.updateridestatus:
                UpdateRideStatus();
                break;
        }
    }

    private void UpdateRideStatus() {
        db = FirebaseDatabase.getInstance().getReference().child("Ongoing");
        db2 = FirebaseDatabase.getInstance().getReference().child("Users");
        String newStatus = update_ride_status.getSelectedItem().toString();
        db.child(keyId).child("status").setValue(newStatus).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                {
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    String userid = user.getUid();
                    db2.child(userid).child("status").setValue(newStatus);
                    Toast.makeText(getContext(), "Status Updated Successfully!", Toast.LENGTH_SHORT).show();
                }else
                {
                    Toast.makeText(getContext(), "Status Could not be Updated", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void EndRide() {
        db = FirebaseDatabase.getInstance().getReference().child("Ongoing");
        db3 = FirebaseDatabase.getInstance().getReference("Completed");
        String sEmail = "getaridelk@gmail.com";
        String sPass = "getarideassignment";
        String recipient = customerEmail;
        String endMileage = mileage.getText().toString();
        if(endMileage.isEmpty())
        {
            mileage.setError("Please enter mileage to end ride");
            mileage.requestFocus();
            return;
        }

        if(Integer.valueOf(endMileage) <= Integer.valueOf(logStart))
        {
            mileage.setError("Mileage cannot be less or equal to start log");
            mileage.requestFocus();
            return;
        }

        int totalPrice = (Integer.valueOf(endMileage) - Integer.valueOf(logStart))*60;
        int totalDistance = Integer.valueOf(endMileage) - Integer.valueOf(logStart);
        //System.out.println(totalPrice);
        Rides ride = new Rides(customerName, customerEmail, pickup, dropoff, drivername, vehicletype, time, status, date, customernumber, drivernumber, vehicleNumber, logStart, endMileage, String.valueOf(totalPrice));
        db3.push().setValue(ride).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                {
                    db.child(keyId).removeValue();
                    Toast.makeText(getContext(), "Ride completed Successfully", Toast.LENGTH_SHORT).show();
                    Properties properties = new Properties();
                    properties.put("mail.smtp.auth", "true");
                    properties.put("mail.smtp.starttls.enable", "true");
                    properties.put("mail.smtp.host", "smtp.gmail.com");
                    properties.put("mail.smtp.port", "587");

                    Session session = Session.getInstance(properties, new Authenticator() {
                        @Override
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(sEmail, sPass);
                        }
                    });

                    Message message = new MimeMessage(session);
                    try {
                        message.setFrom(new InternetAddress(sEmail));
                        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
                        message.setSubject("Ride Completed");
                        message.setText("Your Ride is completed\n\n"+"Driver Name : "+drivername +"\n\n"+ "Vehicle Type : "+vehicletype+"\n\n"+"Vehicle Number : "+vehicleNumber+"\n\n"+"Total Distance travelled : "+totalDistance+"KM"+"\n\n"+"Total Price LKR : "+totalPrice+"\n\n"+"Thank you for choosing get a ride and have a nice day :)");
                        new sendMail().execute(message);
                    } catch (MessagingException e) {
                        e.printStackTrace();
                    }
                    getFragmentManager().beginTransaction().replace(R.id.fragment_container3, new DriverHomeFragment()).commit();
                }else
                {
                    Toast.makeText(getContext(), "Ride could not be completed", Toast.LENGTH_SHORT).show();
                }
            }
        });

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

    private class sendMail extends AsyncTask<Message, String, String> {
        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(getContext(), "Please wait..", "Sending Mail", true, false);
        }

        @Override
        protected String doInBackground(Message... messages) {
            try {
                Transport.send(messages[0]);
                return "Success";
            } catch (MessagingException e) {

                e.printStackTrace();
                return "Error";
            }

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();
            if (s.equals("Success")) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setCancelable(false);
                builder.setTitle(Html.fromHtml("<font color='#509324'>Success</font>"));
                builder.setMessage("Mail send sucessfully.");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                    }
                });
                builder.show();
            }else
            {
                Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
