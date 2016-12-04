package com.example.android.scanmove.activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.akexorcist.googledirection.DirectionCallback;
import com.akexorcist.googledirection.GoogleDirection;
import com.akexorcist.googledirection.constant.TransportMode;
import com.akexorcist.googledirection.model.Direction;
import com.akexorcist.googledirection.model.Route;
import com.akexorcist.googledirection.util.DirectionConverter;
import com.example.android.scanmove.R;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.MapFragment;

import java.util.ArrayList;

public class NavigationActivity extends AppCompatActivity implements OnMapReadyCallback, DirectionCallback {

    private GoogleMap googleMap;
    private LocationManager locateMgr;
    //private String serverKey = "AIzaSyDRoZ4vlNh8MJrD3zvvW8IrcB-BbG2zyWQ";
    private String serverKey = "AIzaSyC-m87Ab1Qf7ZGObVvZKEsEXk6Jk_ARUeY";
    private LatLng camera;
    private LatLng origin;
    private LatLng destination;
    private String[] colors = {"#7fff7272", "#7f31c7c5", "#7fff8a00"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        // DONE : getIntent to get location and navigation user to that

        setUpToolBar();

        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.nav_map);
        mapFragment.getMapAsync(this);

        // Set Point
        Bundle bundle = getIntent().getExtras();
        double myLat = bundle.getDouble("MyLat");
        double myLng = bundle.getDouble("MyLng");

        double desLat = bundle.getDouble("DestinationLat");
        double desLng = bundle.getDouble("DestinationLng");

        origin = new LatLng(myLat, myLng);
        camera = new LatLng(myLat, myLng);
        destination = new LatLng(desLat, desLng);



    }

    private void setUpToolBar(){

        final Toolbar toolbar = (Toolbar) findViewById(R.id.navigation_toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Navigation");

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // when click black left arrow button
                finish();

            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();

        requestDirection();

    }

    @Override
    public void onDirectionSuccess(Direction direction, String rawBody) {
        Toast.makeText(this, "Direction Success", Toast.LENGTH_SHORT).show();
        if (direction.isOK()) {
            googleMap.addMarker(new MarkerOptions().position(origin));
            googleMap.addMarker(new MarkerOptions().position(destination));

            for (int i = 0; i < direction.getRouteList().size(); i++) {
                Route route = direction.getRouteList().get(i);
                String color = colors[i % colors.length];
                ArrayList<LatLng> directionPositionList = route.getLegList().get(0).getDirectionPoint();
                googleMap.addPolyline(DirectionConverter.createPolyline(this, directionPositionList, 5, Color.parseColor(color)));
            }

        }

    }

    @Override
    public void onDirectionFailure(Throwable t) {
        Toast.makeText(this, "Direction Failure!!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        this.googleMap = googleMap;
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            this.googleMap.setMyLocationEnabled(true);

        }
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(camera, 15));

    }

    LocationListener listener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            origin = new LatLng(location.getLatitude(), location.getLongitude());
            camera = new LatLng(location.getLatitude(), location.getLongitude());
        }
    };

    public void requestDirection() {
        Toast.makeText(this, "Direction Requesting...", Toast.LENGTH_SHORT).show();
        GoogleDirection.withServerKey(serverKey)
                .from(origin)
                .to(destination)
                .transportMode(TransportMode.WALKING)
                .alternativeRoute(true)
                .execute(this);
    }


}
