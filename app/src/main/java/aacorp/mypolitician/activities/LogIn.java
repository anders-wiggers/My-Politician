/**
 * @Author Alex Krogh Smythe
 * This is the implementation of the login functionality with Facebook. The correlating XML layout file is the only one i have
 * spent time on.
 */

package aacorp.mypolitician.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;

import aacorp.mypolitician.R;
import aacorp.mypolitician.patterns.Database;


public class LogIn extends Activity {
    private CallbackManager mCallbackManager;
    private static final String TAG = "FACELOG";
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private Button mFacebookBtn;
    private Database db = Database.getInstance();
    private FusedLocationProviderClient mFusedLocationClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        //Initialize firebase auth
        mAuth = FirebaseAuth.getInstance();

        //Create instance of fusedLocationProvider
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        // Initialize Facebook Login button
        mCallbackManager = CallbackManager.Factory.create();
        mFacebookBtn = (Button) findViewById(R.id.facebooklogin);
        mFacebookBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mFacebookBtn.setEnabled(false);

                LoginManager.getInstance().logInWithReadPermissions(LogIn.this, Arrays.asList("email", "public_profile"));
                LoginManager.getInstance().registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        Log.d(TAG, "facebook:onSuccess:" + loginResult);
                        handleFacebookAccessToken(loginResult.getAccessToken());
                    }

                    @Override
                    public void onCancel() {
                        Log.d(TAG, "facebook:onCancel");
                        // ...
                    }

                    @Override
                    public void onError(FacebookException error) {
                        Log.d(TAG, "facebook:onError", error);
                        // ...
                    }
                });
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();


        if (currentUser != null) {
            updateUI(currentUser);
        }
    }

    //Send user to next page if the login is successful
    public void updateUI(FirebaseUser user){
        Toast.makeText(LogIn.this, "You are logged in", Toast.LENGTH_LONG).show();

        db.setUser(user);

        Intent MatchIntent = new Intent(LogIn.this, Match.class);
        startActivity(MatchIntent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Pass the activity result back to the Facebook SDK
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }


    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();

                            mFacebookBtn.setEnabled(true);

                            updateUI(user);

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(LogIn.this, "Authentication failed.", Toast.LENGTH_SHORT).show();

                            mFacebookBtn.setEnabled(true);
                        }

                    }
                });
    }
}
