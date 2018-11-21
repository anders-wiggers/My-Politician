/**
 *  Class is the activity for the main match screen. fethces new politican
 *  from Database singleton. and present the data to the user
 *
 * @author Anders Bille Wiggers
 * @author Alex Krogh Smythe
 * for Introduction-to-Human-Computer-InteractionI course.
 * Copyright (c) 2018
 *
 */

package aacorp.mypolitician.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ExpandableListView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

import aacorp.mypolitician.R;
import aacorp.mypolitician.adapters.ExpanableListViewAdapter;
import aacorp.mypolitician.framework.Politician;
import aacorp.mypolitician.framework.User;
import aacorp.mypolitician.patterns.Database;

public class Match extends Activity {
    ExpandableListView expandableListView;
    private Database db = Database.getInstance();
    private Politician politician; //The politician on display
    private User user;
    private Timer timer = new Timer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);
        waitForDatabase();
    }

    private void updateView(){
        expandableListView = (ExpandableListView) findViewById(R.id.eList);
        ExpanableListViewAdapter adapter = new ExpanableListViewAdapter(this,politician); //TODO fix the adapter
        expandableListView.setAdapter(adapter);
    }

    //TODO GRACEFULLY HANDLE OUT OF POLITICIANS :)
    public void fetchNewPolitician(){
        if(db.hasNextPolitician()) {
            politician = db.fetchRandomPolitician(); //Set the current politician to a randomly fetched politician
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    updateView();
                }
            });
        }
        else{
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(Match.this, "we're out of politicians", Toast.LENGTH_LONG).show();
                }
            });
        }
    }


    public void like(View view){
        //user.likedPoliticians.add(politician);
        if(db.hasNextPolitician()){
            db.addLikeToUser(politician.getId());
        }
        //TODO add full politician view when liked
    }

    public void dislike(View view){
        if(db.hasNextPolitician()) {
            db.addSeenToUser(politician.getId());
        }
        fetchNewPolitician();
    }

    public void waitForDatabase(){
        final TimerTask task = new TimerTask() {
            @Override
            public void run() {
                if(db.isAppReady()){
                 fetchNewPolitician();
                 timer.cancel();
                 timer.purge();
                }
            }
        };


        long delay = 0;
        long intevalPeriod = 1 * 1000;


        // schedules the task to be run in an interval
        timer.scheduleAtFixedRate(task, delay, intevalPeriod);
    }




}
