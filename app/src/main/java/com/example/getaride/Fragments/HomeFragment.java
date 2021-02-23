package com.example.getaride.Fragments;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.example.getaride.Models.Rides;
import com.example.getaride.R;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.RectangularBounds;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Locale;

public class HomeFragment extends Fragment implements View.OnClickListener {
    private final static int MY_PERMISSIONS_REQUEST = 32;
    AutocompleteSupportFragment autocompleteSupportFragment;
    AutocompleteSupportFragment autocompleteSupportFragment2;
    TextView usergreeting, greetingName;
    TextView time, Date;
    private int mHour, mMinute;
    RadioGroup radioGroup;
    String Pickup, Dropoff;
    String Time, Ddate;
    String fullname, contact;
    DatabaseReference mDatabase;
    Button getaRide;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        usergreeting = v.findViewById(R.id.userGreeting);
        Calendar c = Calendar.getInstance();
        int timeOfDay = c.get(Calendar.HOUR_OF_DAY);
        if (timeOfDay >= 0 && timeOfDay < 12) {
            usergreeting.setText("Morning");
        } else if (timeOfDay >= 12 && timeOfDay < 14) {
            usergreeting.setText("Afternoon");
        } else if (timeOfDay >= 14 && timeOfDay < 21) {
            usergreeting.setText("Evening");
        } else if (timeOfDay >= 21 && timeOfDay < 24) {
            usergreeting.setText("Evening");
        } else {
            usergreeting.setText("Null");
        }
        getaRide = v.findViewById(R.id.getaride);
        getaRide.setOnClickListener(this);
        radioGroup = v.findViewById(R.id.vehicleradiogroup);
        time = v.findViewById(R.id.timepickup);
        Date = v.findViewById(R.id.timedate);
        greetingName = v.findViewById(R.id.greetingname);
        final Calendar cal = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userid = user.getUid();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");
        ref.child(userid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String name = dataSnapshot.child("fullName").getValue().toString();
                greetingName.setText(name);
                fullname = dataSnapshot.child("fullName").getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference("Users");
        ref1.child(userid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String number = dataSnapshot.child("phone").getValue().toString();
                //greetingName.setText(number);
                contact = dataSnapshot.child("phone").getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(),
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {

                                time.setText(hourOfDay + ":" + minute);
                                Time = hourOfDay + ":" +minute;
                            }
                        }, mHour, mMinute, true);
                timePickerDialog.show();

            }
        });
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

                Date.setText(sdf.format(cal.getTime()));
                Ddate = sdf.format(cal.getTime());
            }

        };
        Date.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(getContext(), date, cal
                        .get(Calendar.YEAR), cal.get(Calendar.MONTH),
                        cal.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        String apikey = "YOUR_API_KEY";
        Places.initialize(getContext(), apikey);
        PlacesClient placesClient = Places.createClient(getContext());

        autocompleteSupportFragment = (AutocompleteSupportFragment) getChildFragmentManager().findFragmentById(R.id.autocomplete_fragment);
        autocompleteSupportFragment.setTypeFilter(TypeFilter.ESTABLISHMENT);
        autocompleteSupportFragment.setLocationBias(RectangularBounds.newInstance(
                new LatLng(-33.880490, 151.184363),
                new LatLng(-33.858754, 151.229596)
        ));
        autocompleteSupportFragment.setCountries("LK");
        autocompleteSupportFragment.setHint("Enter pick-up location");
        ((EditText) autocompleteSupportFragment.getView().findViewById(R.id.places_autocomplete_search_input)).setTextSize(20.0f);
        ((EditText) autocompleteSupportFragment.getView().findViewById(R.id.places_autocomplete_search_input)).setTextColor(Color.WHITE);
        ((EditText) autocompleteSupportFragment.getView().findViewById(R.id.places_autocomplete_search_input)).setHintTextColor(Color.WHITE);
        autocompleteSupportFragment.getView().findViewById(R.id.places_autocomplete_search_button).setVisibility(View.GONE);
        autocompleteSupportFragment.setPlaceFields(Arrays.asList(Place.Field.LAT_LNG, Place.Field.ID, Place.Field.NAME));
        autocompleteSupportFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                Log.i(getTag(), "Place" + place.getName() + ", " + place.getId());
                Pickup = place.getName();

            }

            @Override
            public void onError(@NonNull Status status) {
                Log.i(getTag(), "An error occured " + status);
            }
        });

        autocompleteSupportFragment2 = (AutocompleteSupportFragment) getChildFragmentManager().findFragmentById(R.id.autocomplete_fragment2);
        autocompleteSupportFragment2.setTypeFilter(TypeFilter.ESTABLISHMENT);
        autocompleteSupportFragment2.setLocationBias(RectangularBounds.newInstance(
                new LatLng(-33.880490, 151.184363),
                new LatLng(-33.858754, 151.229596)
        ));
        int s = 424141;
        autocompleteSupportFragment2.setCountries("LK");
        //autocompleteSupportFragment2.getView().setBackgroundColor(Color.WHITE);
        ((EditText) autocompleteSupportFragment2.getView().findViewById(R.id.places_autocomplete_search_input)).setTextSize(20.0f);
        ((EditText) autocompleteSupportFragment2.getView().findViewById(R.id.places_autocomplete_search_input)).setTextColor(Color.WHITE);
        ((EditText) autocompleteSupportFragment2.getView().findViewById(R.id.places_autocomplete_search_input)).setHintTextColor(Color.WHITE);
        autocompleteSupportFragment2.getView().findViewById(R.id.places_autocomplete_search_button).setVisibility(View.GONE);
        autocompleteSupportFragment2.setHint("Where To?");
        autocompleteSupportFragment2.setPlaceFields(Arrays.asList(Place.Field.LAT_LNG, Place.Field.ID, Place.Field.NAME));
        autocompleteSupportFragment2.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                Log.i(getTag(), "Place" + place.getName() + ", " + place.getId());
                Dropoff = place.getName();
            }

            @Override
            public void onError(@NonNull Status status) {
                Log.i(getTag(), "An error occured " + status);
            }
        });

        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.getaride:
                GetARide();
                break;
        }
    }

    private void GetARide() {
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Rides");
        String dDate = Ddate;
        String vehicleType = null;
        String pickup = Pickup;
        String dropoff = Dropoff;
        String status = "pending";
        String driverName = "Pending";
        String time = Time;
        String number = contact;
        String Name = fullname;
        String driverNumber = "pending";
        String vehicleNumber = "pending";
        String logStart = "pending";
        String logEnd = "pending";
        String price = "pending";
        String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        if(pickup == null)
        {
            Toast.makeText(getContext(), "Please enter a pick up location", Toast.LENGTH_SHORT).show();
            ((EditText) autocompleteSupportFragment.getView().findViewById(R.id.places_autocomplete_search_input)).setError("Please enter a pick up location");
            ((EditText) autocompleteSupportFragment.getView().findViewById(R.id.places_autocomplete_search_input)).requestFocus();
            return;
        }
        if(dropoff == null)
        {
            Toast.makeText(getContext(), "Please enter a destination", Toast.LENGTH_SHORT).show();
            ((EditText) autocompleteSupportFragment2.getView().findViewById(R.id.places_autocomplete_search_input)).setError("Please enter a destination");
            ((EditText) autocompleteSupportFragment2.getView().findViewById(R.id.places_autocomplete_search_input)).requestFocus();
            return;
        }
        if(time == null)
        {
            time = "immediate";
        }
        int radioID = radioGroup.getCheckedRadioButtonId();
        switch (radioID) {
            case R.id.sedan:
                vehicleType = "Sedan";
                break;

            case R.id.miniCar:
                vehicleType = "Mini Car";
                break;
            case R.id.miniVan:
                vehicleType = "Mini Van";
                break;
            case R.id.van:
                vehicleType = "Van";
                break;
        }

        Rides ride = new Rides(Name, email, pickup, dropoff, driverName, vehicleType, time, status, dDate, number, driverNumber, vehicleNumber, logStart, logEnd, price);
        mDatabase.push().setValue(ride).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                {
                    Toast.makeText(getContext(), "Ride booked successfully!", Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(getContext(), "Could not book ride :/", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}






