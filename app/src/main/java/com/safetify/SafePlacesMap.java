package com.safetify;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.safetify.databinding.ActivitySafePlacesMapBinding;

import java.util.ArrayList;

public class SafePlacesMap extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivitySafePlacesMapBinding binding;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private static final int Request_code = 101;
    double lat, lng;
    Button police, hospital;


    LatLng Mumbai_Port_Trust_Hospital = new LatLng(19.02216863548695, 72.86345185034394);
    LatLng Police_Hospital_And_Maternity_Home = new LatLng(19.01822433769103, 72.85610109087216);
    LatLng Bpt_Hospital_D3 = new LatLng(19.012910586102265, 72.862515828353);
    LatLng Acworth_Municipal_Hospital_For_Leprosy= new LatLng(19.02009244385415, 72.85858797559135);
    LatLng Smt_S_R_Mehta_SirK_P_Cardiac_Institute_Blood_Centre = new LatLng(19.032567823260294, 72.8594919692907);
    LatLng Sobti_Hospital = new LatLng(19.026949468747002, 72.8570266957127);
    LatLng Sai_Multispeciality_Hospital_ICU= new LatLng(19.02216863548695, 72.86345185034394);
    LatLng SUNSHINE_HEALTHCARE_DIAGNOSTIC_CENTRE= new LatLng(19.01818597119709, 72.86586725659781);
    LatLng DrKohiar_Clinic= new LatLng(19.018160843641827, 72.8560704216784);
    LatLng Sadguru_Clinic= new LatLng(19.02059251135467, 72.86381812600511);
    LatLng Wadala_Police_Station = new LatLng(19.016301047150584, 72.86571097511225);
    LatLng Nadkarni_Park_Police_Station = new LatLng( 19.018684927781923, 72.86186429282962);
    LatLng AntophillPoliceStation= new LatLng(19.031356136218097, 72.86799810430306);
    LatLng Wadala_Village_Police_Chowky= new LatLng(19.01701361868587, 72.85379346249384);


    private ArrayList<LatLng> locationArrayList;
    private ArrayList<LatLng> locationPolice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        checkGPS();

        binding = ActivitySafePlacesMapBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this.getApplicationContext());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);




//Hospitals**************************************


        locationArrayList = new ArrayList<>();

        // on below line we are adding our
        // locations in our array list.
        locationArrayList.add(Mumbai_Port_Trust_Hospital);
        locationArrayList.add(Police_Hospital_And_Maternity_Home);
        locationArrayList.add(Bpt_Hospital_D3);
        locationArrayList.add(Acworth_Municipal_Hospital_For_Leprosy);
        locationArrayList.add(Smt_S_R_Mehta_SirK_P_Cardiac_Institute_Blood_Centre);
        locationArrayList.add(Sobti_Hospital);
        locationArrayList.add(Sai_Multispeciality_Hospital_ICU);
        locationArrayList.add(SUNSHINE_HEALTHCARE_DIAGNOSTIC_CENTRE);
        locationArrayList.add(DrKohiar_Clinic);
        locationArrayList.add(Sadguru_Clinic);

//POLICE stations***************************************

        locationPolice = new ArrayList<>();
        locationPolice.add(Wadala_Police_Station);
        locationPolice.add(Nadkarni_Park_Police_Station);
        locationPolice.add(Wadala_Village_Police_Chowky);



        police= findViewById(R.id.police);
        hospital=findViewById(R.id.hospital);

        police.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String currentLatLng= lat + ", " + lng ;
                String latLng="19.018684927781923, 72.86186429282962";
                String header="Nearest Police Station";
                Intent book = new Intent(SafePlacesMap.this, showdirection.class);
                book.putExtra("lat", currentLatLng);
                book.putExtra("lng", latLng);
                book.putExtra("header", header);
                startActivity(book);

            }
        });

        hospital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String currentLatLng= lat + ", " + lng ;
                String latLng="19.02216863548695, 72.86345185034394";
                String header="Nearest Hospital";
                Intent book = new Intent(SafePlacesMap.this, showdirection.class);
                book.putExtra("lat", currentLatLng);
                book.putExtra("lng", latLng);
                book.putExtra("header", header);
                startActivity(book);

            }
        });

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
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap = googleMap;
        googleMap.setMapStyle(
                MapStyleOptions.loadRawResourceStyle(this, R.raw.custommap)
        );


        for (int i = 0; i < locationArrayList.size(); i++) {

            // below line is use to add marker to each location of our array list.
            mMap.addMarker(new MarkerOptions().position(locationArrayList.get(i)).icon(BitmapDescriptorFactory.fromResource(R.drawable.locationhospital)));

        }

        for (int i = 0; i < locationPolice.size(); i++) {

            // below line is use to add marker to each location of our array list.
            mMap.addMarker(new MarkerOptions().position(locationPolice.get(i)).icon(BitmapDescriptorFactory.fromResource(R.drawable.locationpolice)));

        }

        // Add a marker in Sydney and move the camera
        getCurrentLocation();
    }

    private void getCurrentLocation(){
        if (ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions
                    (this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},Request_code);
            return;
        }
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setFastestInterval(60000);
        locationRequest.setInterval(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setFastestInterval(5000);
        LocationCallback locationCallback = new LocationCallback(){


            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
              //  Toast.makeText(getApplicationContext(), "Location result is: " + locationResult, Toast.LENGTH_LONG).show();
                if(locationResult==null){
                 //   Toast.makeText(getApplicationContext(), "Current location is null", Toast.LENGTH_LONG).show();

                    return;
                }

                for (Location location: locationResult.getLocations()){
                    if (location !=null){
               //         Toast.makeText(getApplicationContext(), "Current location is"+location.getLongitude(), Toast.LENGTH_LONG).show();
                    }
                }
            }
        };
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, null);

        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {

                if(location != null){
                    lat = location.getLatitude();
                    lng = location.getLongitude();


                    LatLng latLng= new LatLng(lat, lng);
                    mMap.addMarker(new MarkerOptions().position(latLng).title("Your Location").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                    mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16));
                }

            }
        });
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (Request_code){
            case Request_code:
                if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    getCurrentLocation();
                }
        }
    }

    private void checkGPS(){
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setInterval(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(5000);
        locationRequest.setFastestInterval(3000);
        LocationSettingsRequest.Builder builder=new LocationSettingsRequest.Builder().addLocationRequest(locationRequest).setAlwaysShow(true);

        Task<LocationSettingsResponse>locationSettingsResponseTask=LocationServices.getSettingsClient(getApplicationContext()).checkLocationSettings(builder.build());
    }
}