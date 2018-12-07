package aacorp.mypolitician.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.List;

import aacorp.mypolitician.R;
import aacorp.mypolitician.framework.Party;
import aacorp.mypolitician.framework.Politician;
import aacorp.mypolitician.patterns.Database;
import aacorp.mypolitician.patterns.MoveData;


public class LikedPolitician extends Fragment {

    MoveData md;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_liked_politician, container, false);
        md = MoveData.getInstance();
        Politician politician = md.getPolitician();


        TextView name = view.findViewById(R.id.puNameBox);
        TextView party = view.findViewById(R.id.puPartyBox);

        CircularImageView profilePicture = view.findViewById(R.id.fullPolitician_pb);
        ImageView bannerPicture = view.findViewById(R.id.puBannerPic);

               List<Party> paries = Database.getInstance().getParties();

        int color = Color.BLACK;
        for(Party p : paries){
            if(politician.getParty().equals(p.getName())) color = p.getColor();
        }

        profilePicture.setShadowColor(color);

        Glide.with(getContext())
                .load(politician.getProfilePictureId())
                .into(profilePicture);

        Glide.with(getContext())
                .load(politician.getBannerId())
                .into(bannerPicture);


        name.setText(politician.getName());
        party.setText(politician.getParty());

        // Inflate the layout for this fragment
        return view;
    }
}
