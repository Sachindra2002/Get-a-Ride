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

public class ViewAllPastRidesDriverAdapterClass extends FirebaseRecyclerAdapter<Rides, ViewAllPastRidesDriverAdapterClass.ViewCompletedRidesDriver> {
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public ViewAllPastRidesDriverAdapterClass(@NonNull FirebaseRecyclerOptions<Rides> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewAllPastRidesDriverAdapterClass.ViewCompletedRidesDriver holder, int position, @NonNull Rides model) {
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
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container3, new ViewPastRideDriver(model.getPickup(),model.getDropoff(), model.getCustomerName(), model.getPrice(), model.getLogStart(), model.getLogEnd(),model.getDate(), model.getTime())).addToBackStack(null).commit();
            }
        });
    }

    @NonNull
    @Override
    public ViewAllPastRidesDriverAdapterClass.ViewCompletedRidesDriver onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewpastridesdriver, parent, false);
        return new ViewCompletedRidesDriver(v);
    }

    public class ViewCompletedRidesDriver extends RecyclerView.ViewHolder {
        TextView locations, date, time, customername;
        public ViewCompletedRidesDriver(@NonNull View itemView) {
            super(itemView);
            locations = itemView.findViewById(R.id.viewpastlocationsdriver);
            date = itemView.findViewById(R.id.viewpastdatedriver);
            time = itemView.findViewById(R.id.viewpasttimedriver);
            customername = itemView.findViewById(R.id.viewpastcustomernamedriver);
        }
    }
}
