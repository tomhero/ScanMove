package com.example.android.scanmove.utilities;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by 'Chayut' on 5/12/2559.
 *
 *
 */

public class ReadWriteXMLUtility {

    private static final String FILE_INTEREST = "interestData";
    private static final String FILE_FAVORITE = "favoriteData";
    private static String LOG_TAG = ReadWriteXMLUtility.class.getSimpleName();

    private ReadWriteXMLUtility() {

    }

    public static void addFavoritePlace(Context context, String placeEngName) {

        placeEngName = placeEngName.concat(",");

        try {
            FileOutputStream fOut = context.openFileOutput(FILE_FAVORITE, Context.MODE_APPEND); // mode append
            fOut.write(placeEngName.getBytes(Charset.forName("UTF-8")));
            fOut.close();

            Toast.makeText(context, "Favorite Place Added", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            Log.e(LOG_TAG, e.toString());

        }
    }


    public static void addInterestEvent(Context context, String eventId) {

        eventId = eventId.concat(",");

        try {
            FileOutputStream fOut = context.openFileOutput(FILE_INTEREST, Context.MODE_APPEND); // mode append
            fOut.write(eventId.getBytes(Charset.forName("UTF-8")));
            fOut.close();

            Toast.makeText(context, "You are interesting this event", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            Log.e(LOG_TAG, e.toString());

        }
    }

    public static ArrayList<String> getAllFavoritePlace(Context context) {

        ArrayList<String> allPlaceName = new ArrayList<>();

        try {
            FileInputStream fin = context.openFileInput(FILE_FAVORITE);
            int c;
            String temp = "";
            while ((c = fin.read()) != -1) {
                temp = temp + Character.toString((char) c);
            }

            String rawText = temp;


            allPlaceName.addAll(new ArrayList<>(Arrays.asList(rawText.split("\\s*,\\s*"))));

            allPlaceName = makeNoDuplicate(allPlaceName);


            //Toast.makeText(context, "Getting all favorite place..." , Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            Log.e(LOG_TAG, e.toString());

        }

        return allPlaceName;

    }

    public static ArrayList<String> getAllInterestEvent(Context context) {

        ArrayList<String> allInterestEvent = new ArrayList<>();

        try {
            FileInputStream fin = context.openFileInput(FILE_INTEREST);
            int c;
            String temp = "";
            while ((c = fin.read()) != -1) {
                temp = temp + Character.toString((char) c);
            }

            String rawText = temp;

            allInterestEvent.addAll(new ArrayList<>(Arrays.asList(rawText.split("\\s*,\\s*"))));

            allInterestEvent = makeNoDuplicate(allInterestEvent);

            //Toast.makeText(context, "Getting all Interested Event...", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            Log.e(LOG_TAG, e.toString());

        }

        return allInterestEvent;

    }

    public static void deleteInterested(Context context, String delString) {

        String newData = null;

        // Load
        try {
            FileInputStream fin = context.openFileInput(FILE_INTEREST);
            int c;
            String temp = "";
            while ((c = fin.read()) != -1) {
                temp = temp + Character.toString((char) c);
            }

            //Delete
            newData = temp.replace(delString, "");
            newData = newData.replace(",,", ","); // del between a,X,b,
            if (newData.charAt(0) == ',') {
                newData = newData.substring(1); // del First X,b,c,
            }
            fin.close();
        } catch (Exception e) {
            Log.e(LOG_TAG, e.toString());
        }

        Toast.makeText(context, "Remove this event from interested", Toast.LENGTH_SHORT).show();

        //Write Again
        try {
            FileOutputStream fOut = context.openFileOutput(FILE_INTEREST, Context.MODE_PRIVATE);
            // mode PRIVATE
            assert newData != null;
            fOut.write(newData.getBytes());
            fOut.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            Log.e(LOG_TAG, e.toString());
        }
    }


    public static void deleteFavorite(Context context, String delString) {

        String newData = null;

        // Load
        try {
            FileInputStream fin = context.openFileInput(FILE_FAVORITE);
            int c;
            String temp = "";
            while ((c = fin.read()) != -1) {
                temp = temp + Character.toString((char) c);
            }

            //Delete
            newData = temp.replace(delString, "");
            newData = newData.replace(",,", ","); // del between a,X,b,
            if (newData.charAt(0) == ',') {
                newData = newData.substring(1); // del First X,b,c,
            }
            fin.close();
        } catch (Exception e) {
            Log.e(LOG_TAG, e.toString());
        }

        Toast.makeText(context, "Remove " + delString + "from favorite place", Toast.LENGTH_SHORT).show();

        //Write Again
        try {
            FileOutputStream fOut = context.openFileOutput(FILE_FAVORITE, Context.MODE_PRIVATE);
            // mode PRIVATE
            assert newData != null;
            fOut.write(newData.getBytes());
            fOut.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            Log.e(LOG_TAG, e.toString());
        }

    }

    private static ArrayList<String> makeNoDuplicate(ArrayList<String> list) {

        // add elements to al, including duplicates
        Set<String> hs = new HashSet<>();
        hs.addAll(list);
        list.clear();
        list.addAll(hs);

        return list;
    }


}
