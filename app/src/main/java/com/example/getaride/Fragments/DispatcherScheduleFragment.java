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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DispatcherScheduleFragment extends Fragment {
    TextView date, location, morningStartTime, morningEndTime, eveningsStartTime, eveningEndTime;
    String dated, Location, morningstarttime, morningendtime, eveningstarttime, eveningendtime;
    DatabaseReference databaseReference;
    String username;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_dispatcherschedule, container, false);
        date = v.findViewById(R.id.dispatcherscheduledate);
        location = v.findViewById(R.id.dispatcherschedulelocation);
        morningStartTime = v.findViewById(R.id.dispatcherschedulemorningstarttime);
        morningEndTime = v.findViewById(R.id.dispatcherschedulemorningendtime);
        eveningsStartTime = v.findViewById(R.id.dispatcherscheduleveningstarttime);
        eveningEndTime = v.findViewById(R.id.dispatcherscheduleveningendtime);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userid = user.getUid();
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
                            morningstarttime = childSnapShots.child("sTime").getValue().toString();
                            morningendtime = childSnapShots.child("eTime").getValue().toString();
                            eveningstarttime = childSnapShots.child("esTime").getValue().toString();
                            eveningendtime = childSnapShots.child("eeTime").getValue().toString();
                            date.setText(dated);
                            location.setText(Location);
                            morningStartTime.setText(morningstarttime);
                            morningEndTime.setText(morningendtime);
                            eveningsStartTime.setText(eveningstarttime);
                            eveningEndTime.setText(eveningendtime);
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
}
