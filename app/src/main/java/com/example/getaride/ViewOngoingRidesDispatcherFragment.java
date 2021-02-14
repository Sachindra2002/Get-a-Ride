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

public class ViewOngoingRidesDispatcherFragment extends Fragment{
    private RecyclerView allOngoinRides;
    private DatabaseReference mDatabase;
    ViewAllOngoingRidesDispatcherAdapterClass adapterClass;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_viewallongoingridesdispatcher, container,false);
        allOngoinRides = (RecyclerView)v.findViewById(R.id.viewallongoingridesdispatcher);
        allOngoinRides.setHasFixedSize(true);
        allOngoinRides.setLayoutManager(new LinearLayoutManager(getContext()));
        FirebaseRecyclerOptions<Rides> ongoingRides = new FirebaseRecyclerOptions.Builder<Rides>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("Ongoing"), Rides.class)
                .build();


        adapterClass = new ViewAllOngoingRidesDispatcherAdapterClass(ongoingRides);
        allOngoinRides.setAdapter(adapterClass);
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
