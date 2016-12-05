package com.example.android.scanmove.utilities;

import android.annotation.SuppressLint;
import android.util.Log;

import com.example.android.scanmove.appmodel.Event;
import com.firebase.client.DataSnapshot;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Created by 'Chayut' on 24/10/2559.
 * Helper methods related to requesting and receiving event data from FireBase.
 */

public final class QueryUtility {

    private static final String LOG_TAG = QueryUtility.class.getSimpleName();

    /**
     * Create a private constructor because no one should ever create a {@link QueryUtility} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtility (and an object instance of QueryUtility is not needed).
     */
    private QueryUtility() {

    }


    public static ArrayList<Event> extractEvents(DataSnapshot msg){

        //get geometry here and then also does extract coordinate too
        ArrayList<Double>  geometry = (ArrayList<Double>) msg.child("geometry").child("coordinates").getValue();

        // get properties Object here return in Map type and phase to JSON object below
        JSONObject propertiesObj = new JSONObject((Map) msg.child("properties").getValue());

        String placeImageUrl = null, placeName = null, placeThName = null;
        placeImageUrl = propertiesObj.optString("image");
        placeName = propertiesObj.optString("place");
        placeThName = propertiesObj.optString("th_name");

        //  FIXME TESTING AND FAIL T^T : check support method here
        //JSONArray eventsList = supportQuery(msg);

        // Events as an arrayList that contain all event object
        ArrayList<Event> allEvents = new ArrayList<>();

        JSONArray eventsList = null;
        try {
            eventsList = propertiesObj.getJSONArray("events");
        } catch (JSONException e) {
            // if no event in this location, just simply return place data only
            allEvents.add(new Event(geometry, placeThName, placeName, placeImageUrl));
            return allEvents;
        }

        // DONE : Check JSONArray is empty or null
        if(eventsList == null){
            // if no event in this location, just simply return place data only
            allEvents.add(new Event(geometry, placeThName, placeName, placeImageUrl));
            return allEvents;
        }

        // DONE : Iterate through eventsList and set all data into Event Obj
        for (int index = 0; index < eventsList.length(); index++) {

            // temporary single event obj here
            Event tmpEvent = new Event(geometry, placeThName, placeName, placeImageUrl);
            try {
                JSONObject jsonEventObj = eventsList.optJSONObject(index);
                // set all data into tmp event
                tmpEvent.setName(jsonEventObj.optString("name"));
                tmpEvent.setCover(jsonEventObj.optString("cover"));
                tmpEvent.setContent(jsonEventObj.optString("content"));
                tmpEvent.setEvid(jsonEventObj.optString("evid"));

                // DONE : Date Formatted expect here!!
                tmpEvent.setBegin(dateFormat(jsonEventObj.optString("begin")));
                tmpEvent.setEnd(dateFormat(jsonEventObj.optString("end")));

                tmpEvent.setUrl(jsonEventObj.getString("url"));

                // TODO : Remember that tag feature will available later
                JSONArray jsonArray = jsonEventObj.getJSONArray("tag");
                if (jsonArray != null){
                    // check if no any tag or not
                    String[] tags = new String[jsonArray.length()];
                    for (int i = 0; i < jsonArray.length(); i++) {
                        tags[i]= jsonArray.getString(i);
                    }
                    tmpEvent.setTag(tags);
                }


            } catch (JSONException e) {
                Log.e(LOG_TAG, "JSONException: " + e.getMessage());
            } catch (ParseException e) {
                Log.e(LOG_TAG, "ParseException: " + e.getMessage());
            }

            /** then add event to ArrayList<Event> **/
            allEvents.add(tmpEvent);

            // print test
            //Log.v(LOG_TAG, "Event " + (index+1) + "\n" + tmpEvent.toString());

        }

        return allEvents;

    }

    @SuppressLint("SimpleDateFormat")
    static private String dateFormat(String universalDate) throws ParseException {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        Date date = format.parse(universalDate);
        return new SimpleDateFormat("dd MMMM yyyy").format(date);
    }



    static private JSONArray supportQuery(DataSnapshot msg){

        // get properties Object here return in Map type and phase to JSON object below
        //JSONArray tmpPropertiea = new JSONObject(msg.child("properties").child("events").getValue());
        //Log.v(LOG_TAG, "-->" + msg.child("properties").child("events").getValue());

        //GenericTypeIndicator<List<String>> t = new GenericTypeIndicator<List<String>>() {};


        JSONArray tmpEventsList = new JSONArray();
        List list = msg.child("properties").child("events").getValue(List.class);
        for (int i = 0; i < list.size(); i++) {
            tmpEventsList.put(list.get(i));
        }

        Log.v(LOG_TAG, list.size() + " : " + tmpEventsList.toString());

        return tmpEventsList;

    }


}
