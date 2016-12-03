package com.example.android.scanmove.utilities;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.android.scanmove.R;
import com.example.android.scanmove.appmodel.Event;

import java.util.List;

/**
 * Created by 'Chayut' on 7/11/2559.
 * TODO : test this adapter to show all event list
 */

public class EventBigListAdapter extends RecyclerView.Adapter<EventBigListAdapter.ViewHolder> {

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    static class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        TextView nameTextView, dateTextView;
        ImageView bigCardCover;


        //public ImageView infoIcon;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        ViewHolder(View itemView) {

            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            nameTextView = (TextView) itemView.findViewById(R.id.big_card_title);
            dateTextView = (TextView) itemView.findViewById(R.id.big_card_date);
            bigCardCover = (ImageView) itemView.findViewById(R.id.big_card_cover);

        }
    }

    // Store a member variable for the contacts
    List<Event> mEvents;
    // Store the context for easy access
    Context mContext;


    // Pass in the Event array into the constructor
    public EventBigListAdapter(Context context, List<Event> events) {
        mEvents = events;
        mContext = context;
    }

    // Easy access to the context object in the RecyclerView
    private Context getContext() {
        return mContext;
    }


    // Usually involves inflating a layout from XML and returning the holder
    @Override
    public EventBigListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View bigEventListView = inflater.inflate(R.layout.item_event_list_big, parent, false);

        // Return a new holder instance
        EventBigListAdapter.ViewHolder viewHolder = new EventBigListAdapter.ViewHolder(bigEventListView);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(EventBigListAdapter.ViewHolder viewHolder, int position) {
        // Note : use viewHolder -> to find id of view in place holder class

        // Get the data model based on position
        final Event event = mEvents.get(position);

        // Set item views based on your views and data model

        // customize here
        TextView textViewName = viewHolder.nameTextView;
        textViewName.setText(event.getName());

        TextView textViewDate = viewHolder.dateTextView;
        textViewDate.setText("From " + event.getBegin() + " To " + event.getEnd());

        final ImageView imageViewCover = viewHolder.bigCardCover;
        Glide.with(mContext).load(event.getCover()).centerCrop().into(imageViewCover);

    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return mEvents.size();
    }

}
