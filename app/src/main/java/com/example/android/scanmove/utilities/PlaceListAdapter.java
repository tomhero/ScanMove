package com.example.android.scanmove.utilities;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.android.scanmove.R;
import com.example.android.scanmove.appmodel.Landmark;

import java.util.List;

/**
 * Created by 'Chayut' on 5/12/2559.
 *
 */

public class PlaceListAdapter extends ArrayAdapter<Landmark>{

    private List<Landmark> landmarks;

    public PlaceListAdapter(Context context, List<Landmark> landmarkArrayList) {
        super(context, 0, landmarkArrayList);
        this.landmarks = landmarkArrayList;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Landmark landmark = landmarks.get(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_place_list, parent, false);
        }
        // Lookup view for data population
        TextView tvNameEng = (TextView) convertView.findViewById(R.id.list_place_name_eng);
        TextView tvNameTh = (TextView) convertView.findViewById(R.id.list_place_name_th);
        TextView tvImage = (TextView) convertView.findViewById(R.id.list_place_image);
        // Populate the data into the template view using the data object
        tvNameEng.setText(landmark.getmSpotEngName());
        tvNameTh.setText(landmark.getmSpotThaiName());
        tvImage.setText(landmark.getmSpotEngName().substring(0, 1));
        // Return the completed view to render on screen
        return convertView;
    }


}
