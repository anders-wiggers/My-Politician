/**
 *  MatchList is a list of a users Matches
 *
 *  @author Anders Bille Wiggers
 *  for Introduction-to-Human-Computer-InteractionI course.
 *  Copyright (c) 2018.
 *
 */

package aacorp.mypolitician.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import aacorp.mypolitician.Implementation.PoliticianImpl;
import aacorp.mypolitician.R;
import aacorp.mypolitician.adapters.LikedPoliticianListAdapter;
import aacorp.mypolitician.patterns.Database;


public class MatchList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_list);

        updateView();
    }

    public void updateView(){
        ArrayList<PoliticianImpl> likedPoliticians = new ArrayList<>();
        List<PoliticianImpl> prebList = Database.getInstance().getPoliticiansFixed();
        List<String> likedFromUser = Database.getInstance().getUser().getLikedPoliticians();

        for(PoliticianImpl p : prebList){
            for(String s : likedFromUser){
                if(p.getId().equals(s)){
                    likedPoliticians.add(p);
                }
            }
        }

        LikedPoliticianListAdapter adapter = new LikedPoliticianListAdapter(this,R.layout.list_of_matches,likedPoliticians);
        ListView likedListView = findViewById(R.id.listOfMatches);
        likedListView.setAdapter(adapter);
    }
}
