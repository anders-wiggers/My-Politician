/**
 *  MatchList is a list of a users Matches
 *
 *  @author Anders Bille Wiggers
 *  for Introduction-to-Human-Computer-InteractionI course.
 *  Copyright (c) 2018.
 *
 */

package aacorp.mypolitician.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import aacorp.mypolitician.R;


public class MatchList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_list);
    }
}
