/**
 * @Author Alex Krogh Smythe
 * Settings class. Will be implemented later
 */

package aacorp.mypolitician.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Switch;

import com.facebook.login.LoginManager;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;

import aacorp.mypolitician.R;
import aacorp.mypolitician.patterns.Database;

public class Settings extends AppCompatActivity {
    private Switch localpol;
    private Database db;
    private GoogleApiClient mClient;
    private boolean onlyLocalPoliticians;
    private boolean misEnabled;
    private FirebaseAuth mAuth;


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
        localpol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (localpol.isChecked()) {
                    localpol.toggle();
                    db.updateLocalPoliticianSetting(false);
                } else {
                    localpol.toggle();
                    db.updateLocalPoliticianSetting(true);
                }
            }
        });

        if(db.getUser().getLocalPoliticianSetting()){
            localpol.setChecked(true);
        } else {
            localpol.setChecked(false);
        }
    }



    public void resetMatches(View view){
        db.clearUser();
    }

    public void logout(View view){
        mAuth.getInstance().signOut();
        LoginManager.getInstance().logOut();
        Intent intent = new Intent(this,LogIn.class);
        ActivityCompat.finishAffinity(this);
        startActivity(intent);

    }


}
