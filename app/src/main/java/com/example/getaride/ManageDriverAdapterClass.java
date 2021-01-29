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

public class ManageDriverAdapterClass extends FirebaseRecyclerAdapter<Users, ManageDriverAdapterClass.ManagedriverList> {

     /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public ManageDriverAdapterClass(@NonNull FirebaseRecyclerOptions<Users> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ManagedriverList holder, int position, @NonNull Users model) {
        holder.fullname.setText(model.getFullName());
        holder.email.setText("Vehicle Type : "+model.getVehicleType());
        holder.address.setText("Driver Location : "+model.getCurrentLocation());
        holder.dob.setText("Driver Status : "+model.getStatus());
        String keyId = this.getRef(position).getKey();
        holder.fullname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity activity = (AppCompatActivity)v.getContext();
                activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container2, new UpdateDriverFragment(model.getFullName(),model.getEmail(),model.getAddress(),model.getDob(),model.getPhone(), model.getVehicleType(), model.getVehicleNumber(), keyId)).addToBackStack(null).commit();
            }
        });


    }

    @NonNull
    @Override
    public ManagedriverList onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.manage_drivers,parent,false);
        return new ManagedriverList(view);
    }

    public class ManagedriverList extends RecyclerView.ViewHolder
    {
        TextView fullname, email, address, dob;
        public ManagedriverList(@NonNull View itemView) {
            super(itemView);

            fullname = itemView.findViewById(R.id.driverFullName);
            email = itemView.findViewById(R.id.driveremail);
            address = itemView.findViewById(R.id.driveraddress);
            dob = itemView.findViewById(R.id.driverdob);
        }
    }
}
