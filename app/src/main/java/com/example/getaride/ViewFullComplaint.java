package com.example.getaride;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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

public class ViewFullComplaint extends Fragment implements View.OnClickListener {
    String complaint, status, complainee, date, inquiry, id;
    TextView Complaint, Status, Complainee, Date, Inquiry;
    Button Respond;
    int Position;
    DatabaseReference databaseReference;
    public ViewFullComplaint(String complaint, String status, String complainee, String date, String inquiry, String id) {

        this.complaint = complaint;
        this.status = status;
        this.complainee = complainee;
        this.date = date;
        this.inquiry = inquiry;
        this.id = id;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_viewsinglecomplaint, container, false);
        Complaint = v.findViewById(R.id.ComplaintView);
        Status = v.findViewById(R.id.statuscomplaint);
        Complainee = v.findViewById(R.id.complainer);
        Date = v.findViewById(R.id.datecomplaint);
        Inquiry = v.findViewById(R.id.fullcomplaint);

        Respond = v.findViewById(R.id.respond);
        Respond.setOnClickListener(this);

        Complaint.setText(inquiry);
        Status.setText(status);
        Complainee.setText(complainee);
        Date.setText(date);
        Inquiry.setText(complaint);

        return v;
    }

    public void onBackPressed() {
        AppCompatActivity activity = (AppCompatActivity) getContext();
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container2, new ManageComplaintsFragment()).addToBackStack(null).commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.respond:
                sendResponseEmail();
                break;
        }

    }

    private void sendResponseEmail() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Complaints");
        String recipient = complainee;
        String STatus = status;
        String response = "Responded";
        String sEmail = "getaridelk@gmail.com";
        String sPass = "getarideassignment";

       databaseReference.child(id).child("status").setValue(response).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                {
                    Toast.makeText(getContext(), "Complaint responded Successfully", Toast.LENGTH_LONG).show();
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
                        message.setSubject("Complaint Looked Through");
                        message.setText("Thank you for contacting us, your complaint has been looked through and will be checked");
                        new sendMail().execute(message);
                    } catch (MessagingException e) {
                        e.printStackTrace();
                    }
                }else
                {
                    Toast.makeText(getContext(), "Complaint could not be respond", Toast.LENGTH_LONG).show();
                }

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
