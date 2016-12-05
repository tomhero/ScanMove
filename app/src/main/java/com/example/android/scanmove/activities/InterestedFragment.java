package com.example.android.scanmove.activities;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.example.android.scanmove.R;
import com.example.android.scanmove.appmodel.Event;
import com.example.android.scanmove.utilities.EventListViewAdapter;
import com.example.android.scanmove.utilities.ReadWriteXMLUtility;
import com.firebase.client.Firebase;
import com.firebase.client.Query;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 */
public class InterestedFragment extends Fragment implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {

    private SliderLayout mDemoSlider;
    private String LOG_TAG = InterestedFragment.class.getSimpleName();

    @SuppressLint("StaticFieldLeak")
    private static View rootView;

    private Firebase ref;
    private Query qRef;


    public InterestedFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_interested, container, false);

        if (Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        mDemoSlider = (SliderLayout) rootView.findViewById(R.id.event_image_slider);

        HashMap<String, String> url_maps = new HashMap<>();
        url_maps.put("KMITL'S TIE DAY CEREMONY 2016", "https://firebasestorage.googleapis.com/v0/b/scanmove-app.appspot.com/o/Stadium.jpg?alt=media&token=0aa90db0-7992-4490-95a9-1023bf7c85e0");
        url_maps.put("ละคร “อาคา” คำทาย-ทำนาย-สุริยะ ", "https://firebasestorage.googleapis.com/v0/b/scanmove-app.appspot.com/o/maxresdefault.jpg?alt=media&token=7cd30de7-8b50-4a03-8800-ce7bc17f148e");
        url_maps.put("IT OPENHOUSE ", "https://firebasestorage.googleapis.com/v0/b/scanmove-app.appspot.com/o/CqOqxbWUsAAqkPA.jpg?alt=media&token=02107930-a995-40c7-8540-2c73ba6484aa");
        url_maps.put("พิธีพระราชทานปริญญาบัตร (ถ่ายภาพหมู่)", "https://firebasestorage.googleapis.com/v0/b/scanmove-app.appspot.com/o/Untitled-3.jpg?alt=media&token=2ac74e84-23f6-43d7-be80-774279b64f39");
        url_maps.put("I-Bot camp 9", "https://firebasestorage.googleapis.com/v0/b/scanmove-app.appspot.com/o/head-camphub2m-810x425.jpg?alt=media&token=d1ebb792-3e40-477c-83cd-15c82d881582");

        for (String name : url_maps.keySet()) {
            TextSliderView textSliderView = new TextSliderView(getActivity());
            // initialize a SliderLayout
            textSliderView
                    .description(name)
                    .image(url_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.CenterCrop)
                    .setOnSliderClickListener(this);

            //add your extra information
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra", name);

            mDemoSlider.addSlider(textSliderView);
        }

        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Default);
        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Right_Bottom);
        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
        mDemoSlider.setDuration(3200);
        mDemoSlider.addOnPageChangeListener(this);


        return rootView;
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {

        Log.i(LOG_TAG, "Click on : " + slider.getDescription());

        // DONE : add intent to InformationActivity
        Event selectEvent = makeEvent(slider.getDescription());

        if (selectEvent != null) {

            Intent intent = new Intent(getActivity(), InformationActivity.class);
            intent.putExtra("select_event", Parcels.wrap(selectEvent));
            startActivity(intent);

        }
    }

    /**
     * This method will be invoked when the current page is scrolled, either as part
     * of a programmatically initiated smooth scroll or a user initiated touch scroll.
     *
     * @param position             Position index of the first page currently being displayed.
     *                             Page position+1 will be visible if positionOffset is nonzero.
     * @param positionOffset       Value from [0, 1) indicating the offset from the page at position.
     * @param positionOffsetPixels Value in pixels indicating the offset from position.
     */
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    /**
     * This method will be invoked when a new page becomes selected. Animation is not
     * necessarily complete.
     *
     * @param position Position index of the new selected page.
     */
    @Override
    public void onPageSelected(int position) {

    }

    /**
     * Called when the scroll state changes. Useful for discovering when the user
     * begins dragging, when the pager is automatically settling to the current page,
     * or when it is fully stopped/idle.
     *
     * @param state The new scroll state.
     * @see ViewPagerEx#SCROLL_STATE_IDLE
     * @see ViewPagerEx#SCROLL_STATE_DRAGGING
     * @see ViewPagerEx#SCROLL_STATE_SETTLING
     */
    @Override
    public void onPageScrollStateChanged(int state) {

    }


    @Override
    public void onResume() {
        super.onResume();

        //List<String> interestEvent = ReadWriteXMLUtility.getAllInterestEvent(getActivity());
        //Log.i(LOG_TAG, interestEvent.toString());

        if (MainActivity.allEvents != null) {
            setUpInterestList(getActivity());
        }

    }

    @Override
    public void onStop() {
        mDemoSlider.stopAutoCycle();
        super.onStop();
    }

    private Event makeEvent(String name) {

        for (Event ev : MainActivity.allEvents) {

            //Log.i(LOG_TAG, ev.getName() + " == " + name);
            if (ev.getName().equals(name)) {
                return ev;
            }

        }

        return null;

    }

    public static void setUpInterestList(final Context context) {

        // DONE : setup Interest Event UI here
        // Construct the data source
        final ArrayList<Event> arrayOfEvents = Event.searchInterestEvent(
                ReadWriteXMLUtility.getAllInterestEvent(context), MainActivity.allEvents);
        // Create the adapter to convert the array to views
        EventListViewAdapter adapter = new EventListViewAdapter(context, arrayOfEvents);
        // Attach the adapter to a ListView
        ListView listView = (ListView) rootView.findViewById(R.id.interest_list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Event event = arrayOfEvents.get(i);

                Intent intent = new Intent(context, InformationActivity.class);
                intent.putExtra("select_event", Parcels.wrap(event));
                context.startActivity(intent);

            }
        });

    }


}
