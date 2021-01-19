package com.example.hikerswatch;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    LocationManager locationManager;
    LocationListener locationListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                updateLocationInfo(location);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(@NonNull String provider) {

            }

            @Override
            public void onProviderDisabled(@NonNull String provider) {

            }
        };
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
            Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (lastKnownLocation != null) {
                updateLocationInfo(lastKnownLocation);
            }

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startListening();
            }
        }
    }

    public void startListening() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        }
    }

    public void updateLocationInfo(Location location) {
        TextView lat = findViewById(R.id.latitude_text_view);
        TextView lon = findViewById(R.id.longitude_text_view);
        TextView accuracy = findViewById(R.id.accuracy_text_view);
        TextView altitude = findViewById(R.id.altitude_text_view);
        TextView addressText = findViewById(R.id.address_text_view);

        lat.setText("Latitude: "+ location.getLatitude());
        lon.setText("Longitude: " +location.getLongitude());
        accuracy.setText("Accuracy: " + location.getAccuracy());
        altitude.setText("Altitude: " + location.getAltitude());
        String address = "Could not find address:(";
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> listAddresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            if(listAddresses!=null && listAddresses.size()>0){
                address = "Address:\n";

                if(listAddresses.get(0).getThoroughfare() != null){
                    address += listAddresses.get(0).getThoroughfare() + "\n";
                }
                if(listAddresses.get(0).getLocality() != null){
                    address += listAddresses.get(0).getLocality() + " ";
                }
                if(listAddresses.get(0).getPostalCode() != null){
                    address += listAddresses.get(0).getPostalCode() + " ";
                }
                if(listAddresses.get(0).getAdminArea() != null){
                    address += listAddresses.get(0).getAdminArea();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        addressText.setText(address);

    }
}