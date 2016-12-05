package com.example.android.scanmove.activities;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.scanmove.R;
import com.example.android.scanmove.appmodel.Event;
import com.example.android.scanmove.utilities.EventBigListAdapter;
import com.example.android.scanmove.utilities.ItemClickSupport;
import com.firebase.client.Firebase;
import com.firebase.client.Query;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.Iterator;

import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class ExploreFragment extends Fragment {

    // DONE : Refactor code
    // DONE : add ProgressDialog for Loading data

    private Firebase ref;
    private Query qRef;
    private EventBigListAdapter adapter;
    private String LOG_TAG = ExploreFragment.class.getSimpleName();
    FragmentActivity listener;
    View rootView;


    public ExploreFragment() {
        // Required empty public constructor
    }

    /**
     * Called when a fragment is first attached to its context.
     * {@link #onCreate(Bundle)} will be called after this.
     *
     * @param context context
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Activity) {
            this.listener = (FragmentActivity) context;
        }
    }

    /**
     * This event fires 2nd, before views are created for the fragment
     * The onCreate method is called when the Fragment instance is being created, or re-created.
     * Use onCreate for any standard setup that does not require the activity to be fully created
     **/
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // DONE : Instantiate variable here


    }

    /**
     * This method is called when the fragment is no longer connected to the Activity
     * Any references saved in onAttach should be nulled out here to prevent memory leaks.
     **/
    @Override
    public void onDetach() {
        super.onDetach();
        this.listener = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_explore, container, false);
        // TESTING : fake some data below for testing adapter
        // ArrayList<Event> fakeEvents = new Event().createFakeEventList(20);

        //setUpRecycleView(fakeEvents, rootView);

        return rootView;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapter = new EventBigListAdapter(getActivity(), MainActivity.allEvents);
        setUpAdapter();

    }


    private void setUpAdapter(){

        // Lookup the RecyclerView in activity layout
        RecyclerView rvEvents = (RecyclerView) rootView.findViewById(R.id.rvBigEvents);

        // Attach the adapter to the RecyclerView to populate items
        rvEvents.setAdapter(new ScaleInAnimationAdapter(adapter));

        // Set layout manager to position the items
        rvEvents.setLayoutManager(new LinearLayoutManager(getActivity()));

        ItemClickSupport.addTo(rvEvents).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {

                // DONE : change "fakeEvents" to "allEvents" when test success
                Event event = MainActivity.allEvents.get(position);

                Context context = v.getContext();
                Intent intent = new Intent(context, InformationActivity.class);
                intent.putExtra("select_event", Parcels.wrap(event));
                context.startActivity(intent);

            }
        });

    }

    private ArrayList<Event>  cleanEventList(ArrayList<Event> list){
        for(Iterator<Event> i = list.iterator(); i.hasNext(); ) {
            Event item = i.next();
            if (item.getName() == null) {
                i.remove();
            }
        }
        return list;
    }

}
