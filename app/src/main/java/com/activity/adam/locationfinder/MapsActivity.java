package com.activity.adam.locationfinder;

import android.content.Intent;
import android.media.Image;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.TileOverlay;
import com.google.android.gms.maps.model.TileOverlayOptions;
import com.google.android.gms.maps.model.TileProvider;
import com.google.android.gms.maps.model.UrlTileProvider;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import framework.implementation.AndroidGPS;
import framework.implementation.CampusMap;
import framework.implementation.MapData;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ArrayList<MapData> data;
    int position;
    ImageButton back;
    CheckBox showall;
    CampusMap campusMap;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        data = MainActivity.app.getDatabase().getData();
        campusMap = new CampusMap();

        Intent in = getIntent();
        position = in.getIntExtra("position", 0);//gets name from intent
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        back = (ImageButton) findViewById(R.id.back);
        showall = (CheckBox) findViewById(R.id.showall);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.animateCamera(CameraUpdateFactory.zoomTo(1.0f));

        // Add a marker in Sydney and move the camera
        final LatLng building = new LatLng(data.get(position).getLatitude(), data.get(position).getLongitude());
        //get own location
        mMap.setMyLocationEnabled(true);
        final CameraPosition cameraPosition = campusMap.defaultCameraPosition(building);
          mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        TileProvider tileProvider = campusMap.getCampusMap();

        //overlays the uea campus map
        TileOverlay tileOverlay = mMap.addTileOverlay(new TileOverlayOptions()
                .tileProvider(tileProvider));

        mMap.addMarker(new MarkerOptions().position(building).title(data.get(position).getName()));
        showall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((CheckBox) v).isChecked()) {
                    for (int i = 0; i < data.size(); i++) {
                        LatLng lt = new LatLng(data.get(i).getLatitude(), data.get(i).getLongitude());

                        mMap.addMarker(new MarkerOptions().position(lt).title(data.get(i).getName()));

                    }
                }else{
                    mMap.clear();

                    onMapReady(mMap);
                }
            }
        });



    }

}
