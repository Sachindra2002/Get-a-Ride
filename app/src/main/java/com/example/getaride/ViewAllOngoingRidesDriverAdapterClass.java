package com.example.getaride;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class ViewAllOngoingRidesDriverAdapterClass extends FirebaseRecyclerAdapter<Rides, ViewAllOngoingRidesDriverAdapterClass.ViewOngoingRides> {
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public ViewAllOngoingRidesDriverAdapterClass(@NonNull FirebaseRecyclerOptions<Rides> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewOngoingRides holder, int position, @NonNull Rides model) {
        holder.locations.setText(model.getPickup()+" to "+model.getDropoff());
        holder.date.setText("Date : "+model.getDate());
        holder.time.setText("Time : "+model.getTime());
        holder.customername.setText("Cutomer : "+model.getCustomerName());
        String keyId = this.getRef(position).getKey();
        holder.locations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity activity = (AppCompatActivity)v.getContext();
                activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container3, new OngoingRideFragment(model.getPickup(),model.getDropoff(), model.getCustomerEmail(), model.getDate(), model.getTime(),model.getCustomerName(), model.getCustomernumber(), model.getVehicleNumber(), model.getVehicletype(), model.getPrice(), model.getDrivername(), model.getStatus(),model.getDriverNumber(),model.getLogStart(), model.getLogEnd(),keyId)).addToBackStack(null).commit();
            }
        });
    }

    @NonNull
    @Override
    public ViewOngoingRides onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.selectongoingride, parent, false);
        return new ViewOngoingRides(v);
    }

    public class ViewOngoingRides extends RecyclerView.ViewHolder {
        TextView locations, date, time, customername;
        public ViewOngoingRides(@NonNull View itemView) {
            super(itemView);
            locations = itemView.findViewById(R.id.viewallongoinglocationdriver);
            date = itemView.findViewById(R.id.viewallongoingdatedriver);
            time = itemView.findViewById(R.id.viewallongoingtimedriver);
            customername = itemView.findViewById(R.id.viewallongoingcustomerdriver);
        }
    }
}
