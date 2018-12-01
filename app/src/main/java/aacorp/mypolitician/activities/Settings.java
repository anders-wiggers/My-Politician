/**
 * @Author Alex Krogh Smythe
 * Settings class. Will be implemented later
 */

package aacorp.mypolitician.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import aacorp.mypolitician.R;
import aacorp.mypolitician.patterns.Database;

public class Settings extends AppCompatActivity {
    Switch localpol;
    private Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        db = Database.getInstance();

        localpol = (Switch) findViewById(R.id.localpoliticiansswitch);
        localpol.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    Toast.makeText(getBaseContext(), "Local politicians only", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(getBaseContext(), "All politicians available", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}