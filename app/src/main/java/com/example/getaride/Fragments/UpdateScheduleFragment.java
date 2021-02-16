package com.example.getaride.Fragments;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.getaride.Models.Schedule;
import com.example.getaride.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class UpdateScheduleFragment extends Fragment implements View.OnClickListener {
    Spinner locations;
    TextView dateT, sTime, eTime, drivername;
    Button update;
    String fullName, keyID;
    DatabaseReference databaseReference;
    DatabaseReference mDatabase;
    String spinnerName, sTimee, Ddate, eTimee;
    private int mHour, mMinute;

    public UpdateScheduleFragment(String fullName, String keyId) {
        this.fullName = fullName;
        this.keyID = keyId;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_updateschedule, container, false);
        locations = v.findViewById(R.id.scheduleSpinner2);
        dateT = v.findViewById(R.id.scheduleDate);
        sTime = v.findViewById(R.id.scheduleTimeStart);
        eTime = v.findViewById(R.id.scheduleTimeEnd);
        update = v.findViewById(R.id.updateSchedule);
        drivername = v.findViewById(R.id.drivernameschedule);
        drivername.setText(fullName);
        update.setOnClickListener(this);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.locations, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        locations.setAdapter(adapter);
        sTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(),
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {

                                sTime.setText(hourOfDay + ":" + minute);
                                sTimee = hourOfDay + ":" +minute;
                            }
                        }, mHour, mMinute, true);
                timePickerDialog.show();
            }
        });
        eTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(),
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {

                                eTime.setText(hourOfDay + ":" + minute);
                                eTimee = hourOfDay + ":" +minute;
                            }
                        }, mHour, mMinute, true);
                timePickerDialog.show();
            }
        });
        final Calendar cal = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                cal.set(Calendar.YEAR, year);
                cal.set(Calendar.MONTH, monthOfYear);
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

            private void updateLabel() {
                String myFormat = "dd/MM/yyyy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                dateT.setText(sdf.format(cal.getTime()));
                Ddate = sdf.format(cal.getTime());
            }

        };
        dateT.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(getContext(), date, cal
                        .get(Calendar.YEAR), cal.get(Calendar.MONTH),
                        cal.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.updateSchedule:
                updateSchedule();
                break;
        }

    }

    private void updateSchedule() {
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Schedules");
        String location = locations.getSelectedItem().toString();
        String date = Ddate;
        String startTime = sTimee;
        String endTime = eTimee;
        Schedule schedule = new Schedule(fullName, location, date, startTime, endTime);
        Map<String, Object> update = new HashMap<>();
        update.put(keyID+"/date", date);
        update.put(keyID+"/sTime", startTime);
        update.put(keyID+"/eTime", endTime);
        update.put(keyID+"/location", location);
        mDatabase.updateChildren(update).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                {
                    Toast.makeText(getContext(), "Schedule updated Successfully", Toast.LENGTH_SHORT).show();
                }else
                {
                    Toast.makeText(getContext(), "Schedule could not be updated", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
