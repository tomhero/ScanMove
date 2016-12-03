package com.example.android.scanmove.activities;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.scanmove.R;
import com.example.android.scanmove.utilities.GoogleMapUtility;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;


/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends Fragment {

    // DOING : Add GoogleMap as fragment

    MapView mMapView;

    public MapFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        // inflate and return the layout
        View v = inflater.inflate(R.layout.fragment_map, container, false);
        mMapView = (MapView) v.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);

        mMapView.onResume();// needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(new GoogleMapUtility(getActivity()));


        // Perform any camera updates here
        return v;

        //return inflater.inflate(R.layout.fragment_map, container, false);
    }

    /**
     * Called when the Fragment is visible to the user.  This is generally
     * tied to {@link MainActivity#onStart() Activity.onStart} of the containing
     * Activity's lifecycle.
     */
    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }


}
