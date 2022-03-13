package com.safetify;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

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
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.safetify.databinding.ActivityTrackmeBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Trackme extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityTrackmeBinding binding;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private static final int Request_code = 101;
    double lat, lng;
    DbHelper db;
    String username;

    List<ContactModel> list;

    //***********
    Button btnSendMsg;
    EditText etMsg, usey;
    ListView lvDiscussion;
    ArrayList<String> listConversation = new ArrayList<String>();
    ArrayAdapter arrayAdpt;
    String UserName, user_msg_key;
    private DatabaseReference dbr;
    //***********



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trackme);

        checkGPS();

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this.getApplicationContext());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map2);
        mapFragment.getMapAsync(this);

        DbHelper db = new DbHelper(Trackme.this);
        List<ContactModel> list = db.getAllContacts();

        //send SMS to each contact
        for (ContactModel c : list) {
            TextView name = findViewById(R.id.name);
            TextView phNo = findViewById(R.id.phNo);

            username = c.getName();

            name.setText(c.getName());
            phNo.setText(c.getPhoneNo());
        }

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        btnSendMsg = (Button) findViewById(R.id.btnSendMsg);
        etMsg = (EditText) findViewById(R.id.etMessage);
        lvDiscussion = (ListView) findViewById(R.id.lvConversation);
        arrayAdpt = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listConversation);
        lvDiscussion.setAdapter(arrayAdpt);
        UserName = "Kill";
        dbr = FirebaseDatabase.getInstance().getReference();
        btnSendMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, Object> map = new HashMap<String, Object>();
                user_msg_key = dbr.push().getKey();
                dbr.updateChildren(map);
                DatabaseReference dbr2 = dbr.child(user_msg_key);
                Map<String, Object> map2 = new HashMap<String, Object>();
                map2.put("msg", username + " : " + etMsg.getText().toString());
                dbr2.updateChildren(map2);
                etMsg.setText("");
            }
        });
        dbr.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                updateConversation(dataSnapshot);
            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                updateConversation(dataSnapshot);
            }
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
            }
            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });


    }

    public void updateConversation(DataSnapshot dataSnapshot){
        String msg, conversation;
        Iterator i = dataSnapshot.getChildren().iterator();
        while(i.hasNext()){
            msg = (String) ((DataSnapshot)i.next()).getValue();
            conversation = ""+ msg;
            arrayAdpt.insert(conversation, arrayAdpt.getCount());
            arrayAdpt.notifyDataSetChanged();
        }
    }


    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
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
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
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