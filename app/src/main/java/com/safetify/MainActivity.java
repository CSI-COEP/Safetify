package com.safetify;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.provider.ContactsContract;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.CancellationToken;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.OnTokenCanceledListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    RelativeLayout relativeLayout;
    Button police, hospital;


    private static final int IGNORE_BATTERY_OPTIMIZATION_REQUEST = 1002;
    private static final int PICK_CONTACT = 1;

    //create instances of various classes to be used
    Button button1;
    ListView listView;
    DbHelper db;
    List<ContactModel> list;
    CustomAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        FirebaseAuth fAuth = FirebaseAuth.getInstance();
        FirebaseUser user = fAuth.getInstance().getCurrentUser();
        if (user == null) {
            Intent intent = new Intent(getApplicationContext(), EnterPhoneNo.class);
            startActivity(intent);
            finish();
        }

        DbHelper db = new DbHelper(MainActivity.this);

        List<ContactModel> list = db.getAllContacts();
        for(ContactModel c : list){
            TextView homePh = findViewById(R.id.homePh);
            homePh.setText(c.getPhoneNo());
        }


        FloatingActionButton sos = findViewById(R.id.fab);
        sos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Sos.class);
                startActivity(intent);
                FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(getApplicationContext());
                //use the PRIORITY_BALANCED_POWER_ACCURACY so that the service doesn't use unnecessary power via GPS
                //it will only use GPS at this very moment
                if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                fusedLocationClient.getCurrentLocation(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY, new CancellationToken() {
                    @Override
                    public boolean isCancellationRequested() {
                        return false;
                    }

                    @NonNull
                    @Override
                    public CancellationToken onCanceledRequested(@NonNull OnTokenCanceledListener onTokenCanceledListener) {
                        return null;
                    }
                }).addOnSuccessListener(new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        //check if location is null
                        //for both the cases we will create different messages
                        if (location != null) {

                            //get the SMSManager
                            SmsManager smsManager = SmsManager.getDefault();
                            //get the list of all the contacts in Database
                            DbHelper db = new DbHelper(MainActivity.this);

                            List<ContactModel> list = db.getAllContacts();
                            //send SMS to each contact
                            for (ContactModel c : list) {
                                String message = "Hey, " + c.getName() + " I am in DANGER, i need help. Please urgently reach me out. Here is my location.\n " + "http://maps.google.com/?q=" + location.getLatitude() + "," + location.getLongitude();
                                smsManager.sendTextMessage(c.getPhoneNo(), null, message, null, null);
                            }
                        } else {
                            String message = "I am in DANGER, i need help. Please urgently reach me out.\n" + "GPS was turned off.Couldn't find location. Call your nearest Police Station.";
                            SmsManager smsManager = SmsManager.getDefault();
                            DbHelper db = new DbHelper(MainActivity.this);
                            List<ContactModel> list = db.getAllContacts();
                            for (ContactModel c : list) {
                                smsManager.sendTextMessage(c.getPhoneNo(), null, message, null, null);
                            }
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("Check: ", "OnFailure");
                        String message = "I am in DANGER, i need help. Please urgently reach me out.\n" + "GPS was turned off.Couldn't find location. Call your nearest Police Station.";
                        SmsManager smsManager = SmsManager.getDefault();
                        DbHelper db = new DbHelper(MainActivity.this);
                        List<ContactModel> list = db.getAllContacts();
                        for (ContactModel c : list) {
                            smsManager.sendTextMessage(c.getPhoneNo(), null, message, null, null);
                        }
                    }
                });
            }
        });



        //*******************************************************************************

        //*******************************************************************************


        relativeLayout = findViewById(R.id.safePlace);
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SafePlacesMap.class);
                startActivity(intent);
            }
        });

        relativeLayout = findViewById(R.id.trackMe);
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Trackme.class);
                startActivity(intent);
            }
        });

        relativeLayout = findViewById(R.id.trustedlayout);
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Trackme.class);
                startActivity(intent);
            }
        });
        relativeLayout = findViewById(R.id.first);
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, FirstAid.class);
                startActivity(intent);
            }
        });

        LinearLayout linearLayoutLayout = findViewById(R.id.emergency);
        linearLayoutLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, EmergncyContact.class);
                startActivity(intent);
            }
        });

        relativeLayout = findViewById(R.id.educate);
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SelfDefence.class);
                startActivity(intent);
            }
        });

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setBackground(null);
        MenuItem item = bottomNavigationView.getMenu().findItem(R.id.home);
        item.setCheckable(true);
        item.setChecked(true);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        break;
                    case R.id.settings:
                        Intent a = new Intent(MainActivity.this,Settings.class);
                        startActivity(a);
                        break;
                }
                return false;
            }
        });



    }

}