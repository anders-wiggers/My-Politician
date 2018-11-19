/**
 *
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
import android.view.Window;
import android.widget.ExpandableListView;

import aacorp.mypolitician.R;
import aacorp.mypolitician.adapters.ExpanableListViewAdapter;
import aacorp.mypolitician.framework.Politician;
import aacorp.mypolitician.framework.User;
import aacorp.mypolitician.patterns.Database;

public class Match extends Activity {
    ExpandableListView expandableListView;


    private Politician politician; //The politician on display
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);

        politician = Database.getInstance().fetchRandomPolitician(); //Set the current politician to a randomly fetched politician
        politician = Database.getInstance().fetchRandomPolitician();

        expandableListView = (ExpandableListView) findViewById(R.id.eList);

        ExpanableListViewAdapter adapter = new ExpanableListViewAdapter(this,politician);
        expandableListView.setAdapter(adapter);
    }

    public void fetchNewPolitician(){
        politician = Database.getInstance().fetchRandomPolitician();
    }


    public void like(Politician politician){
        //user.likedPoliticians.add(politician);
        //TODO fix liked politician
    }

    public void dislike(){
        fetchNewPolitician();
        //TODO find politician that has not been shown before
    }




}
