package com.example.android.scanmove.utilities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;

import com.example.android.scanmove.R;
import com.example.android.scanmove.activities.LandmarkActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by 'Chayut' on 2/12/2559.
 *
 */

public class GoogleMapUtility implements OnMapReadyCallback {

    private Context ctx;
    private Marker marker;

    public GoogleMapUtility(Context ctx) {
        this.ctx = ctx;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        // latitude and longitude
//        double latitude = 17.385044;
//        double longitude = 78.486671;

        // create marker
//        MarkerOptions marker = new MarkerOptions().position(
//                new LatLng(latitude, longitude)).title("Hello Maps");
//
//        // Changing marker icon
//        marker.icon(BitmapDescriptorFactory
//                .defaultMarker(BitmapDescriptorFactory.HUE_ROSE));

        // adding marke

//        googleMap.addMarker(marker);
//        CameraPosition cameraPosition = new CameraPosition.Builder()
//                .target(new LatLng(13, 100)).zoom(12).build();
//        googleMap.animateCamera(CameraUpdateFactory
//                .newCameraPosition(cameraPosition));

        String[] engPlaceName = ctx.getResources().getStringArray(R.array.eng_place);
        int index = 0;

        if (ContextCompat.checkSelfPermission(ctx, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            googleMap.setMyLocationEnabled(true);
            googleMap.getUiSettings().setCompassEnabled(true);
            googleMap.getUiSettings().setZoomControlsEnabled(false); // hide zoom btn
        }

        marker =
        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(13.727157, 100.77321))
                .title("ร้าน Igear")
                .snippet(engPlaceName[index++])
        );


