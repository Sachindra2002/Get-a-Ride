package com.example.getaride.Fragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.getaride.Models.Rides;
import com.example.getaride.R;
import com.example.getaride.Recyclerviews.ManageRidesFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class AssignRideFragment extends Fragment implements View.OnClickListener{
    Spinner spinner;
    DatabaseReference databaseReference;
    DatabaseReference mDatabase;
    String dropoff, pickup, time, status, customerName, customerEmail, driverName, vehicleType, customernumber, date, driverNumber, keyID, vehiclenumber;
    TextView Customername, CustomerNumber, AssignPickup, AssignDestination, AssignDate, AssignTime, assignvehicletype;
    List<String> drivernames;
    Button assign;
    String spinnerName, key, numberDriver;
    String phoneDriver;
    String vehicleNumber;
    public AssignRideFragment(String dropoff, String pickup, String time, String status, String customerName, String customerEmail, String drivername, String vehicletype, String customernumber, String date, String driverNumber, String vehicleNumber,String keyId) {
        this.dropoff = dropoff;
        this.pickup = pickup;
        this.time = time;
        this.status = status;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.driverName = drivername;
        this.vehicleType = vehicletype;
        this.customernumber = customernumber;
        this.date = date;
        this.driverNumber = driverNumber;
        this.vehiclenumber = vehicleNumber;
        this.keyID = keyId;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_assignride, container, false);

        Customername = v.findViewById(R.id.assigncustomername);
        CustomerNumber = v.findViewById(R.id.assigncustomernumber);
        AssignPickup = v.findViewById(R.id.assignpickup);
        AssignDestination = v.findViewById(R.id.assigndestination);
        AssignDate = v.findViewById(R.id.assigndate);
        assignvehicletype = v.findViewById(R.id.assignvehicletype);
        AssignTime = v.findViewById(R.id.assigntime);
        spinner = v.findViewById(R.id.asigndriverspinner);
        assign = v.findViewById(R.id.buttonassign);
        assign.setOnClickListener(this);
        drivernames = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("Users").orderByChild("role").equalTo("driver").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot childSnapShots : snapshot.getChildren())
                {
                    spinnerName = childSnapShots.child("fullName").getValue().toString();
                    //key = childSnapShots.getKey();
                    //numberDriver = childSnapShots.child("phone").getValue().toString();
                    drivernames.add(spinnerName);
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, drivernames);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(arrayAdapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        Customername.setText(customerName);
        CustomerNumber.setText(customernumber);
        AssignPickup.setText(pickup);
        AssignDestination.setText(dropoff);
        AssignDate.setText(date);
        AssignPickup.setText(pickup);
        AssignTime.setText(time);
        assignvehicletype.setText(vehicleType);
        return v;
    }

    public void onBackPressed() {
        AppCompatActivity activity = (AppCompatActivity) getContext();
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container2, new ManageRidesFragment()).addToBackStack(null).commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.buttonassign:
                assignRide();
                break;
        }
    }
    private void assignRide() {
        DatabaseReference databaseReference2 = FirebaseDatabase.getInstance().getReference("PendingRides");
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Rides");
        String sEmail = "COMPANY_EMAIL";
        String sPass = "EMAIL_PASSWORD";
        String recipient = customerEmail;
        String status = "assigned";
        String logStart = "pending";
        String logEnd = "pending";
        String price = "pending";
        Map<String, Object> update = new HashMap<>();
        //mDatabase = FirebaseDatabase.getInstance().getReference().child("Rides");
        String driverName = spinner.getSelectedItem().toString();
        FirebaseDatabase.getInstance().getReference().child("Users").orderByChild("fullName").equalTo(driverName).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot childSnapShots : snapshot.getChildren())
                {
                    phoneDriver = childSnapShots.child("phone").getValue().toString();
                    vehicleNumber = childSnapShots.child("vehicleNumber").getValue().toString();
                    update.put(keyID+"/driverNumber", phoneDriver);
                    update.put(keyID+"/drivername", driverName);
                    update.put(keyID+"/vehicleNumber", vehicleNumber);
                    update.put(keyID+"/status", status);
                    databaseReference.updateChildren(update).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful())
                            {
                                Rides ride = new Rides(customerName, customerEmail, pickup, dropoff, driverName, vehicleType, time, status, date, customernumber, phoneDriver, vehicleNumber, logStart, logEnd, price);
                                databaseReference2.push().setValue(ride);
                                Toast.makeText(getContext(), "Ride assigned Successfully!", Toast.LENGTH_SHORT).show();
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
                                    message.setSubject("Booking Confirmed");
                                    message.setText("Your Booking has been confirmed and assigned to your driver\n"+"Driver Name : "+driverName +"\n"+ "Vehicle Type : "+vehicleType+"\n"+"Vehicle Number : "+vehicleNumber);
                                    new sendMail().execute(message);
                                } catch (MessagingException e) {
                                    e.printStackTrace();
                                }
                            }else
                            {
                                Toast.makeText(getContext(), "Could not assign ride", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
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
