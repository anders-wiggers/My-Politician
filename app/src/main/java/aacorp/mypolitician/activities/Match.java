/**
 * Match is the main Activity
 *
 *  @author Anders Bille Wiggers
 *  for Introduction-to-Human-Computer-InteractionI course.
 *  Copyright (c) 2018.
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
import aacorp.mypolitician.patterns.Database;

public class Match extends Activity {
    ExpandableListView expandableListView;


    private Politician politician; //The politician on display

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);

        politician = Database.getInstance().fetchRandomPolitician(); //Set the current politician to a randomly fetched politician

        expandableListView = (ExpandableListView) findViewById(R.id.eList);

        ExpanableListViewAdapter adapter = new ExpanableListViewAdapter(this,politician); //Create custom adapter
        expandableListView.setAdapter(adapter); //Set the adapter for the listView.
    }


}
