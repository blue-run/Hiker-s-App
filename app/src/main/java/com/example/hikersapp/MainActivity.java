package com.example.hikersapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    LocationManager locationManager;
    LocationListener locationListener;
    TextView textView9,textView8,textView7,longitude,textView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView2=findViewById(R.id.textView2);
        longitude=findViewById(R.id.longitude);
        textView7=findViewById(R.id.textView7);
        textView8=findViewById(R.id.textView8);
        textView9=findViewById(R.id.textView9);

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        locationListener =new LocationListener( ) {
            @Override
            public void onLocationChanged(Location location) {
                Log.i("info", location.toString());

                Geocoder geocoder=new Geocoder(getApplicationContext(), Locale.getDefault());
                try {
                    List<Address> addressList=geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
                    if(addressList.get(0) != null && addressList.size()>0){
                        String str="";
                        if(addressList.get(0).getLatitude()!=0){
                           textView2.setText("latitude: "+addressList.get(0).getLatitude());

                        }
                        if(addressList.get(0).getLongitude()!=0){
                            longitude.setText("longitude: "+addressList.get(0).getLongitude());
                        }
                       textView7.setText("altitude: "+location.getAltitude());
                        textView8.setText("accuracy: "+location.getAccuracy());
                        if(addressList.get(0).getSubAdminArea()!=null){
                            textView9.setText("address: "+addressList.get(0).getLocality()+" "+addressList.get(0).getSubAdminArea());
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
        }
        else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,locationListener);
        }
    }
}
