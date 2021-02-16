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

import com.example.getaride.AdapterClasses.ViewAllOngoingRidesDriverAdapterClass;
import com.example.getaride.Models.Rides;
import com.example.getaride.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AllOngoingRidesDriverFragment extends Fragment {
    private RecyclerView allongoinridelist;
    private DatabaseReference mDatabase;
    ViewAllOngoingRidesDriverAdapterClass adapterClass;
    String fullname;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_ongoingridesdriver, container,false);
        Bundle bundle = this.getArguments();
        fullname = bundle.getString("key");
        allongoinridelist = (RecyclerView)v.findViewById(R.id.ongoingridefordriver);
        allongoinridelist.setHasFixedSize(true);
        allongoinridelist.setLayoutManager(new LinearLayoutManager(getContext()));
        FirebaseRecyclerOptions<Rides> ongoing = new FirebaseRecyclerOptions.Builder<Rides>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("Ongoing").orderByChild("drivername").equalTo(fullname), Rides.class)
                .build();


        adapterClass = new ViewAllOngoingRidesDriverAdapterClass(ongoing);
        allongoinridelist.setAdapter(adapterClass);
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
