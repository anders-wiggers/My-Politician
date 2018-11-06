package aacorp.mypolitician.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import aacorp.mypolitician.R;

public class Match extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);

        Intent myIntent = new Intent(this, LogInActivity.class);
        this.startActivity(myIntent);

    }
}
