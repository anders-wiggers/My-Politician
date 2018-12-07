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

    public LikedPoliticianListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<PoliticianImpl> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(resource,parent,false);

        TextView name = convertView.findViewById(R.id.listPoliticianName);
        name.setText(getItem(position).getName());

        CircularImageView profilePicture = convertView.findViewById(R.id.fullPolitician_pb);

        List<Party> paries = Database.getInstance().getParties();

        int color = Color.BLACK;
        for(Party p : paries){
            if(getItem(position).getParty().equals(p.getName())) color = p.getColor();
        }

        profilePicture.setShadowColor(color);

        Glide.with(getContext())
                .load(getItem(position).getProfilePictureId())
                .into(profilePicture);

        ImageView deleteButton = convertView.findViewById(R.id.removePolitician);
        final Button confirmButton = convertView.findViewById(R.id.confirmationDelete);

        convertView.findViewById(R.id.constraintLayout2).setClickable(true);

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
