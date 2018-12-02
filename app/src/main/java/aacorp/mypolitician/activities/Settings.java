/**
 * @Author Alex Krogh Smythe
 * Settings class. Will be implemented later
 */

package aacorp.mypolitician.activities;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;

import java.util.ArrayList;
import java.util.List;

import aacorp.mypolitician.R;
import aacorp.mypolitician.framework.Geofencing;
import aacorp.mypolitician.patterns.Database;

public class Settings extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    Switch localpol;
    private Database db;
    private GoogleApiClient mClient;
    private Geofencing mGeofencing;
    private boolean onlyLocalPoliticians;
    private boolean misEnabled;

    //Constants
    public static final String TAG = Settings.class.getSimpleName();
    private static final int PERMISSIONS_REQUEST_FINE_LOCATION = 111;
    private static final int PLACE_PICKER_REQUEST = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        db = Database.getInstance();

        localpol = (Switch) findViewById(R.id.localpoliticiansswitch);
        misEnabled = getPreferences(MODE_PRIVATE).getBoolean("EnabledSetting", false);
        localpol.setChecked(misEnabled);
        localpol.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                SharedPreferences.Editor editor = getPreferences(MODE_PRIVATE).edit();
                editor.putBoolean("EnabledSetting", isChecked);
                misEnabled = isChecked;
                editor.commit();

                if (isChecked) {
                    mGeofencing.registerAllGeofences();
                    Toast.makeText(getBaseContext(), "Local politicians only", Toast.LENGTH_SHORT).show();
                    onlyLocalPoliticians = true;

                } else {
                    mGeofencing.unRegisterAllGeofences();
                    Toast.makeText(getBaseContext(), "All politicians available", Toast.LENGTH_SHORT).show();
                    onlyLocalPoliticians = false;
                }
            }
        });

        mClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .addApi(Places.GEO_DATA_API)
                .enableAutoManage(this,this)
                .build();

        mGeofencing = new Geofencing(this,mClient);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.i(TAG, "API Client connection Successful!");
    }

    @Override
    public void onConnectionSuspended(int cause) {
        Log.i(TAG, "API Client connection suspended");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.e(TAG, "API Client Connection Failed");
    }
}