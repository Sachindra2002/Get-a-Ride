package com.example.getaride.AdapterClasses;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.getaride.Fragments.UpdateScheduleFragment;
import com.example.getaride.Models.Schedule;
import com.example.getaride.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class ManageSchedulesAdapterclass extends FirebaseRecyclerAdapter<Schedule, ManageSchedulesAdapterclass.ManageShedules> {
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public ManageSchedulesAdapterclass(@NonNull FirebaseRecyclerOptions<Schedule> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ManageShedules holder, int position, @NonNull Schedule model) {
        holder.driverName.setText(model.getFullName());
        holder.date.setText("Date : "+model.getDate());
        holder.sTime.setText("Start Time : "+model.getsTime());
        holder.eTime.setText("End Time : "+model.geteTime());
        String keyId = this.getRef(position).getKey();
        holder.driverName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity activity = (AppCompatActivity)v.getContext();
                activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container2, new UpdateScheduleFragment(model.getFullName(),keyId)).addToBackStack(null).commit();
            }
        });
    }

    @NonNull
    @Override
    public ManageShedules onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.manage_schedules, parent, false);
        return new ManageShedules(v);
    }

    public class ManageShedules extends RecyclerView.ViewHolder {
        TextView driverName, date, sTime, eTime;
        public ManageShedules(@NonNull View itemView) {
            super(itemView);
             driverName = itemView.findViewById(R.id.driverFullNameschedule);
             date = itemView.findViewById(R.id.driverdateschedule);
             sTime = itemView.findViewById(R.id.driverstarttimeschedule);
             eTime = itemView.findViewById(R.id.driverendtimeschedule);
        }
    }
}
