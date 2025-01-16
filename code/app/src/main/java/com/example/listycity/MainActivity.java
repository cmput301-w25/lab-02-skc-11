package com.example.listycity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

    ListView cityList;
    ArrayAdapter<String> cityAdapter;  //ArrayAdapter is a class that holds strings
    ArrayList<String> dataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); //**NEED; calls onCreate of parent class (AppCompatActivity)
        setContentView(R.layout.activity_main); //**NEED; sets display/UI

        EdgeToEdge.enable(this);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        cityList = findViewById(R.id.city_list);
        Button button = findViewById(R.id.addCityButton); //need this 1st to interact with UI elements
        Button deleteCity = findViewById(R.id.deleteCityButton);

        String[] cities = {"Edmonton","Paris","London","Vancouver"};    //instantiate into string array
        dataList = new ArrayList<>();
        dataList.addAll(Arrays.asList(cities)); //convert primitive arr to Array, and send to addAll (addAll accepts collections, not primitive list)
        cityAdapter=new ArrayAdapter<>(this, R.layout.content, dataList);  // 2nd: what is looks like, 3rd arg:container that holds content
        cityList.setAdapter(cityAdapter); //tell listview who its adapter is


        //FUNCTION - Delete city from List:
        cityList.setOnItemClickListener(new AdapterView.OnItemClickListener() {  //when indiv elemen on listview is clicked
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //HERE - Define behaviour when indiv elemen on listview is clicked:

                String selectedCity = (String) parent.getItemAtPosition(position); //get which item is clicked

                //Once Item is clicked, then listen for when deleteCityButton is clicked:
                deleteCity.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //When deleteCityButton is clicked, then delete from the ListView datasource:
                        dataList.remove(position);  //dataList is the data source for the ListView
                        cityAdapter.notifyDataSetChanged(); //notifies adapter which refreshes ListView

                        //short confirmation message:
                        Toast deleteConfirmed =Toast.makeText(MainActivity.this, selectedCity+" has been deleted.", Toast.LENGTH_SHORT);
                        deleteConfirmed.show();
                    }
                });
            }
        });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //define what they do (crash the app, display a string)
                dataList.add("Ottawa"); //add to datalist, arrayadapter and list dont know bout it
                cityAdapter.notifyDataSetChanged(); //notify adapter
            }
        });
    }
}