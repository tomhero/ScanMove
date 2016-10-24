package com.example.android.scanmove.activities;


import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
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


    public ScanQRFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //View mScan = inflater.inflate(R.layout.fragment_scan_qr, container, false);

        // getActivity() --> Same as Activity does
        // Do some shit here

//        ZXingScannerView.LayoutParams lp = new ZXingScannerView.LayoutParams(ZXingScannerView.LayoutParams.MATCH_PARENT, ZXingScannerView.LayoutParams.MATCH_PARENT);
//        lp.setMargins(0,0,0,0);


//        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//        lp.setMargins(left, top, right, bottom);
//        imageView.setLayoutParams(lp);

        // check Android 6 permission
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

        //getActivity().setContentView(mScan);
        //mScan.setPadding(0,0,0,0);
        mScan.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorPrimaryDark));
        mScan.setResultHandler(this); // Register ourselves as a handler for scan results.
        mScan.startCamera();

        return mScan;
    }

    /**
     * Called when the fragment is visible to the user and actively running.
     * This is generally
     * tied to {@link MainActivity#onResume() Activity.onResume} of the containing
     * Activity's lifecycle.
     */
    @Override
    public void onResume() {
        super.onResume();

        mScan.resumeCameraPreview(this);

    }



    /**
     * Called when the Fragment is no longer started.  This is generally
     * tied to {@link MainActivity#onStop() Activity.onStop} of the containing
     * Activity's lifecycle.
     */
    @Override
    public void onStop() {
        super.onStop();

        mScan.stopCameraPreview();
        //mScan.stopCamera();
    }

    @Override
    public void handleResult(Result result) {

        Intent intent = new Intent(getActivity(), LandmarkActivity.class);
        intent.putExtra("TARGET", result.getText());
        startActivity(intent);

        Toast.makeText(getActivity(), "TARGET : " + result.getText(), Toast.LENGTH_SHORT).show();

//        new AlertDialog.Builder(getActivity())
//                .setTitle("Scan Result")
//                .setMessage("TEXT : " + result.getText())
//                .setNeutralButton("อืม", new DialogInterface.OnClickListener(){
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        // Do nothing
//                    }
//                })
//                .show();

        // mScan.resumeCameraPreview(this);  // set for resume
    }

}
