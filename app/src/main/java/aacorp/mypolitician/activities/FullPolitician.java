/**
 * Handles the view of the full politician
 * Copyright (c) 2018.
 * @author Anders Bille Wiggers
 * for Introduction-to-Human-Computer-InteractionI course.
 *
 */
package aacorp.mypolitician.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.List;

import aacorp.mypolitician.R;
import aacorp.mypolitician.adapters.ExpanableListViewAdapter;
import aacorp.mypolitician.framework.Party;
import aacorp.mypolitician.framework.Politician;
import aacorp.mypolitician.patterns.Database;
import aacorp.mypolitician.patterns.MoveData;

public class FullPolitician extends AppCompatActivity {
    private Politician politician;
    private ExpandableListView expandableListView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().setTitle("Your Matches");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_politician);
        politician = MoveData.getInstance().getPolitician(); //fetches politician from moveData
        updateUI(); //updates UI
    }

    /**
     * Update the ui to when all the data is ready
     */
    private void updateUI(){
        expandableListView = (ExpandableListView) findViewById(R.id.fullPoliticianList);
        ExpanableListViewAdapter adapter = new ExpanableListViewAdapter(this,politician);
        expandableListView.setAdapter(adapter);

        //finds the names textview
        TextView name = findViewById(R.id.fullPolitician);
        TextView party = findViewById(R.id.fullParty);

        //finds the circular image view and banner view
        CircularImageView profilePicture = findViewById(R.id.fullPolitician_pb);
        ImageView bannerPicture = findViewById(R.id.fullCover);

        //finds the list of parties
        List<Party> parties = Database.getInstance().getParties();

        int color = Color.BLACK;
        for(Party p : parties){
            if(politician.getParty().equals(p.getName())) color = p.getColor();
        }

        profilePicture.setShadowColor(color);

        //load image with glide
        Glide.with(this)
                .load(politician.getProfilePictureId())
                .into(profilePicture);

        Glide.with(this)
                .load(politician.getBannerId())
                .into(bannerPicture);


        //set name and party
        name.setText(politician.getName());
        party.setText(politician.getParty());

    }

}