        marker =
        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(13.7245722, 100.7761328))
                .title("โรงอาหารสถาปัตย์")
                .snippet(engPlaceName[index++])
        );

        marker =
        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(13.726454, 100.775338))
                .title("หอประชุมประสม รังสิโรจน์")
                .snippet(engPlaceName[index++])
        );

        marker =
        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(13.731111, 100.780235))
                .title("สำนักงานคอมพิวเตอร์และคณะบริหาร")
                .snippet(engPlaceName[index++])
        );

        marker =
        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(13.727608,  100.773556))
                .title("ตึกวิศวกรรมอุตาสาหการ")
                .snippet(engPlaceName[index++])
        );

        marker =
        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(13.727521, 100.77394))
                .title("ตึกวิศวกรรมเครื่องกล")
                .snippet(engPlaceName[index++])
        );

        marker =
        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(13.727058, 100.774376))
                .title("ตึกวิศวกรรมโยธา")
                .snippet(engPlaceName[index++])
        );

        marker =
        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(13.727493, 100.774443))
                .title("ตึกวิศวกรรมการวัดคุม")
                .snippet(engPlaceName[index++])
        );

        marker =
        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(13.7291455, 100.7759867))
                .title("อาคารปฏิบัติการรวมวิศวกรรมศาสตร์ 2")
                .snippet(engPlaceName[index++])
        );

        marker =
        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(13.72631843806725, 100.7724133201932))
                .title("ตึกวิศวกรรมการเกษตร")
                .snippet(engPlaceName[index++])
        );

        marker =
        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(13.726369, 100.773157))
                .title("อาคารปฏิบัติการรวมวิศวกรรมศาสตร์")
                .snippet(engPlaceName[index++])
        );

        marker =
        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(13.725943, 100.773878))
                .title("ตึกบูรณาการ")
                .snippet(engPlaceName[index++])
        );

        marker =
        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(13.726454, 100.775338))
                .title("โรงอาหารสถาปัตย์")
                .snippet(engPlaceName[index++])
        );

        marker =
        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(13.731108, 100.781384))
                .title("คณะเทคโนโลยีสารสนเทศ")
                .snippet(engPlaceName[index++])
        );

        marker =
        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(13.7311602, 100.7810433))
                .title("ร้านป้าไม่ได้อะไรเลย")
                .snippet(engPlaceName[index++])
        );

        marker =
        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(13.726664, 100.775836))
                .title("โรงอาหารฝั่งวิศวะและสถาปัตย์")
                .snippet(engPlaceName[index++])
        );

        marker =
        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(13.726758, 100.774739))
                .title("ร้านกาแฟฝั่งวิศวะและสถาปัตย์")
                .snippet(engPlaceName[index++])
        );

        marker =
        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(13.730791, 100.777788))
                .title("ตึกอธิการบดี")
                .snippet(engPlaceName[index++])
        );

        marker =
        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(13.730969, 100.778601))
                .title("ลานพระจอม")
                .snippet(engPlaceName[index++])
        );

        marker =
        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(13.729976, 100.777187))
                .title("อาคารเรียนรวมตึกพระเทพ")
                .snippet(engPlaceName[index++])
        );

        marker =
        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(13.729477, 100.777361))
                .title("ตึกอนามัย")
                .snippet(engPlaceName[index++])
        );

        marker =
        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(13.729474, 100.776857))
                .title("ไปรษณีย์ลาดกระบัง")
                .snippet(engPlaceName[index++])
        );

        marker =
        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(13.729997, 100.775897))
                .title("โรงอาหารตึกพระเทพ")
                .snippet(engPlaceName[index++])
        );

        marker =
        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(13.729485, 100.775949))
                .title("Cafe de late")
                .snippet(engPlaceName[index++])
        );

        marker =
        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(13.731075, 100.77473))
                .title("สมาคมศิษย์เก่า")
                .snippet(engPlaceName[index++])
        );

        marker =
        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(13.730258, 100.77173))
                .title("สนามกีฬาลาดกระบัง")
                .snippet(engPlaceName[index++])
        );

        marker =
        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(13.728733, 100.772878))
                .title("โรงยิมแบดมินตัน")
                .snippet(engPlaceName[index++])
        );

        marker =
        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(13.730443, 100.775305))
                .title("สระว่ายน้ำลาดกระบัง")
                .snippet(engPlaceName[index++])
        );

        marker =
        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(13.7284883, 100.7740285))
                .title("หอพักนักศึกษา")
                .snippet(engPlaceName[index++])
        );

        marker =
        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(13.728741, 100.775178))
                .title("โรงอาหารโรงแอล")
                .snippet(engPlaceName[index++])
        );

        marker =
        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(13.728669, 100.776974))
                .title("ธนาคารกสิกรไทย")
                .snippet(engPlaceName[index++])
        );

        marker =
        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(13.728696, 100.776773))
                .title("ธนาคารกรุงศรีอยุธยาและธนาคารกรุงไทย")
                .snippet(engPlaceName[index++])
        );

        marker =
        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(13.7295293, 100.7797699))
                .title("ตึกจุฬาภรณ์วิลัยลักษณ์")
                .snippet(engPlaceName[index++])
        );

        marker =
        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(13.7295264, 100.7794049))
                .title("หอประชุมจุฬาภรณ์วิลัยลักษณ์")
                .snippet(engPlaceName[index++])
        );

        marker =
        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(13.727411, 100.777117))
                .title("หอประชุมเก่า")
                .snippet(engPlaceName[index++])
        );

        marker =
        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(13.72771, 100.778908))
                .title("อาคารเฉลิมพระเกียรติ")
                .snippet(engPlaceName[index++])
        );

        marker =
        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(13.726678, 100.779189))
                .title("หอประชุมใหม่")
                .snippet(engPlaceName[index++])
        );

        marker =
        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(13.7284996, 100.7744263))
                .title("7-11 หอใน")
                .snippet(engPlaceName[index++])
        );

        marker =
        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(13.7254908, 100.7805291))
                .title("โรงอาหารกลางน้ำ")
                .snippet(engPlaceName[index++])
        );

        marker =
        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(13.725835, 100.7801512))
                .title("ครัวสุนันท์")
                .snippet(engPlaceName[index++])
        );

        marker =
        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(13.73116, 100.782041))
                .title("โรงอาหารคณะไอที")
                .snippet(engPlaceName[index++])
        );

        marker =
        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(13.728989, 100.777321))
                .title("วิทยาลัยนาโนเทคโนโลยี")
                .snippet(engPlaceName[index++])
        );

        marker =
        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(13.728989, 100.777321))
                .title("วิทยาลัยนานาชาติ")
                .snippet(engPlaceName[index++])
        );

        marker =
        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(13.726406, 100.780779))
                .title("คณะเทคโนโลยีการเกษตรและคณะอุตสาหกรรม")
                .snippet(engPlaceName[index++])
        );

        marker =
        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(13.7289323, 100.7786371))
                .title("โรงอาหารคณะวิทยาศาสตร์")
                .snippet(engPlaceName[index++])
        );

        marker =
        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(13.728972, 100.778863))
                .title("ร้านถ่ายเอกสารและร้านขนมคณะวิทยาศาสตร์")
                .snippet(engPlaceName[index++])
        );

        marker =
        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(13.7294264, 100.7791742))
                .title("คณะวิทยาศาสตร์")
                .snippet(engPlaceName[index++])
        );

        marker =
        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(13.7300871, 100.7807308))
                .title("คณะครุศาสตร์")
                .snippet(engPlaceName[index++])
        );

        marker =
        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(13.729998, 100.781545))
                .title("ตึกคณะเทคโนโลยีการเกษตร")
                .snippet(engPlaceName[index++])
        );

        marker =
        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(13.7292504, 100.7808068))
                .title("โรงอาหารครุศาสตร์")
                .snippet(engPlaceName[index++])
        );

        marker =
        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(13.7289576, 100.7813197))
                .title("โรงอาหารคณะเทคโนโลยีการเกษตร")
                .snippet(engPlaceName[index++])
        );

        marker =
        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(13.72522988447332, 100.775500352169))
                .title("อาคารเรียนรวม")
                .snippet(engPlaceName[index++])
        );

        marker =
        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(13.72742011092707, 100.7764013584444))
                .title("วิศวกรรมโทรคมนาคม")
                .snippet(engPlaceName[index++])
        );

        marker =
        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(13.72721616785576, 100.7764060954491))
                .title("ตึกคณะวิศวกรรมศาสตร์")
                .snippet(engPlaceName[index++])
        );

        marker =
        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(13.72715988452907, 100.7757707944266))
                .title("โรงอาหารเจ้าคุณ")
                .snippet(engPlaceName[index++])
        );

        marker =
        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(13.72831, 100.775037))
                .title("สถานีรถไฟพระจอมเกล้า")
                .snippet(engPlaceName[index++])
        );

        marker =
        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(13.72754430228742, 100.7723905320033))
                .title("ตึกโหลคณะวิศวกรรมศาสตร์")
                .snippet(engPlaceName[index++])
        );

        marker =
        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(13.72754430228742, 100.7723905320033))
                .title("สถานีรถไฟหัวตะเข้")
                .snippet(engPlaceName[index++])
        );

        marker =
        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(13.72625934642938, 100.775962041802))
                .title("U-Store KMITL")
                .snippet(engPlaceName[index++])
        );

        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(13.72927456695318, 100.776923300995))
                .title("True Coffee")
                .snippet(engPlaceName[index++])
        );

        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(13.72927456695318,
                100.776923300995), 15)); //<-- LatLng for Camera Zoom

        googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {

                Intent intent = new Intent(ctx, LandmarkActivity.class);
                intent.putExtra("TARGET", marker.getSnippet()); // DONE : use snippet instead of title
                ctx.startActivity(intent);

            }
        });




    }
}
