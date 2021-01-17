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

public class ManageDriversFragment extends Fragment {
    private RecyclerView driverList;
    private DatabaseReference mDatabase;
    ManageDriverAdapterClass adapterClass;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_managedrivers, container,false);

       /* mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
        mDatabase.keepSynced(true);*/
        driverList = (RecyclerView)v.findViewById(R.id.managedrivers);
        driverList.setHasFixedSize(true);
        driverList.setLayoutManager(new LinearLayoutManager(getContext()));
        FirebaseRecyclerOptions<Users> list = new FirebaseRecyclerOptions.Builder<Users>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("Users").orderByChild("role").equalTo("driver"), Users.class)
                .build();

        adapterClass = new ManageDriverAdapterClass(list);
        driverList.setAdapter(adapterClass);
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
