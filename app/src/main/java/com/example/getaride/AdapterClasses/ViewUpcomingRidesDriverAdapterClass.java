package com.example.getaride.AdapterClasses;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.getaride.Fragments.StartRideFragment;
import com.example.getaride.Models.Rides;
import com.example.getaride.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class ViewUpcomingRidesDriverAdapterClass extends FirebaseRecyclerAdapter<Rides, ViewUpcomingRidesDriverAdapterClass.ViewUpcomingRides> {
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public ViewUpcomingRidesDriverAdapterClass(@NonNull FirebaseRecyclerOptions<Rides> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewUpcomingRidesDriverAdapterClass.ViewUpcomingRides holder, int position, @NonNull Rides model) {
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
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container3, new StartRideFragment(model.getPickup(),model.getDropoff(), model.getCustomerEmail(),model.getDate(), model.getTime(), model.getCustomerName(), model.getCustomernumber(), model.getDriverNumber(),model.getVehicleNumber(), model.getVehicletype(), model.getPrice(), model.getDrivername(), model.getLogStart(), model.getLogEnd(),keyId)).addToBackStack(null).commit();
            }
        });
    }

    @NonNull
    @Override
    public ViewUpcomingRides onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewupcomingridesdriver, parent, false);
        return new ViewUpcomingRides(v);
    }

    public class ViewUpcomingRides extends RecyclerView.ViewHolder {
        TextView locations, date, time, customername;
        public ViewUpcomingRides(@NonNull View itemView) {
            super(itemView);
            locations = itemView.findViewById(R.id.viewupcominglocationsdriver);
            date = itemView.findViewById(R.id.viewupcomingdatedriver);
            time = itemView.findViewById(R.id.viewupcomingtimedriver);
            customername = itemView.findViewById(R.id.viewupcomingcustomernamedriver);
        }
    }
}
