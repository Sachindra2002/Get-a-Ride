package com.example.getaride;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.Calendar;

public class HomeFragment extends Fragment {
RadioGroup radioGroup;
RadioButton radioButton;
TextView usergreeting;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        usergreeting = v.findViewById(R.id.userGreeting);

        Calendar c = Calendar.getInstance();
        int timeOfDay = c.get(Calendar.HOUR_OF_DAY);

        if(timeOfDay >= 0 && timeOfDay < 12){
            usergreeting.setText("Morning");
            //Toast.makeText(this, "Good Morning", Toast.LENGTH_SHORT).show();
        }else if(timeOfDay >= 12 && timeOfDay < 14){
            usergreeting.setText("Afternoon");
            //Toast.makeText(this, "Good Afternoon", Toast.LENGTH_SHORT).show();
        }else if(timeOfDay >= 14 && timeOfDay < 21){
            usergreeting.setText("Evening");
            //Toast.makeText(this, "Good Evening", Toast.LENGTH_SHORT).show();
        }else if(timeOfDay >= 21 && timeOfDay < 24){
            usergreeting.setText("Evening");
            //Toast.makeText(this, "Good Night", Toast.LENGTH_SHORT).show();
        }else{
            usergreeting.setText("Null");
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_dropdown_item_1line, PLACES);
        AutoCompleteTextView dropdown = (AutoCompleteTextView) v.findViewById(R.id.dropdown);
        AutoCompleteTextView pickup = (AutoCompleteTextView) v.findViewById(R.id.pickup);
        pickup.setAdapter(adapter);
        dropdown.setAdapter(adapter);

        String pickuplocation = pickup.getText().toString();
        String dropdownlocation = pickup.getText().toString();

        return v;
    }

    private static final String[] PLACES = new String[] {
            "Colombo", "Apiit", "Union Place", "Negombo", "IIT", "Pettah",
            "Kochikade", "CCC", "Sen-Saal Hyde Park", "Sen-Saal WTC", "Park Street Mews",
            "Nawaloka", "Colombo City Centre", "Dinemore", "Hemas Holdings", "Arpico Hyde Park",
            "Arpico Negombo", "Keels Dalupotha", "Keels Darley Road", "Keels Union Place",
            "Hilton Colombo Residencies", "Slave Island", "One Galle Face", "Kollupitiya",
            "Airport Depature Terminal", "Airport Arrivals Terminal", "Airport Parking", "Gflock",
            "Durdans Hospital"
    };

}
