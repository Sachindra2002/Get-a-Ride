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

public class ViewUpcomingridesCustomerFragment extends Fragment {
    private RecyclerView allUpcomingRides;
    private DatabaseReference mDatabase;
    ViewAllUpcomingRidesCustomerAdapterClass adapterClass;
    String fullname;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_viewupcomingridescustomer, container,false);
        Bundle bundle = this.getArguments();
        fullname = bundle.getString("key");
        allUpcomingRides = (RecyclerView)v.findViewById(R.id.viewupcomingridescustomer);
        allUpcomingRides.setHasFixedSize(true);
        allUpcomingRides.setLayoutManager(new LinearLayoutManager(getContext()));
        FirebaseRecyclerOptions<Rides> upcomingRides = new FirebaseRecyclerOptions.Builder<Rides>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("PendingRides").orderByChild("customerName").equalTo(fullname), Rides.class)
                .build();


        adapterClass = new ViewAllUpcomingRidesCustomerAdapterClass(upcomingRides);
        allUpcomingRides.setAdapter(adapterClass);
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
