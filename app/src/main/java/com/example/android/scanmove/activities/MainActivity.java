package com.example.android.scanmove.activities;

import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.android.scanmove.R;
import com.example.android.scanmove.appmodel.Event;
import com.example.android.scanmove.utilities.MyFragmentAdapter;
import com.example.android.scanmove.utilities.QueryUtility;
import com.example.android.scanmove.utilities.UrlsConfig;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;


public class MainActivity extends AppCompatActivity {

    // To inspect that user launch this activity at first time or not
    private boolean firstLaunchState = true;

    private final int MY_PERMISSIONS_REQUEST_MULTIPLE_PERMISSION = 12;

    // In order to set launch ViewPager item on resume activity
    private int recentViewPagerItem = 2; // Default is Second item from -> [0,1,2,3,4]

    private Firebase ref;
    private Query qRef;
    public static ArrayList<Event> allEvents;

    ProgressDialog dialog;

    //MyFragmentAdapter adapter;

//    private MyFragmentAdapter initAdapter(){
//
//        return MyFragmentAdapter.getFragmentAdapterInstance(getSupportFragmentManager(), this);
//
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set the content of the activity to use the activity_main.xml layout file
        setContentView(R.layout.activity_main);

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            ActivityCompat.requestPermissions(this, new String[]{
//                            Manifest.permission.CAMERA},
//                    MY_PERMISSIONS_REQUEST_MULTIPLE_PERMISSION);
//        }


        setUpAppScreen();

        firstLaunchState = false;

    }

    /**
     * Dispatch onResume() to fragments.  Note that for better inter-operation
     * with older versions of the platform, at the point of this call the
     * fragments attached to the activity are <em>not</em> resumed.  This means
     * that in some cases the previous state may still be saved, not allowing
     * fragment transactions that modify the state.  To correctly interact
     * with fragments in their proper state, you should instead override
     * {@link #onResumeFragments()}.
     */
    @Override
    protected void onResume() {
        super.onResume();

        queryAllEvent();

        if(!firstLaunchState){
            setUpAppScreen();
        }

    }

    /**
     * Dispatch onPause() to fragments.
     */
    @Override
    protected void onPause() {
        super.onPause();

        // Find the view pager that will allow the user to swipe between fragments
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);

        // get current position user viewing
        recentViewPagerItem = viewPager.getCurrentItem();

    }

    private void setUpAppScreen(){

        // Find the view pager that will allow the user to swipe between fragments
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);

        // Create an adapter that knows which fragment should be shown on each page
        //adapter = initAdapter();
        MyFragmentAdapter adapter = new MyFragmentAdapter(getSupportFragmentManager(), this);

        // Set the adapter onto the view pager
        viewPager.setAdapter(adapter);

        // Launch app on specific fragment
        viewPager.setCurrentItem(recentViewPagerItem);

        // Attach SmartTab to activity
        // Find the tab layout that shows the tabs
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        // Connect the tab layout with the view pager. This will
        //   1. Update the tab layout when the view pager is swiped
        //   2. Update the view pager when a tab is selected
        //   3. Set the tab layout's tab names with the view pager's adapter's titles
        //      by calling onPageTitle()
        tabLayout.setupWithViewPager(viewPager);

    }

    private void queryAllEvent(){

        final ArrayList<Event> allEventTmp = new ArrayList<>();

        dialog = ProgressDialog.show(this, "",
                "Loading. Please wait...", true);

        /** Query process goes here **/
        if (Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        Firebase.setAndroidContext(this);
        ref = new Firebase(UrlsConfig.FIREBASE_URL);

        qRef = ref.orderByChild("properties");

        // DONE : Query data from Fire base below
        // DONE : remove counter when update data in FireBase database
        qRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.getValue() != null) {

                    for (DataSnapshot msg : dataSnapshot.getChildren()) {


                        // Retrieve all data from FireBase using QueryUtility
                        ArrayList<Event> mEvents = QueryUtility.extractEvents(msg);

                        // DONE : add mEvents to allEvents list by using addAll(Collection<? extends E> c)
                        allEventTmp.addAll(mEvents);

                        //Log.i(LOG_TAG, "allEvents Size : " + allEventTmp.size());

                    }

                    // DONE : check empty event here
                    allEvents = cleanEventList(allEventTmp);

                    // DONE set up interest listView after querying
                    InterestedFragment.setUpInterestList(MainActivity.this);

                    dialog.dismiss();

                }

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

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
