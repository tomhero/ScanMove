package com.example.android.scanmove.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.android.scanmove.R;
import com.example.android.scanmove.appmodel.Event;

import org.parceler.Parcels;

public class InformationActivity extends AppCompatActivity {

    // User user = (User) Parcels.unwrap(getIntent().getParcelableExtra("user"));

    private TextView fullInfo, fullLocation, fullSite, fullDate;

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

        //toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.earth_globe));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // when click black left arrow button
                finish();

            }
        });

        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(mEvent.getName());

        loadBackdrop();

        setUpAllInfo(mEvent);

    }

    private void loadBackdrop() {
        final ImageView imageView = (ImageView) findViewById(R.id.backdrop);
        Glide.with(this).load(R.drawable.event_cover).centerCrop().into(imageView);

    }

    private void setUpAllInfo(Event event){

        fullInfo = (TextView) findViewById(R.id.full_event_info);
        fullLocation = (TextView) findViewById(R.id.full_event_location);
        fullDate = (TextView) findViewById(R.id.full_event_date);
        fullSite = (TextView) findViewById(R.id.full_event_site);

        fullInfo.setText(event.getContent());
        fullLocation.setText(event.getmSpotEngName());
        fullDate.setText(event.getBegin() + " - " + event.getEnd());
        fullSite.setText(event.getUrl());


    }
}
