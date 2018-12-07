/**
 *  Adapter for the LikedPoliticans in Match List
 *
 *  Handles generation of the ListView in match list class.
 *
 *  @author Anders Bille Wiggers
 *  for Introduction-to-Human-Computer-InteractionI course.
 *  Copyright (c) 2018
 *
 */

package aacorp.mypolitician.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.ArrayList;
import java.util.List;

import aacorp.mypolitician.Implementation.PoliticianImpl;
import aacorp.mypolitician.R;
import aacorp.mypolitician.activities.FullPolitician;
import aacorp.mypolitician.activities.MatchList;
import aacorp.mypolitician.framework.Party;
import aacorp.mypolitician.patterns.Database;
import aacorp.mypolitician.patterns.MoveData;

public class LikedPoliticianListAdapter extends ArrayAdapter<PoliticianImpl> {

    private final int resource;
    private Context context;
    private Database db = Database.getInstance();

    /**
     * Constructor to the adapter
     * @param context the current context
     * @param resource current resource
     * @param objects list of politicians
     */
    public LikedPoliticianListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<PoliticianImpl> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
    }


    /**
     * Set the view for the each Liked politician
     * @param position the current position
     * @param convertView a view
     * @param parent the group parent
     * @return view with a liked politician
     */
    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(resource,parent,false);//get the view

        //find name textView
        TextView name = convertView.findViewById(R.id.listPoliticianName);
        //set name textView
        name.setText(getItem(position).getName());

        //find Circular ImageView
        CircularImageView profilePicture = convertView.findViewById(R.id.fullPolitician_pb);

        //import list of parties from database
        List<Party> paries = Database.getInstance().getParties();

        //sort for party color
        int color = Color.BLACK;
        for(Party p : paries){
            if(getItem(position).getParty().equals(p.getName())) color = p.getColor();
        }

        //set the shadow color to the party color
        profilePicture.setShadowColor(color);

        //load image with glide from firebase
        Glide.with(getContext())
                .load(getItem(position).getProfilePictureId())
                .into(profilePicture);

        //find deletebutton and confirm button
        ImageView deleteButton = convertView.findViewById(R.id.removePolitician);
        final Button confirmButton = convertView.findViewById(R.id.confirmationDelete);

        convertView.findViewById(R.id.constraintLayout2).setClickable(true);

        //Set onClickListerners for buttons
        convertView.findViewById(R.id.constraintLayout2).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                confirmButton.setVisibility(View.VISIBLE);
                Animation animation = AnimationUtils.loadAnimation(context,R.anim.animation_left);
                confirmButton.startAnimation(animation);
            }
        });

        deleteButton.setClickable(true);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmButton.setVisibility(View.VISIBLE);
                Animation animation = AnimationUtils.loadAnimation(context,R.anim.animation_left);
                confirmButton.startAnimation(animation);
            }
        });

        confirmButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                db.removeLikeFromUser(getItem(position).getId());
                if(context instanceof MatchList){
                    ((MatchList)context).updateView();
                }
            }
        });

        convertView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(confirmButton.getVisibility()==View.VISIBLE){
                    Animation animation = AnimationUtils.loadAnimation(context,R.anim.animation_right);
                    confirmButton.startAnimation(animation);
                    confirmButton.setVisibility(View.GONE);
                }
                else {
                    MoveData.getInstance().setPolitician(getItem(position));
                    Intent intent = new Intent(context, FullPolitician.class);
                    context.startActivity(intent);
                }

            }
        });


        return convertView;
    }
}
