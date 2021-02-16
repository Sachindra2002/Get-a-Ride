package com.example.getaride.Fragments;

import android.os.Bundle;
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
import androidx.fragment.app.Fragment;

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

import java.util.HashMap;
import java.util.Map;

public class DriverScheduleFragment extends Fragment implements View.OnClickListener{
    String dated, Location, startime, endtime, userid;
    TextView date, location, startTime, endTime;
    Spinner driver_location, status;
    DatabaseReference databaseReference;
    String username;
    Button update;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_driverschedule, container, false);
        update = v.findViewById(R.id.updatedriverstatus);
        update.setOnClickListener(this);
        date = v.findViewById(R.id.driverscheduledate);
        location = v.findViewById(R.id.driverschedulelocation);
        startTime = v.findViewById(R.id.driverschedulestarttime);
        endTime = v.findViewById(R.id.driverscheduleendtime);
        driver_location = v.findViewById(R.id.locationdriverspinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.locations, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        driver_location.setAdapter(adapter);
        status = v.findViewById(R.id.statusdriverspinner);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(getActivity(),
                R.array.driver_status, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        status.setAdapter(adapter1);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        userid = user.getUid();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");
        ref.child(userid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //String name = dataSnapshot.child("fullName").getValue().toString();
                username = dataSnapshot.child("fullName").getValue().toString();
                FirebaseDatabase.getInstance().getReference().child("Schedules").orderByChild("fullName").equalTo(username).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot childSnapShots : snapshot.getChildren()) {
                            dated = childSnapShots.child("date").getValue().toString();
                            Location = childSnapShots.child("location").getValue().toString();
                            startime = childSnapShots.child("sTime").getValue().toString();
                            endtime = childSnapShots.child("eTime").getValue().toString();
                            date.setText(dated);
                            location.setText(Location);
                            startTime.setText(startime);
                            endTime.setText(endtime);
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.updatedriverstatus:
                updateDriver();
                break;
        }
    }

    private void updateDriver() {
        Map<String, Object> update = new HashMap<>();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");
        String newLocation = driver_location.getSelectedItem().toString();
        String newStatus = status.getSelectedItem().toString();
        update.put(userid+"/currentLocation", newLocation);
        update.put(userid+"/status", newStatus);
        databaseReference.updateChildren(update).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                {
                    Toast.makeText(getContext(), "Status and Location Updated Successfully!", Toast.LENGTH_SHORT).show();
                }else
                {
                    Toast.makeText(getContext(), "Status and could not be updated", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
}
