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


    private Politician politician;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);

        politician = Database.getInstance().fetchRandomPolitician();
        ProgressBar pb = findViewById(R.id.progressBar);
        pb.setProgress(politician.getStrength().get("def").getPercent());

        TextView tw = findViewById(R.id.textView2);
        tw.setText(politician.getStrength().get("def").getText());

        expandableListView = (ExpandableListView) findViewById(R.id.eList);

        ExpanableListViewAdapter adapter = new ExpanableListViewAdapter(this,politician);
        expandableListView.setAdapter(adapter);
    }


}
