package com.example.getaride;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ManageScheduleFragment extends Fragment {
    private RecyclerView driverschedules;
    private DatabaseReference mDatabase;
    ManageSchedulesAdapterclass adapterClass;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_manageschedules, container,false);
        driverschedules = (RecyclerView)v.findViewById(R.id.managedschedulesss);
        driverschedules.setHasFixedSize(true);
        driverschedules.setLayoutManager(new LinearLayoutManager(getContext()));
        FirebaseRecyclerOptions<Schedule> schedules = new FirebaseRecyclerOptions.Builder<Schedule>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("Schedules"), Schedule.class)
                .build();


        adapterClass = new ManageSchedulesAdapterclass(schedules);
        driverschedules.setAdapter(adapterClass);
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        adapterClass.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapterClass.stopListening();
    }
}
