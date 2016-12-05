package com.example.android.scanmove.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.balysv.materialripple.MaterialRippleLayout;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.android.scanmove.R;
import com.example.android.scanmove.appmodel.Event;
import com.example.android.scanmove.utilities.EventListAdapter;
import com.example.android.scanmove.utilities.GoogleMapUtility;
import com.example.android.scanmove.utilities.ItemClickSupport;
import com.example.android.scanmove.utilities.QueryUtility;
import com.example.android.scanmove.utilities.ReadWriteXMLUtility;
import com.example.android.scanmove.utilities.UrlsConfig;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;

import org.parceler.Parcels;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

public class LandmarkActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private final static String LOG_TAG = LandmarkActivity.class.getSimpleName();

    ProgressDialog dialog;

    private String targetName;
    private Firebase ref;
    private Query qRef;

    private ImageButton loveButton;

    private RetrieveDrawableImage retrieveTask;

    protected GoogleApiClient googleApiClient;

    protected Location mLastLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        buildGoogleApiClient();

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

                    setUpToolBar();

                    // Lookup all no event state component and then kick it!!
                    LinearLayout noEventView = (LinearLayout) findViewById(R.id.no_event_view);
                    noEventView.setVisibility(View.GONE); // some magic fragment here!! 9_9

                    // set all data into RecycleView
                    setUpRecycleView(mEvents);

                }
                else {

                    // set setContentView here for good UX and UI
                    setContentView(R.layout.activity_landmark);

                    setUpToolBar();

                    // Lookup the RecyclerView in activity layout and get it out!!
                    RecyclerView rvEvents = (RecyclerView) findViewById(R.id.rvEvents);
                    rvEvents.setVisibility(View.GONE);

                    // set some essential use data
                    setUpAlterRecycleView(mEvents);


                }

                //set up love button
                setLoveButton(mEvents.get(0).getmSpotEngName());

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

    private void setUpAlterRecycleView(final ArrayList<Event> eventList){

        TextView placeName = (TextView) findViewById(R.id.place_eng_name_view);
        placeName.setText(eventList.get(0).getmSpotEngName());

        TextView placeThName = (TextView) findViewById(R.id.place_th_name_view);
        placeThName.setText(eventList.get(0).getmSpotThaiName());

        MaterialRippleLayout navLabel = (MaterialRippleLayout) findViewById(R.id.ripple_navigation_label_alter);
        // add some animation
        YoYo.with(Techniques.BounceInUp)
                .duration(1500)
                .playOn(findViewById(R.id.ripple_navigation_label_alter));

        // add on click action
        navLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // FIXME : use mock location -3-
                Intent coordinate = new Intent(LandmarkActivity.this, NavigationActivity.class);
                coordinate.putExtra("MyLat", GoogleMapUtility.fakeLatitude);
                coordinate.putExtra("MyLng", GoogleMapUtility.fakeLongtitide);

                coordinate.putExtra("DestinationLat", eventList.get(0).getCoordinates().get(0)); // from firebase
                coordinate.putExtra("DestinationLng", eventList.get(0).getCoordinates().get(1)); // from firebase
                startActivity(coordinate);

            }
        });


    }

    private void setLoveButton(final String placeEngName){

        // love button here
        loveButton = (ImageButton) findViewById(R.id.place_love_btn);

        if(isLoved(placeEngName)){
            loveButton.setImageResource(R.drawable.ic_favorite_black_24dp);
        } else {
            loveButton.setImageResource(R.drawable.ic_favorite_border_black_24dp);
        }

        loveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // DONE : add some behavior to this button!!
                if(isLoved(placeEngName)){
                    // DONE : add delete favorite action here!!
                    ReadWriteXMLUtility.deleteFavorite(LandmarkActivity.this, placeEngName);
                    loveButton.setImageResource(R.drawable.ic_favorite_border_black_24dp);
                }
                else {
                    ReadWriteXMLUtility.addFavoritePlace(LandmarkActivity.this, placeEngName);
                    loveButton.setImageResource(R.drawable.ic_favorite_black_24dp);
                }

                YoYo.with(Techniques.ZoomIn)
                        .duration(320)
                        .playOn(findViewById(loveButton.getId()));

            }
        });

    }

    private boolean isLoved(String placeEngName){

        ArrayList<String> loveList = ReadWriteXMLUtility.getAllFavoritePlace(this);

        return loveList.contains(placeEngName);

    }

    private void setUpToolBar(){

        final Toolbar toolbar = (Toolbar) findViewById(R.id.landmark_toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Landmark");

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // when click black left arrow button
                finish();

            }
        });
    }

    protected synchronized void buildGoogleApiClient() {
        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addConnectionCallbacks(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    protected void onStart() {
        super.onStart();
        googleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (googleApiClient.isConnected()) {
            googleApiClient.disconnect();
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
        } else {
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
        }

        if(mLastLocation != null){

            Toast.makeText(this, "lastLocation get!! : " + mLastLocation.getLatitude(), Toast.LENGTH_SHORT).show();

        }

    }

    @Override
    public void onConnectionSuspended(int i) {

        Log.i(LOG_TAG, "Suspended for " + i + "second");
        googleApiClient.connect();

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {

    }


}

