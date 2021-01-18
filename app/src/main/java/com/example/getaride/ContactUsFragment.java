package com.example.getaride;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class ContactUsFragment extends Fragment implements View.OnClickListener{
    private Spinner spinner;
    Button inquirysubmit;
    EditText inquirytext;
    private DatabaseReference mDatabase;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_contactus, container,false);
        spinner = (Spinner) v.findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.inquiry_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        inquirytext = v.findViewById(R.id.inquiry);
        String complaint = spinner.getSelectedItem().toString();
        inquirysubmit = v.findViewById(R.id.submitinquiry);
        inquirysubmit.setOnClickListener(this);
        mDatabase = FirebaseDatabase.getInstance().getReference();


        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.submitinquiry:
                submitInquiry();
                break;
        }

    }

    private void submitInquiry() {

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();

        String Date = date.toString();
        String Complaint = spinner.getSelectedItem().toString();
        String Inquiry = inquirytext.getText().toString().trim();
        String status = "pending";
        String uid = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        Random rand = new Random(); //instance of random class
        int upperbound = 10000;
        //generate random values from 0-24
        int complaintID = rand.nextInt(upperbound);
        String id = String.valueOf(complaintID);

        if(Inquiry.isEmpty())
        {
            inquirytext.setError("Please enter the details");
            inquirytext.requestFocus();
            return;
        }
        Complaints complaint = new Complaints(Complaint, Inquiry, id, uid, status, Date);
        mDatabase.child("Complaints").child(id).setValue(complaint).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                {
                    Toast.makeText(getContext(), "Inquiry submitted sucessfully!", Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(getContext(), "Inquiry not submitted", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
