package aacorp.mypolitician.adapters;

import android.content.Context;
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

import java.util.ArrayList;

import aacorp.mypolitician.Implementation.PoliticianImpl;
import aacorp.mypolitician.R;
import aacorp.mypolitician.activities.MatchList;
import aacorp.mypolitician.patterns.Database;

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

        ImageView deleteButton = convertView.findViewById(R.id.removePolitician);
        final Button confirmButton = convertView.findViewById(R.id.confirmationDelete);

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

            }
        });

        return convertView;
    }
}
