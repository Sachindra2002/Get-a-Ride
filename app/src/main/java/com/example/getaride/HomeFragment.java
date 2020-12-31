package com.example.getaride;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.RectangularBounds;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import java.util.Arrays;
import java.util.Calendar;

public class HomeFragment extends Fragment implements OnMapReadyCallback {
    private final static int MY_PERMISSIONS_REQUEST = 32;
    GoogleMap map;
    SupportMapFragment mapFragment;
    FusedLocationProviderClient client;
    SearchView searchView;
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
        String apikey = "AIzaSyAJNtKlSYJsqtn6oBdM7m-e9jzUFsQsc88";
        Places.initialize(getContext(), apikey);
        PlacesClient placesClient = Places.createClient(getContext());

        AutocompleteSupportFragment autocompleteSupportFragment = (AutocompleteSupportFragment) getChildFragmentManager().findFragmentById(R.id.autocomplete_fragment);
        autocompleteSupportFragment.setTypeFilter(TypeFilter.ESTABLISHMENT);
        autocompleteSupportFragment.setLocationBias(RectangularBounds.newInstance(
                new LatLng(-33.880490, 151.184363),
                new LatLng(-33.858754, 151.229596)
        ));
        autocompleteSupportFragment.setCountries("LK");
        autocompleteSupportFragment.setPlaceFields(Arrays.asList(Place.Field.LAT_LNG, Place.Field.ID, Place.Field.NAME));
        autocompleteSupportFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                Log.i(getTag(), "Place"+place.getName()+", "+place.getId());
                map.addMarker(new MarkerOptions().position(place.getLatLng()).title(place.getAddress()));
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(place.getLatLng(), 10));
            }

            @Override
            public void onError(@NonNull Status status) {
                Log.i(getTag(), "An error occured "+status);
            }
        });

        //searchView = v.findViewById(R.id.search);
        mapFragment = (SupportMapFragment) this.getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        client = LocationServices.getFusedLocationProviderClient(getActivity());

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            getCurrentLocation();
        }else
        {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        }

        return v;
    }

    private void getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            //ActivityCompat.requestPermissions(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION, MY_PERMISSIONS_REQUEST);
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Task<Location> task = client.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null)
                {
                    mapFragment.getMapAsync(new OnMapReadyCallback() {
                        @Override
                        public void onMapReady(GoogleMap googleMap) {
                            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                            MarkerOptions options = new MarkerOptions().position(latLng).title("I am there");
                            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,10));
                            googleMap.addMarker(options);
                        }
                    });
                }
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == 44)
        {
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                getCurrentLocation();
            }
        }
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


/*        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                String location = searchView.getQuery().toString();
                List<Address> addressList = null;

                if (location != null || !location.equals("")) {
                    Geocoder geocoder = new Geocoder(getContext());
                    try {
                        addressList = geocoder.getFromLocationName(location, 1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Address address = addressList.get(0);
                    LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                    map.addMarker(new MarkerOptions().position(latLng).title(location));
                    map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        mapFragment.getMapAsync(this);*/





