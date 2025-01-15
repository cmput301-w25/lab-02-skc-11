package com.example.listycity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

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
        Button button = findViewById(R.id.button); //need to 1st to interact with UI elements

        String[] cities = {"Edmonton","Paris","London","Vancouver"};    //instantiate into string array
        dataList = new ArrayList<>();
        dataList.addAll(Arrays.asList(cities)); //convert primitive arr to Array, and send to addAll (addAll accepts collections, not primitive list)
        cityAdapter=new ArrayAdapter<>(this, R.layout.content, dataList);  // 2nd: what is looks like, 3rd arg:container that holds content
        cityList.setAdapter(cityAdapter); //tell listview who its adapter is

        cityList.setOnItemClickListener(new AdapterView.OnItemClickListener() {  //when indiv elemen on listview is clicked
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //gets which item is clicked
                //define behaviour when indiv elemen on listview is clicked
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