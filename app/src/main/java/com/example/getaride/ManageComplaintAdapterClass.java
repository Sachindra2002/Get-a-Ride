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

public class ManageComplaintAdapterClass extends FirebaseRecyclerAdapter<Complaints, ManageComplaintAdapterClass.ManagecomplaintList> {
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public ManageComplaintAdapterClass(@NonNull FirebaseRecyclerOptions<Complaints> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ManageComplaintAdapterClass.ManagecomplaintList holder, int position, @NonNull Complaints model) {
        holder.complaint.setText(model.getInquiry());
        holder.status.setText(model.getStatus());
        holder.complainer.setText(model.getComplainee());
        holder.date.setText(model.getDate());

        holder.complaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity activity = (AppCompatActivity)v.getContext();
                activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container2, new ViewFullComplaint(model.getComplaint(),model.getStatus(),model.getComplainee(),model.getDate(),model.getInquiry(), model.getId())).addToBackStack(null).commit();
            }
        });
    }

    @NonNull
    @Override
    public ManagecomplaintList onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.manage_complaints,parent,false);
        return new ManagecomplaintList(view);
    }


    public class ManagecomplaintList extends RecyclerView.ViewHolder {
        TextView complaint, status, complainer, date;
        public ManagecomplaintList(@NonNull View itemView) {
            super(itemView);
            complaint = itemView.findViewById(R.id.complaint);
            status = itemView.findViewById(R.id.complaintstatus);
            complainer = itemView.findViewById(R.id.complainee);
            date = itemView.findViewById(R.id.complaintDate);

        }
    }
}
