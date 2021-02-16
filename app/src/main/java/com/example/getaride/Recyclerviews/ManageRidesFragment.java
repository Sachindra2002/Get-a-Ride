package com.example.getaride.Recyclerviews;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.getaride.AdapterClasses.ManageRidesAdapterclass;
import com.example.getaride.Models.Rides;
import com.example.getaride.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ManageRidesFragment extends Fragment {
    private RecyclerView pendingridelist;
    private DatabaseReference mDatabase;
    ManageRidesAdapterclass adapterClass;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_pendingrides, container,false);
        pendingridelist = (RecyclerView)v.findViewById(R.id.managependingrides);
        pendingridelist.setHasFixedSize(true);
        pendingridelist.setLayoutManager(new LinearLayoutManager(getContext()));
        FirebaseRecyclerOptions<Rides> pendingList = new FirebaseRecyclerOptions.Builder<Rides>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("Rides").orderByChild("status").equalTo("pending"), Rides.class)
                .build();


        adapterClass = new ManageRidesAdapterclass(pendingList);
        pendingridelist.setAdapter(adapterClass);
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
