package com.example.getaride;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class DriverHomeFragment extends Fragment implements View.OnClickListener{
    TextView usergreeting, greetingName;
    String fullname;
    ImageView ongoing, upcoming, past, schedule;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_driverhome, container, false);
        ongoing = v.findViewById(R.id.ongoingrideshomedriver);
        ongoing.setOnClickListener(this);
        upcoming = v.findViewById(R.id.upcomingrideshomedriver);
        upcoming.setOnClickListener(this);
        past = v.findViewById(R.id.completedrideshomedriver);
        past.setOnClickListener(this);
        schedule = v.findViewById(R.id.myschedulehomedriver);
        schedule.setOnClickListener(this);
        usergreeting = v.findViewById(R.id.userGreetingdriver);
        greetingName = v.findViewById(R.id.greetingnamedriver);
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
            case R.id.ongoingrideshomedriver:
                Bundle bundle2 = new Bundle();
                bundle2.putString("key", fullname);
                AllOngoingRidesDriverFragment fragment2 = new AllOngoingRidesDriverFragment();
                fragment2.setArguments(bundle2);
                getFragmentManager().beginTransaction().replace(R.id.fragment_container3, fragment2).addToBackStack(null).commit();
                break;

            case R.id.upcomingrideshomedriver:
                Bundle bundle = new Bundle();
                bundle.putString("key", fullname);
                ViewUpcomingRidesDriverFragment fragment = new ViewUpcomingRidesDriverFragment();
                fragment.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.fragment_container3, fragment).addToBackStack(null).commit();
                break;

            case R.id.completedrideshomedriver:
                Bundle bundle1 = new Bundle();
                bundle1.putString("key", fullname);
                ViewCompletedRidesDriverFragment fragment1 = new ViewCompletedRidesDriverFragment();
                fragment1.setArguments(bundle1);
                getFragmentManager().beginTransaction().replace(R.id.fragment_container3, fragment1).addToBackStack(null).commit();
                break;

            case R.id.myschedulehomedriver: getFragmentManager().beginTransaction().replace(R.id.fragment_container3, new DriverScheduleFragment()).addToBackStack(null).commit();
                break;
        }
    }
}
