package com.example.android.scanmove.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.android.scanmove.R;
import com.example.android.scanmove.appmodel.Event;
import com.example.android.scanmove.utilities.EventListAdapter;
import com.example.android.scanmove.utilities.ItemClickSupport;
import com.example.android.scanmove.utilities.QueryUtility;
import com.example.android.scanmove.utilities.UrlsConfig;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

import org.parceler.Parcels;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

public class LandmarkActivity extends AppCompatActivity {

    private final static String LOG_TAG = LandmarkActivity.class.getSimpleName();

    ProgressDialog dialog;

    private String targetName;
    private Firebase ref;
    private Query qRef;

    private RetrieveDrawableImage retrieveTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dialog = ProgressDialog.show(LandmarkActivity.this, "",
                "Loading. Please wait...", true);

        if (Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        targetName = getIntent().getStringExtra("TARGET");

        // DEBUGGING : Retrieve all data from database using FireBase here

        Firebase.setAndroidContext(this);
        ref = new Firebase(UrlsConfig.FIREBASE_URL);

        qRef = ref.orderByChild("properties/place").equalTo(targetName);

        qRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.getValue() != null) {

                    for (DataSnapshot msg : dataSnapshot.getChildren()) {

                        // DONE : move legacy code to new databaseUtility class from below

                        // Retrieve all data from FireBase using QueryUtility
                        ArrayList<Event> mEvents = QueryUtility.extractEvents(msg);

                        //Toast.makeText(LandmarkActivity.this, "Target : " + targetName, Toast.LENGTH_SHORT).show();

                        // get place image for these events
                        assert mEvents != null;
                        String placeImageUrl = mEvents.get(0).getImageUrl();

                        // set up UI on screen
                        try {
                            setUpScreen(mEvents, placeImageUrl);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                }

                else {

                    // if not found target, then finish activity
                    Toast.makeText(LandmarkActivity.this, "Target Not Found", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    finish(); // return to ScanQRFragment
                }

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }

            /**
             * this function will manage all data to set up on the screen
             * **/
            private void setUpScreen(ArrayList<Event> mEvents, String placeImageUrl) throws IOException {

                // load and set place cover image
                //ImageView placePicture = (ImageView) findViewById(R.id.place_image);
                //Glide.with(LandmarkActivity.this).load(placeImageUrl).into(placePicture);
                retrieveTask = new RetrieveDrawableImage(getBaseContext());
                retrieveTask.execute(placeImageUrl);

                // check for no event at this place
                if(mEvents.get(0).getEvid() != null){

                    // set setContentView here for good UX and UI
                    setContentView(R.layout.activity_landmark);

                    // Lookup all no event state component and then kick it!!
                    LinearLayout noEventView = (LinearLayout) findViewById(R.id.no_event_view);
                    noEventView.setVisibility(View.GONE);

                    // set all data into RecycleView
                    setUpRecycleView(mEvents);

                }
                else {

                    // set setContentView here for good UX and UI
                    setContentView(R.layout.activity_landmark);

                    // Lookup the RecyclerView in activity layout and get it out!!
                    RecyclerView rvEvents = (RecyclerView) findViewById(R.id.rvEvents);
                    rvEvents.setVisibility(View.GONE);

                    // set some essential use data
                    setUpAlterRecycleView(mEvents);


                }

            }

        });

        // That's all! Hoooooreeeeeiii!!!

    }


    //inner sub class of AsyncTask below
    class RetrieveDrawableImage extends AsyncTask<String, Void, Drawable> {

        //private ProgressDialog dialog;
        private Context context;

        RetrieveDrawableImage(Context context) {
            this.context = context;
        }

        @Override
        protected Drawable doInBackground(String... urls) {
            try {
                return Drawable.createFromStream((InputStream) new URL(urls[0]).getContent(), "src");
            } catch (IOException e) {
                Log.e(LOG_TAG, e.toString());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Drawable drawable) {
            super.onPostExecute(drawable);
            if (drawable != null) {
                // when download image finish
                dialog.dismiss();
                setPlaceImage(drawable);
            }
        }
    }


    private void setPlaceImage(Drawable image) {

        ImageView placePicture = (ImageView) findViewById(R.id.place_image);
        placePicture.setImageDrawable(image);

        // add some animation
        YoYo.with(Techniques.FadeInDown)
                .duration(750)
                .playOn(findViewById(placePicture.getId()));

    }

    private void setUpRecycleView(ArrayList<Event> eventList) {


        //final int FAKE_AMOUNT_SPAWN = getIntent().getStringExtra("TARGET").length();
        TextView placeName = (TextView) findViewById(R.id.place_eng_name_view);
        placeName.setText(eventList.get(0).getmSpotEngName());

        //final int FAKE_AMOUNT_SPAWN = getIntent().getStringExtra("TARGET").length();
        TextView placeThName = (TextView) findViewById(R.id.place_th_name_view);
        placeThName.setText(eventList.get(0).getmSpotThaiName());

        // Lookup the RecyclerView in activity layout
        RecyclerView rvEvents = (RecyclerView) findViewById(R.id.rvEvents);

        // Initialize events
        final ArrayList<Event> events = eventList;

        // Create adapter passing in the sample user data
        EventListAdapter adapter = new EventListAdapter(this, events);

        // Attach the adapter to the RecyclerView to populate items
        rvEvents.setAdapter(adapter);

        // Set layout manager to position the items
        rvEvents.setLayoutManager(new LinearLayoutManager(this));

        ItemClickSupport.addTo(rvEvents).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {

                Event event = events.get(position);

                Context context = v.getContext();
                Intent intent = new Intent(context, InformationActivity.class);
                intent.putExtra("select_event", Parcels.wrap(event));
                context.startActivity(intent);

            }
        });

    }

    private void setUpAlterRecycleView(ArrayList<Event> eventList){

        TextView placeName = (TextView) findViewById(R.id.place_eng_name_view);
        placeName.setText(eventList.get(0).getmSpotEngName());

        TextView placeThName = (TextView) findViewById(R.id.place_th_name_view);
        placeThName.setText(eventList.get(0).getmSpotThaiName());

        // add some animation
        YoYo.with(Techniques.BounceInUp)
                .duration(1500)
                .playOn(findViewById(R.id.ripple_navigation_label_alter));


    }


}

