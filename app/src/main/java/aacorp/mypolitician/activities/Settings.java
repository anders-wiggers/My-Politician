/**
 * @Author Alex Krogh Smythe
 * Settings class. Will be implemented later
 */

package aacorp.mypolitician.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;

import aacorp.mypolitician.R;
import aacorp.mypolitician.patterns.Database;
import aacorp.mypolitician.patterns.MoveData;

public class Settings extends AppCompatActivity {
    private Switch localpol;
    private Database db;
    private FirebaseAuth mAuth;
    private AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        db = Database.getInstance(); //gets the db

        localpol = findViewById(R.id.localpoliticiansswitch);

        //set on checked change listener
        localpol.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!localpol.isChecked()) {
                    db.updateLocalPoliticianSetting(false);
                } else {
                    if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(Settings.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 101);
                    }
                    if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                        db.updateLocalPoliticianSetting(true);
                    }
                }
            }
        });

        //check the current local setting from the user and set the switch accordingly
        if(db.getUser().getLocalPoliticianSetting()){
            localpol.setChecked(true);
        } else {
            localpol.setChecked(false);
        }
        localpol.setTypeface(ResourcesCompat.getFont(this, R.font.avenirbook));
    }

    /**
     * button click method
     * opens the alertDialog which the user needs to conferm to reset settings
     * @param view current view
     */
    public void resetMatches(View view){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Settings.this);

        View child = getLayoutInflater().inflate(R.layout.reset_dialog, null);
        alertDialogBuilder.setView(child);

        alertDialog = alertDialogBuilder.create();

        alertDialog.show();
    }

    /**
     * resets the users current liked and seen politicians
     * @param view current view
     */
    public void resetApp(View view){
        alertDialog.dismiss();
        if(!MoveData.getInstance().hasReset()) {
            MoveData.getInstance().toggleHasReset();
        }
        Toast.makeText(this,"Account has been reset",Toast.LENGTH_SHORT).show();
        db.clearUser();
    }

    /**
     * button click method
     * opens alertDialog which the user need to conferm to logout
     * @param view current view
     */
    public void logoutDialog(View view){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Settings.this);

        View child = getLayoutInflater().inflate(R.layout.logout_dialog, null);
        alertDialogBuilder.setView(child);

        alertDialog = alertDialogBuilder.create();

        alertDialog.show();
    }

    /**
     * logs the user out of the application and send the user to the login activity
     * @param view current view
     */
    public void logout(View view){
        alertDialog.dismiss();
        mAuth.getInstance().signOut();
        LoginManager.getInstance().logOut();
        Intent intent = new Intent(this,LogIn.class);
        ActivityCompat.finishAffinity(this);
        startActivity(intent);

    }

    /**
     * button method to dismiss a alert
     * @param view current view
     */
    public void dismiss(View view){
        alertDialog.dismiss();
    }

    /**
     * button click method
     * opens the terms and condition for the user.
     * @param view
     */
    public void terms(View view){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Settings.this);

        View child = getLayoutInflater().inflate(R.layout.tos, null);
        alertDialogBuilder.setView(child);

        alertDialog = alertDialogBuilder.create();

        alertDialog.show();
    }


}
