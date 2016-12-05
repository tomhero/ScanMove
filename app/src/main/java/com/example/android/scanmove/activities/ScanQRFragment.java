package com.example.android.scanmove.activities;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.android.scanmove.R;
import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ScanQRFragment extends Fragment implements ZXingScannerView.ResultHandler {

    private static final String LOG_TAG = "ScanQRFragment";
    private ZXingScannerView mScan;
    public static final int MY_PERMISSIONS_REQUEST_CAMERA = 42;
    // create boolean for check.... something
    private boolean triggered = false;
    FragmentActivity listener;


    public ScanQRFragment() {
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
        //View mScan = inflater.inflate(R.layout.fragment_scan_qr, container, false);

        // getActivity() --> Same as Activity does
        // Do some shit here

        //startCameraInstance();

        //mScan.startCamera(); // camera will start soon

        // view initialization steps.......
        startCameraInstance();
        mScan.startCamera();
        triggered = true;

        return mScan;
    }

    /**
     * Called when the Fragment is no longer resumed.  This is generally
     * tied to {@link MainActivity#onPause() Activity.onPause} of the containing
     * Activity's lifecycle.
     */
    @Override
    public void onPause() {
        super.onPause();
        mScan.stopCameraPreview();
    }

    @Override
    public void handleResult(Result result) {

        // Check the Network Connection here
        ConnectivityManager connMgr = (ConnectivityManager)
                getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            // Ok
            Intent intent = new Intent(getActivity(), LandmarkActivity.class);
            intent.putExtra("TARGET", result.getText());
            startActivity(intent);
        } else {
            // No connection
            Toast.makeText(getActivity(), "Please check your internet connection!!", Toast.LENGTH_SHORT).show();
            mScan.resumeCameraPreview(this);
        }

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        //Log.i(LOG_TAG, "UserVisibleHint -> " + isVisibleToUser);

        // To start or resume camera when fragment is visible
        if (!triggered && isVisibleToUser && isAdded()) {
            startCameraInstance();
            mScan.startCamera();
            triggered = true;
        } else if (triggered && isVisibleToUser) {
            mScan.resumeCameraPreview(this);
        }
        else if (triggered && !isVisibleToUser){
            // if fragment is turning invisible
            mScan.stopCameraPreview();
        }
    }

    private void startCameraInstance() {

         //check Android 6 permission
         //isAdded() -> to check the fragment is already detached or not
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (getActivity().getApplicationContext().checkSelfPermission(android.Manifest.permission.CAMERA)
                    == PackageManager.PERMISSION_GRANTED) {

                // CameraManager must be initialized here, not in onCreate(). This is necessary because we don't
                // want to open the camera driver and measure the screen size if we're going to show the help on
                // first launch. That led to bugs where the scanning rectangle was the wrong size and partially
                // off screen.
                mScan = new ZXingScannerView(this.getContext());

            } else {
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{android.Manifest.permission.CAMERA},
                        MY_PERMISSIONS_REQUEST_CAMERA);

                mScan = new ZXingScannerView(this.getContext());
            }
        } else {
            mScan = new ZXingScannerView(this.getContext());
        }

        mScan.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorPrimaryDark));
        mScan.setResultHandler(this); // Register ourselves as a handler for scan results.

    }

}
