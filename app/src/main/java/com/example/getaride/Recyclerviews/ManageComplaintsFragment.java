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

import com.example.getaride.AdapterClasses.ManageComplaintAdapterClass;
import com.example.getaride.Models.Complaints;
import com.example.getaride.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ManageComplaintsFragment extends Fragment {
    private RecyclerView cList;
    ManageComplaintAdapterClass adapterClass;
    private DatabaseReference mDatabase;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_managecomplaints, container, false);

        cList = (RecyclerView) v.findViewById(R.id.managecomplaints);
        cList.setHasFixedSize(true);
        cList.setLayoutManager(new LinearLayoutManager(getContext()));
        FirebaseRecyclerOptions<Complaints> complaintList = new FirebaseRecyclerOptions.Builder<Complaints>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("Complaints").orderByChild("status").equalTo("pending"), Complaints.class)
                .build();
        adapterClass = new ManageComplaintAdapterClass(complaintList);
        cList.setAdapter(adapterClass);
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
