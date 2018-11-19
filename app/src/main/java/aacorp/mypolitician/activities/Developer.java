/**
 *  Developer class NOT FOR user interaction
 *
 * @author Anders Bille Wiggers
 * @author Alex Krogh Smythe
 * for Introduction-to-Human-Computer-InteractionI course.
 * Copyright (c) 2018
 *
 */

package aacorp.mypolitician.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import aacorp.mypolitician.Implementation.PoliticianImpl;
import aacorp.mypolitician.R;
import aacorp.mypolitician.patterns.Database;

public class Developer extends AppCompatActivity {
    private String log = "MP developer";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_developer);
        Database.getInstance();
    }

    public void MatchList(View view){
        Intent intent = new Intent(this,MatchList.class);
        startActivity(intent);
    }

    public void Login(View view){
        Intent intent = new Intent(this,LogIn.class);
        startActivity(intent);
    }

    public void Settings(View view){
        Intent intent = new Intent(this,Settings.class);
        startActivity(intent);
    }

    public void match(View view){
        Intent intent = new Intent(this, aacorp.mypolitician.activities.Match.class);
        startActivity(intent);
    }

    public void debug(View view){
        TextView textView = findViewById(R.id.textView);


        PoliticianImpl politician = (PoliticianImpl) Database.getInstance().fetchDummy();
        textView.setText(Database.getInstance().fetchRandomPolitician().getName());


        Log.e(log,politician.getName()+ " has following strenght: " + politician.getStrength().get("def").getPercent());


        Database.getInstance().createPolitician();

    }
}
