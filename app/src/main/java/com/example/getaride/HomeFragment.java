package com.example.getaride;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import java.util.Calendar;

public class HomeFragment extends Fragment implements OnMapReadyCallback {
    private final static int MY_PERMISSIONS_REQUEST = 32;
    TextView usergreeting;
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
        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        return v;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

    }
}










/*        if(!Places.isInitialized())
        {
            Places.initialize(getContext(), apikey);
        }
        placesClient = Places.createClient(getContext());

        final AutocompleteSupportFragment autocompleteSupportFragment = (AutocompleteSupportFragment) getChildFragmentManager().findFragmentById(R.id.autoComplete_fragment);

        autocompleteSupportFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.LAT_LNG,Place.Field.NAME));
        autocompleteSupportFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                 final LatLng latLng = place.getLatLng();
                //Toast.makeText(getActivity(),""+latLng.latitude,Toast.LENGTH_SHORT ).show();

                Log.i("Place Api", "onPlaceSelected: "+latLng.latitude+"\n"+latLng.longitude);
            }

            @Override
            public void onError(@NonNull Status status) {

            }
        });*/



/*
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_dropdown_item_1line, PLACES);
        AutoCompleteTextView dropdown = (AutoCompleteTextView) v.findViewById(R.id.dropdown);
        AutoCompleteTextView pickup = (AutoCompleteTextView) v.findViewById(R.id.pickup);
        pickup.setAdapter(adapter);
        dropdown.setAdapter(adapter);

        String pickuplocation = pickup.getText().toString();
        String dropdownlocation = pickup.getText().toString();*/


/*   private static final String[] PLACES = new String[] {
            "Colombo", "Apiit", "Union Place", "Negombo", "IIT", "Pettah",
            "Kochikade", "CCC", "Sen-Saal Hyde Park", "Sen-Saal WTC", "Park Street Mews",
            "Nawaloka", "Colombo City Centre", "Dinemore", "Hemas Holdings", "Arpico Hyde Park",
            "Arpico Negombo", "Keels Dalupotha", "Keels Darley Road", "Keels Union Place",
            "Hilton Colombo Residencies", "Slave Island", "One Galle Face", "Kollupitiya",
            "Airport Depature Terminal", "Airport Arrivals Terminal", "Airport Parking", "Gflock",
            "Durdans Hospital"
    };*/





