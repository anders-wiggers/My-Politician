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

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.util.Timer;
import java.util.TimerTask;

import aacorp.mypolitician.R;
import aacorp.mypolitician.adapters.ExpanableListViewAdapter;
import aacorp.mypolitician.fragments.LikedPolitician;
import aacorp.mypolitician.framework.OnSwipeTouchListener;
import aacorp.mypolitician.framework.Politician;
import aacorp.mypolitician.framework.User;
import aacorp.mypolitician.patterns.Database;
import aacorp.mypolitician.patterns.MoveData;

public class Match extends AppCompatActivity {
    private Politician prevPolitician = null;
    private ExpandableListView expandableListView;
    private Database db = Database.getInstance();
    private Politician politician; //The politician on display
    private User user;
    private Timer timer = new Timer();
    private ImageButton removePreview;
    private FrameLayout frag_container;
    private ImageView politician_page;
    private ImageView nextPoliticianPage;
    private ConstraintLayout constraintLayout;
    private ConstraintLayout loading;
    private ConstraintLayout outOfPoliticians;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);
        waitForDatabase(); //Start time to check for the database

        //set views
        loading = findViewById(R.id.loading);
        outOfPoliticians = findViewById(R.id.outOfPoliticians);
        removePreview = findViewById(R.id.removePreview);
        frag_container = findViewById(R.id.fragment_container);
        politician_page = findViewById(R.id.politician_page);
        nextPoliticianPage = findViewById(R.id.politician_page_2);

        //Set SwipeSwipeListerner
        constraintLayout = findViewById(R.id.relativeLayout);
        OnSwipeTouchListener onSwipeTouchListener = new OnSwipeTouchListener(this) {
            @Override
            public void onSwipeLeft() {
                Intent intent = new Intent(Match.this, Statistics.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
            @Override
            public void onSwipeRight() {
                Intent intent = new Intent(Match.this, MatchList.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }

            @Override
            public void onSwipeBottom() {
                Intent intent = new Intent(Match.this, Settings.class);
                startActivity(intent);
            }


        };
        constraintLayout.setOnTouchListener(onSwipeTouchListener);
    }

    /**
     * Fetches a new politician on resume
     */
    @Override
    protected void onResume() {
        super.onResume();
        if(MoveData.getInstance().hasReset()){
            fetchNewPolitician();
            MoveData.getInstance().toggleHasReset();
        }
    }

    /**
     * updates the match view
     */
    private void updateView(){
        if(prevPolitician==null){
            (findViewById(R.id.redo_button)).setVisibility(View.INVISIBLE);
        }
        else {
            (findViewById(R.id.redo_button)).setVisibility(View.VISIBLE);
        }
        expandableListView = (ExpandableListView) findViewById(R.id.eList);
        ExpanableListViewAdapter adapter = new ExpanableListViewAdapter(this,politician);
        expandableListView.setAdapter(adapter);
        loading.setVisibility(View.GONE);
    }

    public void fetchNewPolitician() {
        if (db.hasNextPolitician()) {
            politician = db.fetchRandomPolitician(); //Set the current politician to a randomly fetched politician
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    updateView();
                }
            });
            outOfPoliticians.setVisibility(View.GONE);
        } else {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    outOfPoliticians.setVisibility(View.VISIBLE);
                }
            });
        }
        }

    /**
     * Like a the current politician. Adds the current pol to the
     * user in the database
     * @param view
     */
    public void like(View view){
        //user.likedPoliticians.add(politician);
        if(db.hasNextPolitician()){
            db.addLikeToUser(politician.getId());
            MoveData.getInstance().setPolitician(politician);
            loadFragment();
        }

    }

    /**
     * Removes the preview fragment
     * @param view
     */
    public void removePreview(View view){
        removePreview.setVisibility(View.GONE);
        fetchNewPolitician();
        Animation animation = AnimationUtils.loadAnimation(this,R.anim.animation_move_frag);
        frag_container.startAnimation(animation);
        frag_container.setVisibility(View.GONE);
    }


    /**
     * dislikes the current politicians. Add the current pol to the
     * user in the database
     * @param view
     */
    public void dislike(View view){
        if(db.hasNextPolitician()) {
            prevPolitician = politician;
            db.addSeenToUser(politician.getId());
        }
        Animation animation = AnimationUtils.loadAnimation(this,R.anim.remove_match_animation);
        politician_page.startAnimation(animation);

        Animation animation1 = AnimationUtils.loadAnimation(this,R.anim.remove_match_next);
        nextPoliticianPage.startAnimation(animation1);
        fetchNewPolitician();
    }

    /**
     * fetch the last politicians and remove the politician from seen
     * @param view
     */
    public void redoDislike(View view){
        politician = prevPolitician;
        prevPolitician = null;
        db.removeSeenFromUser(politician.getId());
        updateView();
    }

    /**
     * Waits for the database before fetching a politician, avoids nullpointer
     */
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

    /**
     * loads the like politiicans to the fragment.
     */
    private void loadFragment(){
        LikedPolitician myf = new LikedPolitician();

        Bundle bundle = new Bundle();
        bundle.putString("name", politician.getName());
        bundle.putString("party",politician.getParty());

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.fragment_container, myf);
        transaction.commit();
        frag_container.setVisibility(View.VISIBLE);
        removePreview.setVisibility(View.VISIBLE);
    }

    /**
     * opens settings menu
     * @param view
     */
    public void Settings(View view){
        Intent intent = new Intent(this,Settings.class);
        startActivity(intent);
    }

}
