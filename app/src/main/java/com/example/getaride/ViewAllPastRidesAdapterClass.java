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

public class ViewAllPastRidesAdapterClass extends FirebaseRecyclerAdapter<Rides, ViewAllPastRidesAdapterClass.ViewPastRides> {
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public ViewAllPastRidesAdapterClass(@NonNull FirebaseRecyclerOptions<Rides> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewAllPastRidesAdapterClass.ViewPastRides holder, int position, @NonNull Rides model) {
        holder.locations.setText(model.getPickup()+" to "+model.getDropoff());
        holder.date.setText("Date : "+model.getDate());
        holder.driver.setText("Driver : "+model.getDrivername());
        holder.price.setText("Income : "+model.getPrice());
        String keyId = this.getRef(position).getKey();
        holder.locations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity activity = (AppCompatActivity)v.getContext();
                activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container2, new ViewFullPastRide(model.getPickup(),model.getDropoff(), model.getCustomerEmail(), model.getCustomerName(), model.getDate(), model.getVehicleNumber(), model.getVehicletype(), model.getPrice(), model.getDrivername(), model.getLogStart(), model.getLogEnd(),model.getTime())).addToBackStack(null).commit();
            }
        });
    }

    @NonNull
    @Override
    public ViewPastRides onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_allpastrides, parent, false);
        return new ViewPastRides(v);
    }

    public class ViewPastRides extends RecyclerView.ViewHolder {
        TextView locations, date, driver, price;
        public ViewPastRides(@NonNull View itemView) {
            super(itemView);
            locations = itemView.findViewById(R.id.viewpastlocations);
            date = itemView.findViewById(R.id.viewpastdate);
            driver = itemView.findViewById(R.id.viewpastdriver);
            price = itemView.findViewById(R.id.viewpastprice);
        }
    }
}
