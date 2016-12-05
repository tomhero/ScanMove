package com.example.android.scanmove.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.balysv.materialripple.MaterialRippleLayout;
import com.bumptech.glide.Glide;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.android.scanmove.R;
import com.example.android.scanmove.appmodel.Event;
import com.example.android.scanmove.utilities.GoogleMapUtility;
import com.example.android.scanmove.utilities.ReadWriteXMLUtility;

import org.parceler.Parcels;

import java.util.ArrayList;

public class InformationActivity extends AppCompatActivity {

    // User user = (User) Parcels.unwrap(getIntent().getParcelableExtra("user"));

    private static final String EXTRA_NAME = "select_event";
    private String fake_location_name = "SOME_FAKE_LOCATION";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);

        Intent intent = getIntent();

        Event mEvent = Parcels.unwrap(intent.getParcelableExtra(EXTRA_NAME));

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Event Information");

        //toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.earth_globe)); //it can set icon
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // when click black left arrow button
                finish();

            }
        });


//        CollapsingToolbarLayout collapsingToolbar =
//                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
//        collapsingToolbar.setTitle("Event Information");

        loadBackdrop(mEvent);

        setUpAllInfo(mEvent);

    }

    private void loadBackdrop(Event event) {
        final ImageView imageView = (ImageView) findViewById(R.id.backdrop);
        Glide.with(this).load(event.getCover()).centerCrop().into(imageView);

    }

    @SuppressLint("SetTextI18n")
    private void setUpAllInfo(final Event event) {

        TextView fullName = (TextView) findViewById(R.id.full_event_name);
        TextView fullInfo = (TextView) findViewById(R.id.full_event_info);
        TextView fullLocation = (TextView) findViewById(R.id.full_event_location);
        TextView fullDate = (TextView) findViewById(R.id.full_event_date);
        TextView fullSite = (TextView) findViewById(R.id.full_event_site);
        final String siteUrl = event.getUrl();

        fullName.setText(event.getName());
        fullInfo.setText(event.getContent());
        fullLocation.setText(event.getmSpotEngName());
        fullDate.setText(event.getBegin() + " - " + event.getEnd());
        if (siteUrl == null) {
            fullSite.setText("No Official Website");
        }
        else{
            fullSite.setText(siteUrl);
        }


        RelativeLayout siteLink = (RelativeLayout) findViewById(R.id.site_link_layout);
        siteLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if (siteUrl != null) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(siteUrl));
                    startActivity(intent);
                }

            }
        });

        MaterialRippleLayout navLabel = (MaterialRippleLayout) findViewById(R.id.ripple_navigation_label);
        // DONE : add some animation to label
        YoYo.with(Techniques.Shake)
                .delay(2500)
                .duration(1000)
                .playOn(findViewById(navLabel.getId()));

        navLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // FIXME : use mock location -3-
                Intent coordinate = new Intent(InformationActivity.this, NavigationActivity.class);
                coordinate.putExtra("MyLat", GoogleMapUtility.fakeLatitude);
                coordinate.putExtra("MyLng", GoogleMapUtility.fakeLongtitide);

                coordinate.putExtra("DestinationLat", event.getCoordinates().get(0)); // from firebase
                coordinate.putExtra("DestinationLng", event.getCoordinates().get(1)); // from firebase
                startActivity(coordinate);

            }
        });

        setfavButton(event.getEvid());

    }

    private void setfavButton(final String eventId){

        // love button here
        final FloatingActionButton favButton = (FloatingActionButton) findViewById(R.id.interest_button);

        if(isfavor(eventId)){
            favButton.setImageResource(R.drawable.star);
        } else {
            favButton.setImageResource(R.drawable.ic_star_border);
        }

        favButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // DONE : add some behavior to this button!!
                if(isfavor(eventId)){
                    // DONE : add delete interested action here!!
                    ReadWriteXMLUtility.deleteInterested(InformationActivity.this, eventId);
                    favButton.setImageResource(R.drawable.ic_star_border);
                }
                else {
                    ReadWriteXMLUtility.addInterestEvent(InformationActivity.this, eventId);
                    favButton.setImageResource(R.drawable.star);
                }

                YoYo.with(Techniques.RotateIn)
                        .duration(320)
                        .playOn(findViewById(favButton.getId()));

            }
        });

    }

    private boolean isfavor(String eventName){

        ArrayList<String> favList = ReadWriteXMLUtility.getAllInterestEvent(this);

        return favList.contains(eventName);

    }

}
