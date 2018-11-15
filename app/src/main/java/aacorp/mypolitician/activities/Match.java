/*
 * Copyright (c) 2018.
 * @author Anders Bille Wiggers
 * for Introduction-to-Human-Computer-InteractionI course.
 *
 */

package aacorp.mypolitician.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.ExpandableListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import aacorp.mypolitician.R;
import aacorp.mypolitician.adapters.ExpanableListViewAdapter;
import aacorp.mypolitician.framework.Politician;
import aacorp.mypolitician.patterns.Database;

public class Match extends Activity {
    ExpandableListView expandableListView;


    private Politician politician; //The politician on display
    private aacorp.mypolitician.framework.User user;

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
        user.likedPoliticians.add(politician);
    }

    public void dislike(){
        fetchNewPolitician();
    }




}
