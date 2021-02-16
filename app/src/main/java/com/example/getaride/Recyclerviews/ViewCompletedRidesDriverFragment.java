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

import com.example.getaride.Models.Rides;
import com.example.getaride.R;
import com.example.getaride.AdapterClasses.ViewAllPastRidesDriverAdapterClass;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ViewCompletedRidesDriverFragment extends Fragment {
    private RecyclerView allpastridelist;
    private DatabaseReference mDatabase;
    ViewAllPastRidesDriverAdapterClass adapterClass;
    String fullname;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_viewpastridesdriver, container,false);
        Bundle bundle = this.getArguments();
        fullname = bundle.getString("key");
        allpastridelist = (RecyclerView)v.findViewById(R.id.viewallpastridesdriver);
        allpastridelist.setHasFixedSize(true);
        allpastridelist.setLayoutManager(new LinearLayoutManager(getContext()));
        FirebaseRecyclerOptions<Rides> pastRideList = new FirebaseRecyclerOptions.Builder<Rides>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("Completed").orderByChild("drivername").equalTo(fullname), Rides.class)
                .build();


        adapterClass = new ViewAllPastRidesDriverAdapterClass(pastRideList);
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
