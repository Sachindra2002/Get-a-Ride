package com.example.getaride.AdapterClasses;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.getaride.Models.Rides;
import com.example.getaride.R;
import com.example.getaride.Fragments.ViewPastRideCustomer;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class ViewAllpastCustomerAdapterClass extends FirebaseRecyclerAdapter<Rides, ViewAllpastCustomerAdapterClass.ViewCompletedRidesCustomer> {
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public ViewAllpastCustomerAdapterClass(@NonNull FirebaseRecyclerOptions<Rides> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewCompletedRidesCustomer holder, int position, @NonNull Rides model) {
        holder.locations.setText(model.getPickup()+" to "+model.getDropoff());
        holder.date.setText("Date : "+model.getDate());
        holder.time.setText("Time : "+model.getTime());
        holder.drivername.setText("Driver : "+model.getDrivername());
        String keyId = this.getRef(position).getKey();
        holder.locations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity activity = (AppCompatActivity)v.getContext();
                activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ViewPastRideCustomer(model.getPickup(),model.getDropoff(), model.getDrivername(), model.getPrice(), model.getLogStart(), model.getLogEnd(),model.getDate(), model.getTime())).addToBackStack(null).commit();
            }
        });
    }

    @NonNull
    @Override
    public ViewCompletedRidesCustomer onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewpastridescustomer, parent, false);
        return new ViewCompletedRidesCustomer(v);
    }

    public class ViewCompletedRidesCustomer extends RecyclerView.ViewHolder {
        TextView locations, date, time, drivername;
        public ViewCompletedRidesCustomer(@NonNull View itemView) {
            super(itemView);
            locations = itemView.findViewById(R.id.viewpastlocationscustomer);
            date = itemView.findViewById(R.id.viewpastdatecustomer);
            time = itemView.findViewById(R.id.viewpasttimecustomer);
            drivername = itemView.findViewById(R.id.viewpastdrivernamecustomer);
        }
    }
}
