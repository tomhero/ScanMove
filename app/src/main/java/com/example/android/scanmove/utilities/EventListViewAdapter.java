package com.example.android.scanmove.utilities;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.android.scanmove.R;
import com.example.android.scanmove.appmodel.Event;

import java.util.ArrayList;

/**
 * Created by 'Chayut' on 6/12/2559.
 *
 */

public class EventListViewAdapter extends ArrayAdapter<Event>{

    private ArrayList<Event> events;

    public EventListViewAdapter(Context context, ArrayList<Event> events) {
        super(context, 0, events);
        this.events = events;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Event event = events.get(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_event_small_list, parent, false);
        }
        // Lookup view for data population
        TextView tvName = (TextView) convertView.findViewById(R.id.list_event_name);
        ImageView frameName = (ImageView) convertView.findViewById(R.id.frame_interest_event);
        // Populate the data into the template view using the data object
        tvName.setText(event.getName());
        Glide.with(getContext()).load(event.getCover()).centerCrop().into(frameName);
        // Return the completed view to render on screen

        return convertView;
    }




}
