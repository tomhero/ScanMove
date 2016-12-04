package com.example.android.scanmove.activities;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.scanmove.R;
import com.example.android.scanmove.appmodel.Event;
import com.example.android.scanmove.utilities.EventBigListAdapter;
import com.example.android.scanmove.utilities.ItemClickSupport;
import com.example.android.scanmove.utilities.QueryUtility;
import com.example.android.scanmove.utilities.UrlsConfig;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

import org.parceler.Parcels;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ExploreFragment extends Fragment {

    // DONE : Refactor code
    // DONE : add ProgressDialog for Loading data

    private Firebase ref;
    private Query qRef;
    private ArrayList<Event> allEvents, fakeEvents;
    private EventBigListAdapter adapter;
    private String LOG_TAG = ExploreFragment.class.getSimpleName();
    FragmentActivity listener;
    View rootView;
    ProgressDialog dialog;


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

        //fakeEvents = new Event().createFakeEventList(20);

        // DONE : Instantiate variable here
        // Initialize allEvents
        allEvents = new ArrayList<Event>();


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

        // DONE : Query process below
        dialog = ProgressDialog.show(getActivity(), "",
                "Loading. Please wait...", true);
        queryFromFireBase();

    }

    private void queryFromFireBase() {

       final ArrayList<Event> allEventTmp = new ArrayList<>();

        /** Query process goes here **/
        if (Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        Firebase.setAndroidContext(getActivity());
        ref = new Firebase(UrlsConfig.FIREBASE_URL);

        qRef = ref.orderByChild("properties");

        // DONE : Query data from Fire base below
        // TODO : remove counter when update data in FireBase database
        qRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.getValue() != null) {

                    //int counter = 0;

                    for (DataSnapshot msg : dataSnapshot.getChildren()) {

//                        if (counter > 12) {
//                            break;
//                        }

                        // Retrieve all data from FireBase using QueryUtility
                        ArrayList<Event> mEvents = QueryUtility.extractEvents(msg);

                        // DONE : add mEvents to allEvents list by using addAll(Collection<? extends E> c)
                        allEventTmp.addAll(mEvents);

                        //counter++;

                        Log.i(LOG_TAG, "allEvents Size : " + allEventTmp.size());

                    }

                    allEvents = allEventTmp;

                    adapter = new EventBigListAdapter(getActivity(), allEvents);

                    setUpAdapter();

                    dialog.dismiss();

                }


            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

    }

    private void setUpAdapter(){

        // Lookup the RecyclerView in activity layout
        RecyclerView rvEvents = (RecyclerView) rootView.findViewById(R.id.rvBigEvents);

        // Attach the adapter to the RecyclerView to populate items
        rvEvents.setAdapter(adapter);

        // Set layout manager to position the items
        rvEvents.setLayoutManager(new LinearLayoutManager(getActivity()));

        ItemClickSupport.addTo(rvEvents).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {

                // DONE : change "fakeEvents" to "allEvents" when test success
                Event event = allEvents.get(position);

                Context context = v.getContext();
                Intent intent = new Intent(context, InformationActivity.class);
                intent.putExtra("select_event", Parcels.wrap(event));
                context.startActivity(intent);

            }
        });

    }

}
