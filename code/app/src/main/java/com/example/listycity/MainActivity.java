package com.example.listycity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    //need these 3 attributes to implement functionality of the list
    ListView cityList;
    ArrayAdapter<String> cityAdapter;  //ArrayAdapter is a class that holds strings
    ArrayList<String> dataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); //**NEED; calls onCreate of parent class (AppCompatActivity)
        //all activities must extend AppCompatActivity to be an activity
        setContentView(R.layout.activity_main); //**NEED; sets display/UI - tells activity what the layout is look like and all the elements

        EdgeToEdge.enable(this); //not necessary

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        }); //not necessary

        //FIRST MUST CONNECT TO EACH visual UI ELEMENT that you wanna interact with:
        cityList = findViewById(R.id.city_list);
        Button addCity = findViewById(R.id.addCityButton);
        Button deleteCity = findViewById(R.id.deleteCityButton);
        EditText inputText = findViewById(R.id.inputCity);
        Button confirm = findViewById(R.id.confirmButton);

        //Initialize confirmButton as disabled:
        confirm.setEnabled(false);

        String[] cities = {"Edmonton","Paris","London","Vancouver"};    //instantiate into string array
        dataList = new ArrayList<>();
        dataList.addAll(Arrays.asList(cities)); //convert primitive Array to ArrayList, and send to addAll (cuz addAll accepts collections, not primitive list)
        cityAdapter=new ArrayAdapter<>(this, R.layout.content, dataList);
        // ^ArrayAdapter args:
        // 1st arg: Type Context - tells ArrayAdapter on which activity/fragment to operate on
        // 2nd arg: Type Int - layout file defining what each element in the array looks like
        // 3rd arg: Type ArrayList - container that holds content - the data source used by the adapter
        cityList.setAdapter(cityAdapter); //tell listview who its adapter is
        // Adapter adapts data to display


        //FUNCTION - Delete city from List:
        //setOnItemClickListener() called when an individ element in ListView is clicked
        cityList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //parameters: view refers to elements inside the view/list, position sets to whichever index was clicked
                //HERE - Define behaviour when indiv elemen on listview is clicked:

                String selectedCity = (String) parent.getItemAtPosition(position); //get which item is clicked

                //Once Item is clicked, then listen for when deleteCityButton is clicked:
                deleteCity.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //When deleteCityButton is clicked, then delete from the ListView datasource:
                        dataList.remove(position);  //dataList is the data source for the ListView
                        cityAdapter.notifyDataSetChanged(); //notifies adapter, which refreshes to UI ListView element

                        //short confirmation pop-up message:
                        Toast deleteConfirmed =Toast.makeText(MainActivity.this, selectedCity+" has been deleted.", Toast.LENGTH_SHORT);
                        deleteConfirmed.show();
                    }
                });
            }
        });


        //FUNCTION - Add a city to list
        //flow - click addCityButton, then can type a city then click confirm
        addCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //HERE - define what to do when addCity is clicked (ie. crash the app, display a string etc):

                Toast promptAddCity = Toast.makeText(MainActivity.this, "type a city and confirm", Toast.LENGTH_SHORT);
                promptAddCity.show();

                //once addCity clicked, enable confirmButton
                confirm.setEnabled(true);

                //Wait for confirmButton to be clicked:
                confirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //HERE - ADD THE NEW CITY (in EditText) TO CITY LIST
                        String newCity = inputText.getText().toString(); //use toString() to convert 'editable' to 'String'
                        //dataList.add( inputText.getText().toString() );
                        dataList.add(newCity);  //add to datalist, (at this point arrayadapter and ListView don't know bout it)**
                        cityAdapter.notifyDataSetChanged(); //notify adapter, which updates ListView

                        //After city is added, disable confirmButton:
                        confirm.setEnabled(false);
                    }
                });
            }
        });
    }
}