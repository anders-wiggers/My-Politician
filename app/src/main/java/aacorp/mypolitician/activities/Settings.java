/**
 * @Author Alex Krogh Smythe
 * Settings class. Will be implemented later
 */

package aacorp.mypolitician.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import aacorp.mypolitician.R;
import aacorp.mypolitician.patterns.Database;

public class Settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().setTitle("Settings");  // provide compatibility to all the versions
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }

    public void resetMatchesAndStatistics(View view){
        Database.getInstance().clearUser();

    }

}
