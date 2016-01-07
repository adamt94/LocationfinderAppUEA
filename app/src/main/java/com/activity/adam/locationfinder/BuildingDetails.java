package com.activity.adam.locationfinder;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class BuildingDetails extends AppCompatActivity {
    TextView description,name,abbr,type,opentimes;
    FloatingActionButton mapview;
    int position;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ArrayList<MapData> md;
      //  if(CustomAdapter.getFilteredData()==null) {
            md= MainActivity.db.getData();
     //   }else{


     //   }
        super.onCreate(savedInstanceState);
        Intent in = getIntent();
        //position of listview clicked
        position = in.getIntExtra("position",0);//gets name from intent

        setContentView(R.layout.activity_building_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_action_back);
        setSupportActionBar(toolbar);
        description = (TextView) findViewById(R.id.description);
        name = (TextView) findViewById(R.id.Name);
        abbr = (TextView) findViewById(R.id.Abbrivation);
        type = (TextView) findViewById(R.id.Type);
        opentimes = (TextView) findViewById(R.id.OpeningTimes);
        mapview = (FloatingActionButton) findViewById(R.id.fab);
        //set the title
        if(md.get(position).getAbbr().contains("null"))
        {

            setTitle( md.get(position).getName());

        }
        else {
            setTitle(md.get(position).getAbbr());
        }
        description.setText(Html.fromHtml(md.get(position).getDescription()));
        //makes hyperlinks clickable
        description.setMovementMethod(LinkMovementMethod.getInstance());

        abbr.setText(Html.fromHtml("<b>" + "Abbreviation" + "</b>" + "<br/>" + md.get(position).getAbbr()));
        name.setText(Html.fromHtml("<b>" + "Name" + "</b>" + "<br/>" + md.get(position).getName()));
        type.setText(Html.fromHtml("<b>" + "Type" + "</b>" + "<br/>" + md.get(position).getType()));
        opentimes.setText(Html.fromHtml("<b> "+"Opening Times"+"</b>"+"<br/>"+md.get(position).getOpeningTimes()));

        mapview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BuildingDetails.this, MapsActivity.class);
                intent.putExtra("position", position);
                startActivity(intent);

            }
        });





    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if(id == android.R.id.home)
        {
            //returns to home
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
