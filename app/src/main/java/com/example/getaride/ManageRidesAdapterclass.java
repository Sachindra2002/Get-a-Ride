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

public class ManageRidesAdapterclass extends FirebaseRecyclerAdapter<Rides, ManageRidesAdapterclass.ManagePendingRidesList> {
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public ManageRidesAdapterclass(@NonNull FirebaseRecyclerOptions<Rides> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ManageRidesAdapterclass.ManagePendingRidesList holder, int position, @NonNull Rides model) {
        holder.Destination.setText(model.getDropoff());
        holder.pickup.setText(model.getPickup());
        holder.time.setText(model.getTime());
        holder.status.setText(model.getDate());
        String keyId = this.getRef(position).getKey();
        holder.Destination.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity activity = (AppCompatActivity)v.getContext();
                activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container2, new AssignRideFragment(model.getDropoff(),model.getPickup(),model.getTime(),model.getStatus(),model.getCustomerName(), model.getCustomerEmail(), model.getDrivername(), model.getVehicletype(),model.getCustomernumber(), model.getDate(),model.getDriverNumber(),model.getVehicleNumber(), keyId)).addToBackStack(null).commit();
            }
        });
    }

    @NonNull
    @Override
    public ManagePendingRidesList onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.manage_pendingrides,parent,false);
        return new ManagePendingRidesList(view);
    }


    public class ManagePendingRidesList extends RecyclerView.ViewHolder {
        TextView Destination, pickup, time, status;
        public ManagePendingRidesList(@NonNull View itemView) {
            super(itemView);

            Destination = itemView.findViewById(R.id.pendingdestination);
            pickup = itemView.findViewById(R.id.pendingpickup);
            time = itemView.findViewById(R.id.pendingtime);
            status = itemView.findViewById(R.id.pendingstatus);
        }
    }
}
