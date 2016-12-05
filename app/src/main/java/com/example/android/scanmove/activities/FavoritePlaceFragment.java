package com.example.android.scanmove.activities;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.android.scanmove.R;
import com.example.android.scanmove.appmodel.Landmark;
import com.example.android.scanmove.utilities.PlaceListAdapter;
import com.example.android.scanmove.utilities.ReadWriteXMLUtility;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavoritePlaceFragment extends Fragment {

    List<String> engLoveList;

    View rootView;
    private String LOG_TAG = FavoritePlaceFragment.class.getSimpleName();


    public FavoritePlaceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_favorite_place, container, false);

        // DONE : do some thing below

        //ArrayList<String> placeNameList = ReadWriteXMLUtility.getAllFavoritePlace(getActivity());

        //Log.i(LOG_TAG, placeNameList.toString());

        // Construct the data source
        List<Landmark> arrayOfLandmark = setLandmarkData();

        // Create the adapter to convert the array to views
        PlaceListAdapter placeListAdapter = new PlaceListAdapter(getActivity(), arrayOfLandmark);

        // Attach the adapter to a ListView
        ListView listView = (ListView) rootView.findViewById(R.id.fav_list);
        listView.setAdapter(placeListAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                // DOING : Go to landmark activity with valid intent
                Intent intent = new Intent(getActivity(), LandmarkActivity.class);
                intent.putExtra("TARGET", engLoveList.get(i));
                startActivity(intent);

            }
        });

        return rootView;

    }

    private List<Landmark> setLandmarkData(){

        List<Landmark> arrayOfLandmark = new ArrayList<>();

        List<String> thaiList = Arrays.asList(getResources().getStringArray(R.array.th_place));
        List<String> engList = Arrays.asList(getResources().getStringArray(R.array.eng_place));

        engLoveList = ReadWriteXMLUtility.getAllFavoritePlace(getActivity());

        for (String loveName : engLoveList) {

            Landmark tmpLandmark = new Landmark();

            int thNameIndex = engList.indexOf(loveName);

            tmpLandmark.setmSpotEngName(loveName);
            tmpLandmark.setmSpotThaiName(thaiList.get(thNameIndex));

            arrayOfLandmark.add(tmpLandmark);

        }

        return arrayOfLandmark;

    }

}
