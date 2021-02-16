package com.example.getaride.AdapterClasses;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.getaride.Fragments.UpcomingRideFragmentCustomer;
import com.example.getaride.Models.Rides;
import com.example.getaride.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class ViewAllUpcomingRidesCustomerAdapterClass extends FirebaseRecyclerAdapter<Rides, ViewAllUpcomingRidesCustomerAdapterClass.Viewupcomingrides> {
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public ViewAllUpcomingRidesCustomerAdapterClass(@NonNull FirebaseRecyclerOptions<Rides> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull Viewupcomingrides holder, int position, @NonNull Rides model) {
        holder.locations.setText(model.getPickup()+" to "+model.getDropoff());
        holder.date.setText("Date : "+model.getDate());
        holder.time.setText("Time : "+model.getTime());
        holder.driverName.setText("Driver : "+model.getCustomerName());
        String keyId = this.getRef(position).getKey();
        holder.locations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity activity = (AppCompatActivity)v.getContext();
                activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new UpcomingRideFragmentCustomer(model.getPickup(),model.getDropoff(), model.getCustomerEmail(), model.getDate(), model.getTime(),model.getCustomerName(), model.getCustomernumber(), model.getVehicleNumber(), model.getVehicletype(), model.getPrice(), model.getDrivername(), model.getStatus(),model.getDriverNumber(),model.getLogStart(), model.getLogEnd(),keyId)).addToBackStack(null).commit();
            }
        });
    }

    @NonNull
    @Override
    public Viewupcomingrides onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewupcomingridescustomer, parent, false);
        return new Viewupcomingrides(v);
    }

    public class Viewupcomingrides extends RecyclerView.ViewHolder {
        TextView locations, date, time, driverName;
        public Viewupcomingrides(@NonNull View itemView) {
            super(itemView);
            locations = itemView.findViewById(R.id.viewupcominglocationscustomer);
            date = itemView.findViewById(R.id.viewupcomingdatecustomer);
            time = itemView.findViewById(R.id.viewupcomingtimecustomer);
            driverName = itemView.findViewById(R.id.viewupcomingdrivernamecustomer);
        }
    }
}
