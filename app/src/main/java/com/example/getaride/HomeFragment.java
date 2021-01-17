package com.example.getaride;

import android.Manifest;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.RectangularBounds;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class HomeFragment extends Fragment implements OnMapReadyCallback {
    private final static int MY_PERMISSIONS_REQUEST = 32;
    GoogleMap map;
    SupportMapFragment mapFragment;
    FusedLocationProviderClient client;
    TextView usergreeting;
    ArrayList<LatLng> listPoints;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        //usergreeting = v.findViewById(R.id.userGreeting);
     /*   Calendar c = Calendar.getInstance();
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
        }*/
        listPoints = new ArrayList<>();
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
                Log.i(getTag(), "Place" + place.getName() + ", " + place.getId());
                map.addMarker(new MarkerOptions().position(place.getLatLng()).title(place.getAddress()));
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(place.getLatLng(), 10));
                listPoints.add(place.getLatLng());
            }

            @Override
            public void onError(@NonNull Status status) {
                Log.i(getTag(), "An error occured " + status);
            }
        });

        AutocompleteSupportFragment autocompleteSupportFragment2 = (AutocompleteSupportFragment) getChildFragmentManager().findFragmentById(R.id.autocomplete_fragment2);
        autocompleteSupportFragment2.setTypeFilter(TypeFilter.ESTABLISHMENT);
        autocompleteSupportFragment2.setLocationBias(RectangularBounds.newInstance(
                new LatLng(-33.880490, 151.184363),
                new LatLng(-33.858754, 151.229596)
        ));
        autocompleteSupportFragment2.setCountries("LK");
        autocompleteSupportFragment2.setPlaceFields(Arrays.asList(Place.Field.LAT_LNG, Place.Field.ID, Place.Field.NAME));
        autocompleteSupportFragment2.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                Log.i(getTag(), "Place" + place.getName() + ", " + place.getId());
                map.addMarker(new MarkerOptions().position(place.getLatLng()).title(place.getAddress()));
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(place.getLatLng(), 10));
                listPoints.add(place.getLatLng());
            }

            @Override
            public void onError(@NonNull Status status) {
                Log.i(getTag(), "An error occured " + status);
            }
        });

        //searchView = v.findViewById(R.id.search);
        mapFragment = (SupportMapFragment) this.getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        client = LocationServices.getFusedLocationProviderClient(getActivity());
/*

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            getCurrentLocation();
        } else {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        }
*/

        return v;
    }


/*    private void getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST);
            return;
        }
        Task<Location> task = client.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    mapFragment.getMapAsync(new OnMapReadyCallback() {
                        @Override
                        public void onMapReady(GoogleMap googleMap) {
                            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                            MarkerOptions options = new MarkerOptions().position(latLng).title("I am there");
                            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
                            googleMap.addMarker(options);
                        }
                    });
                }
            }
        });
    }*/

    @Override
    public void onMapReady(GoogleMap googleMap) {
        try {
            // Customise the styling of the base map using a JSON object defined
            // in a raw resource file.
            boolean success = googleMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            getContext(), R.raw.mapstyle));

            if (!success) {
                Log.e(getTag(), "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            Log.e(getTag(), "Can't find style. Error: ", e);
        }
        map = googleMap;
        map.getUiSettings().setZoomControlsEnabled(true);
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST);
            return;
        }
        map.setMyLocationEnabled(true);

        map.setOnCameraMoveListener(new GoogleMap.OnCameraMoveListener() {
            @Override
            public void onCameraMove() {
                if(listPoints.size() == 2)
                {
                    String url = getRequestUrl(listPoints.get(0), listPoints.get(1));
                    TaskRequestDirection taskRequestDirection = new TaskRequestDirection();
                    taskRequestDirection.execute(url);
                }
            }
        });

/*        map.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                if(listPoints.size() == 2)
                {
                    String url = getRequestUrl(listPoints.get(0), listPoints.get(1));
                    TaskRequestDirection taskRequestDirection = new TaskRequestDirection();
                    taskRequestDirection.execute(url);
                }
            }
        });*/

    }
    private String requestDirection(String reqUrl) throws IOException {
        String responseString = "";
        InputStream inputStream = null;
        HttpURLConnection httpURLConnection = null;
        try {
            URL url = new URL(reqUrl);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.connect();
            inputStream = httpURLConnection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuffer stringBuffer = new StringBuffer();
            String line = "";
            while ((line = bufferedReader.readLine()) != null)
            {
                stringBuffer.append(line);
            }
            responseString = stringBuffer.toString();
            bufferedReader.close();
            inputStreamReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(inputStream !=null)
            {
                inputStream.close();
            }
            httpURLConnection.disconnect();
        }
        return responseString;
    }

    private String getRequestUrl(LatLng origin, LatLng dest) {
        //Value of origin
        String str_org = "origin=" + origin.latitude + "," + origin.longitude;
        //Value of destination
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;
        //Set value enable the sensor
        String sensor = "sensor=false";
        //Mode for find direction
        String mode = "mode=driving";
        String key = "&key="+ "AIzaSyAJNtKlSYJsqtn6oBdM7m-e9jzUFsQsc88";
        //Build the full param
        String param = str_org + "&" + str_dest + "&" + sensor + "&" + mode+ "&" + key;
        //output format
        String output = "json";

        //Create url to request
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + param ;
        return url;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 44) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST);
                    return;
                }
                map.setMyLocationEnabled(true);
            }
        }
    }
    public class TaskRequestDirection extends AsyncTask<String, Void, String >
    {

        @Override
        protected String doInBackground(String... strings) {
            String responseString = ";";
            try {
                responseString = requestDirection(strings[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return responseString;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            TaskParser taskParser = new TaskParser();
            taskParser.execute(s);
        }
    }
    public class TaskParser extends AsyncTask<String, Void,List<List<HashMap<String, String>>>>
    {

        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... strings) {
            JSONObject jasonObject = null;
            List<List<HashMap<String, String>>> routes = null;

            try {
                jasonObject = new JSONObject(strings[0]);
                DirectionsParser directionsParser = new DirectionsParser();
                routes = directionsParser.parse(jasonObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return routes;

        }

        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> lists) {
            ArrayList points = null;
            PolylineOptions polylineOptions = null;
            for(List<HashMap<String, String>> path : lists)
            {
                points = new ArrayList();
                polylineOptions = new PolylineOptions();
                for(HashMap<String, String> point : path)
                {
                    double lat = Double.parseDouble(point.get("lat"));
                    double lon = Double.parseDouble(point.get("lon"));

                    points.add(new LatLng(lat, lon) );
                }
                polylineOptions.addAll(points);
                polylineOptions.width(15);
                polylineOptions.color(0xFF0000FF);
                polylineOptions.geodesic(true);
                map.addPolyline(polylineOptions);
            }

            if(polylineOptions!=null)
            {
                map.addPolyline(polylineOptions);
            }else
            {
                Toast.makeText(getContext(), "Direction not found!", Toast.LENGTH_SHORT).show();
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


/*if(listPoints.size() == 2)
                {
                    listPoints.clear();
                    map.clear();
                }
                listPoints.add(latLng);
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(latLng);

                if(listPoints.size() == 1)
                {
                    markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                }else
                {
                    markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                }
                map.addMarker(markerOptions);*/





