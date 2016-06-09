package com.activity.adam.locationfinder;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
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

import framework.implementation.MapData;

public class BuildingDetails extends AppCompatActivity {
    TextView description,name,abbr,type,opentimes,Websitelink;
    FloatingActionButton mapview;
    int position;
    ArrayList<MapData> md;
    CollapsingToolbarLayout ctbl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //get the map data
        md= MainActivity.app.getDatabase().getData();

        super.onCreate(savedInstanceState);
        Intent in = getIntent();
        //get the position to get right item in mapdata array
        position = in.getIntExtra("position",0);//gets name from intent

        //set up all the different textview and buttons etc
        setContentView(R.layout.activity_building_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_action_back);
        setSupportActionBar(toolbar);
        description = (TextView) findViewById(R.id.description);
        name = (TextView) findViewById(R.id.Name);
        abbr = (TextView) findViewById(R.id.Abbrivation);
        type = (TextView) findViewById(R.id.Type);
        opentimes = (TextView) findViewById(R.id.OpeningTimes);
        ctbl = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        mapview = (FloatingActionButton) findViewById(R.id.fab);
        Websitelink = (TextView) findViewById(R.id.websiteLink);
        //set the title
        setTitle(md.get(position).getName());
        if(MainActivity.app.getDatabase().getData().get(position).getAbbr().equals("BIO"))
        {

            ctbl.setBackgroundResource(R.drawable.bio);
        }
        if(MainActivity.app.getDatabase().getData().get(position).getAbbr().equals("HIS"))
        {
            ctbl.setBackgroundResource(R.drawable.his);
        }
        if(MainActivity.app.getDatabase().getData().get(position).getAbbr().equals("NBS"))
        {
            ctbl.setBackgroundResource(R.drawable.nbs);
        }
        if(MainActivity.app.getDatabase().getData().get(position).getAbbr().equals("PHA"))
        {
            ctbl.setBackgroundResource(R.drawable.pha);
        }
        if(MainActivity.app.getDatabase().getData().get(position).getAbbr().equals("MED"))
        {
            ctbl.setBackgroundResource(R.drawable.med);
        }


        if (!md.get(position).getDescription().contains("null")){
            //set all the text and formatting
            description.setText(Html.fromHtml(md.get(position).getDescription()));
            //makes hyperlinks clickable
            description.setMovementMethod(LinkMovementMethod.getInstance());
        }else{
            description.setText("This location does not have a description");
        }

        if (!md.get(position).getAbbr().contains("null")){
            abbr.setText(Html.fromHtml("<b>" + "Abbreviation" + "</b>" + "<br/>" + md.get(position).getAbbr()));
        }else{
            abbr.setVisibility(View.GONE);
        }
        if (!md.get(position).getName().contains("null")){
            name.setText(Html.fromHtml("<b>" + "Name" + "</b>" + "<br/>" + md.get(position).getName()));
        }else{
            name.setVisibility(View.GONE);
        }
        if (!md.get(position).getOpeningTimes().contains("null")){
            opentimes.setText(Html.fromHtml("<b> "+"Opening Times"+"</b>"+"<br/>"+md.get(position).getOpeningTimes()));
        }else{
            opentimes.setVisibility(View.GONE);
        }

        type.setText(Html.fromHtml("<b>" + "Type" + "</b>" + "<br/>" + md.get(position).getType()));
        if(!md.get(position).getLink().contains("null")) {
            Websitelink.setText(Html.fromHtml("<b>" + "Website Link" + "</b>" + "<br/>" +"<a href=\""+ md.get(position).getLink() + "\">" + md.get(position).getLink() + "</a>"));
            Websitelink.setMovementMethod(LinkMovementMethod.getInstance());
        }
        //user clicks the mapview button
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
