package com.activity.adam.locationfinder;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsoluteLayout;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Locale;

import framework.implementation.AndroidApp;
import framework.implementation.Database;
import framework.implementation.MapData;

public class MainActivity extends AppCompatActivity implements Serializable {
    ListView locationList; // list of locations
    public static AndroidApp app;
    public static Database db; //database to get the mapdata
    CustomAdapter adapter; //custom listview
    ArrayList<MapData> data; //the data of all the locations
    EditText search; //search input
    AdapterView.AdapterContextMenuInfo info;
    private RelativeLayout layout; //used for adding search input

    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        layout = (RelativeLayout) findViewById(R.id.searchlayout);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        app = new AndroidApp(this);
        //enable gps
        app.getGPS().enableLocation();
        //setup database
        data = app.getDatabase().getData();

        //pass the assest file to database and get the location Data

        data = app.getDatabase().getData();

        //setup the listview and adapter
        locationList = (ListView) findViewById(R.id.locations);
        adapter = new CustomAdapter(data, this);
        locationList.setAdapter(adapter);

        //create on click for list view
        locationList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(MainActivity.this, BuildingDetails.class);
                if (CustomAdapter.getFilteredData() != null) {
                    MapData d = CustomAdapter.getFilteredData().get(position);
                    //check if the list was filtered to pass the right position in data
                    for (int i = 0; i < data.size(); i++) {
                        if (d.getName().equals(data.get(i).getName())) {
                            position = i;
                        }
                    }
                }
                //pass position to next class to get the right MapData
                intent.putExtra("position", position);
                startActivity(intent);


            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder inputAlert = new AlertDialog.Builder(context);
                inputAlert.setTitle("Add your location to the list");
                final EditText locationName = new EditText(context);
                locationName.setHint("Location Name");
                inputAlert.setView(locationName);
                inputAlert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String userLocationName = locationName.getText().toString();
                        MapData md = new MapData("Custom Location", userLocationName, app.getGPS().getLocation().getLongitude(), app.getGPS().getLocation().getLatitude(), "loc", "null", "Custom Location - No Description", "null", "null", "null");
                        data.add(0, md);
                        adapter.notifyDataSetChanged();
                        Toast.makeText(MainActivity.this, "Your Location has been saved",
                                Toast.LENGTH_LONG).show();
                    }
                });
                inputAlert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog alertDialog = inputAlert.create();
                alertDialog.show();

            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    //tool bar at the top creates the on click for each button
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            final AlertDialog.Builder inputAlert = new AlertDialog.Builder(context);
            inputAlert.setTitle("Settings");


            inputAlert.setPositiveButton("Delete Saved Locations", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                   for(int i =0; i< data.size(); i++)
                   {
                       if(data.get(i).getType().equals("Custom Location"))
                       {
                           data.remove(i);
                           i--;
                       }
                   }
                    adapter.notifyDataSetChanged();
                }
            });
            inputAlert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            AlertDialog alertDialog = inputAlert.create();
            alertDialog.show();
        }










        if (id == R.id.search) {
            //adds a textview dynamically
            RelativeLayout.LayoutParams lparams = new RelativeLayout.LayoutParams(
                    LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            if (search == null) {
                search = new EditText(this);

                search.setLayoutParams(lparams);
                search.setId(R.id.seachbar);
                search.setSingleLine();
                search.setHint("Search");
                this.layout.addView(search);
            }

            //changes the listview position as search bar is added
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) locationList.getLayoutParams();
            params.addRule(RelativeLayout.BELOW, R.id.seachbar);
            locationList.setLayoutParams(params);

            //set focus on search bar and open keyboard
            search.requestFocus();
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(search, InputMethodManager.SHOW_IMPLICIT);
            // Capture Text in EditText
            //Makes the list searchable
            search.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    System.out.println("Text [" + s + "]");
                    adapter.getFilter().filter(s.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });


        }

        return super.onOptionsItemSelected(item);
    }


}
