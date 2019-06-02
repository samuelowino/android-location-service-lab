package org.aplusscreators.android_locationservices_lab.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import org.aplusscreators.android_locationservices_lab.R;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private final int LOCATION_PERMMISSION_REQUEST_CODE = 1121;
    private FusedLocationProviderClient fusedLocationProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        boolean permissionGranted = checkPermissionGrantedStatus();

        if (!permissionGranted) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, LOCATION_PERMMISSION_REQUEST_CODE);
        } else {
            setUpLocationRequestTask();
        }

    }

    private void setUpLocationRequestTask() {
        Task getCurrentLocationTask = getLastKnowLocation();

        getCurrentLocationTask.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location == null)
                    return;

                Toast.makeText(getApplicationContext(),"Location request success",Toast.LENGTH_LONG).show();
                Log.w(TAG, "onSuccess: Lat: " + location.getLatitude() + " Lng: " + location.getLongitude() + ", speeed" + location.getSpeed());
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(),"location request failed",Toast.LENGTH_LONG).show();
                Log.w(TAG, "onFailure: Error " + e );
            }
        });
    }

    @SuppressLint("MissingPermission")
    private Task getLastKnowLocation() {
        Task task = fusedLocationProviderClient.getLastLocation();
        return task;
    }

    private void displayPermissionExplanationDialog() {
        //todo
    }

    private boolean checkPermissionGrantedStatus() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_DENIED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        == PackageManager.PERMISSION_DENIED) {

            return false;
        } else
            return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case LOCATION_PERMMISSION_REQUEST_CODE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getApplicationContext(), "Permission Granted", Toast.LENGTH_LONG).show();
                    setUpLocationRequestTask();
                } else
                    Toast.makeText(getApplicationContext(), "Permission Denied", Toast.LENGTH_LONG).show();
                break;
        }
    }
}
