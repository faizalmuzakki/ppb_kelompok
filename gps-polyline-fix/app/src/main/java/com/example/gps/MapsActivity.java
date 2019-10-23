package com.example.gps;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private LocationManager lm;
    private LocationListener ll;
    private lokasiListener mylocationListener;
    private Polyline pl;
    public List<LatLng> point;
    private LatLng current = null;
    private LatLng before = null;
    private LatLng myPos = null;

    private class lokasiListener implements LocationListener {
        private TextView txtLat, txtLong;

        @Override
        public void onLocationChanged(Location location) {
            txtLat = findViewById(R.id.idLokasiLat);
            txtLong = findViewById(R.id.idLokasiLng);
            txtLat.setText(String.valueOf(location.getLatitude()));
            txtLong.setText(String.valueOf(location.getLongitude()));

//            double latitude = location.getLatitude();
//            double longitude = location.getLongitude();
            //LatLng myPos = new LatLng(latitude,longitude);

            //pl = mMap.addPolyline(new PolylineOptions().add(new LatLng(latitude, longitude)));
            if(current==null){
                before = null;
                current = new LatLng(location.getLatitude(), location.getLongitude());
//                gotoPeta(location.getLatitude(), location.getLongitude(), 15);
                Toast.makeText(getBaseContext(), "GPS Capture:" + location.getLatitude() + " " + location.getLongitude(), Toast.LENGTH_LONG).show();
            }
            else{
                before = new LatLng(current.latitude, current.longitude);
                current = new LatLng(location.getLatitude(), location.getLongitude());
                tambahGaris();
                Toast.makeText(getBaseContext(), "GPS Capture:" + location.getLatitude() + " " + location.getLongitude() + " before" + before.latitude + " " + before.longitude, Toast.LENGTH_LONG).show();
            }
//            if(myPos == null) {
//                myPos = new LatLng(latitude, longitude);
//            }else {
//
//                Polyline route = mMap.addPolyline(new PolylineOptions());
//                route.setPoints(point);
//                LatLng x = myPos;
//                point.add(myPos);
//                Log.d("tes polyline", x.toString());
//            }
//            point.add(myPos);
//            Polyline route = mMap.addPolyline(new PolylineOptions());
//            route.setPoints(point);

            //pl = mMap.addPolyline(new PolylineOptions().add(new LatLng(latitude, longitude)));
            //mMap.addMarker(new MarkerOptions().position(myPos).title("Marker in " + latitude + ":" +longitude));
            //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myPos, 50));
            //Log.d("tes polyline", point.toString());
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
        mapFragment.getMapAsync(this);
        LocationManager mylocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        mylocationListener = new lokasiListener();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            return;
        }
        mylocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2, 2, mylocationListener);
        mylocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 2, 2, mylocationListener);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override

    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMyLocationEnabled(true);
    }

    private void tambahGaris(){
        Polyline poli = mMap.addPolyline(new PolylineOptions().add(current).add(before));
        poli.setWidth(10f);
        poli.setColor(Color.BLUE);
    }
}