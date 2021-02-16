package com.example.getaride.AdapterClasses;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.getaride.Fragments.ViewOngoinrideDetailed;
import com.example.getaride.Models.Rides;
import com.example.getaride.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class ViewAllOngoingRidesDispatcherAdapterClass extends FirebaseRecyclerAdapter<Rides, ViewAllOngoingRidesDispatcherAdapterClass.viewAllOngoingRides> {


    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public ViewAllOngoingRidesDispatcherAdapterClass(@NonNull FirebaseRecyclerOptions<Rides> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull viewAllOngoingRides holder, int position, @NonNull Rides model) {
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
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container2, new ViewOngoinrideDetailed(model.getPickup(),model.getDropoff(), model.getCustomerName(), model.getStatus(),model.getDrivername() ,model.getLogStart(), model.getLogEnd(),model.getDate(), model.getTime())).addToBackStack(null).commit();
            }
        });
    }

    @NonNull
    @Override
    public viewAllOngoingRides onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewallongoinridesdispatcher, parent, false);
        return new viewAllOngoingRides(v);
    }

    public class viewAllOngoingRides extends RecyclerView.ViewHolder {
        TextView locations, date, time, customername;
        public viewAllOngoingRides(@NonNull View itemView) {
            super(itemView);
            locations = itemView.findViewById(R.id.viewallongoinglocationdispatcher);
            date = itemView.findViewById(R.id.viewallongoingdatedispatcher);
            time = itemView.findViewById(R.id.viewallongoingtimedispatcher);
            customername = itemView.findViewById(R.id.viewallongoingcustomerdispatcher);
        }
    }
}
