package com.example.getaride;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class DispatcherHomeFragment extends Fragment implements View.OnClickListener{
    TextView usergreeting, greetingName;
    ImageView assignrides, drivers, ongoing, schedule;
    String fullname;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_dispatcherhome, container, false);
        usergreeting = v.findViewById(R.id.userGreetingdispatcher);
        Calendar c = Calendar.getInstance();
        int timeOfDay = c.get(Calendar.HOUR_OF_DAY);
        if (timeOfDay >= 0 && timeOfDay < 12) {
            usergreeting.setText("Morning");
        } else if (timeOfDay >= 12 && timeOfDay < 14) {
            usergreeting.setText("Afternoon");
        } else if (timeOfDay >= 14 && timeOfDay < 21) {
            usergreeting.setText("Evening");
        } else if (timeOfDay >= 21 && timeOfDay < 24) {
            usergreeting.setText("Evening");
        } else {
            usergreeting.setText("Null");
        }
        assignrides = v.findViewById(R.id.assigntaxi);
        assignrides.setOnClickListener(this);
        drivers = v.findViewById(R.id.drivers);
        drivers.setOnClickListener(this);
        ongoing = v.findViewById(R.id.ongoing);
        ongoing.setOnClickListener(this);
        schedule = v.findViewById(R.id.schedule);
        schedule.setOnClickListener(this);
        greetingName = v.findViewById(R.id.greetingnamedispatcher);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userid = user.getUid();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");
        ref.child(userid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String name = dataSnapshot.child("fullName").getValue().toString();
                greetingName.setText(name);
                fullname = dataSnapshot.child("fullName").getValue().toString();
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
            case R.id.assigntaxi:
                AppCompatActivity activity = (AppCompatActivity)v.getContext();
                activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container2, new ManageRidesFragment()).addToBackStack(null).commit();
                break;
            case R.id.drivers:
                AppCompatActivity activity2 = (AppCompatActivity)v.getContext();
                activity2.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
                activity2.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container2, new ManageDriversFragment()).addToBackStack(null).commit();
                break;

        }
    }
}
