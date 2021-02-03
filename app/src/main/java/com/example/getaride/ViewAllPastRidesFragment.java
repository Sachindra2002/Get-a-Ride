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

public class ViewAllPastRidesFragment extends Fragment {
    private RecyclerView allpastridelist;
    private DatabaseReference mDatabase;
    ViewAllPastRidesAdapterClass adapterClass;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_managedrivers, container,false);
        allpastridelist = (RecyclerView)v.findViewById(R.id.managedrivers);
        allpastridelist.setHasFixedSize(true);
        allpastridelist.setLayoutManager(new LinearLayoutManager(getContext()));
        FirebaseRecyclerOptions<Rides> pastRideList = new FirebaseRecyclerOptions.Builder<Rides>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("PastRides"), Rides.class)
                .build();


        adapterClass = new ViewAllPastRidesAdapterClass(pastRideList);
        allpastridelist.setAdapter(adapterClass);
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
