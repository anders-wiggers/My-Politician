package aacorp.mypolitician.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import aacorp.mypolitician.Implementation.PoliticianImpl;
import aacorp.mypolitician.R;

public class LikedPoliticianListAdapter extends ArrayAdapter<PoliticianImpl> {

    private final int resource;
    private Context context;

    public LikedPoliticianListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<PoliticianImpl> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {


        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(resource,parent,false);

        ((TextView) convertView.findViewById(R.id.listPoliticianName)).setText(getItem(position).getName());

        return convertView;
    }
}
