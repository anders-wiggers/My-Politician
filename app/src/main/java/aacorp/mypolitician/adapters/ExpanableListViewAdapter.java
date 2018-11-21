/**
 *  Adapter for the ExpandableListView in Match
 *
 *  Handles generation of the ExpandelbeListView be extending the
 *  Base Expandable List Adapter and overriding the classes.
 *
 *  @author Anders Bille Wiggers
 *  for Introduction-to-Human-Computer-InteractionI course.
 *  Copyright (c) 2018
 *
 */

package aacorp.mypolitician.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;

import aacorp.mypolitician.R;
import aacorp.mypolitician.framework.Constants;
import aacorp.mypolitician.framework.Politician;
import aacorp.mypolitician.framework.Strength;

public class ExpanableListViewAdapter extends BaseExpandableListAdapter {

    private final Politician match; //The Politician

    private LayoutInflater inflater;


    String[] groupNames; //Names of the Dropdowns
    String[][] childNames; //Content of the Dropdowns

    Context context; //The context of the view.

    public ExpanableListViewAdapter(Context context, Politician match){
        this.context = context;
        this.match = match;

        groupNames = new String[match.getStrength().size()];
        childNames = new String[match.getStrength().size()][1];

        inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        int i=0;
        for(Strength s : match.getStrength().values()){         //retrieving the text and percent from the politicians strengths
            groupNames[i] = String.valueOf(s.getPercent());
            childNames[i][0] = s.getText();
            i++;
        }
    }

    @Override
    public int getGroupCount() {
        return groupNames.length;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return childNames[groupPosition].length;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groupNames[groupPosition];
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return childNames[groupPosition][childPosition];
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    /**
     * Generates a groupView for a giving position. By returning a View of a new progress bar.
     *
     * @return A progress bar with the appropriate strength as Progress.
     */
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View view, ViewGroup parent) {
        /*
        LinearLayout linearLayout = new LinearLayout(context);
        ProgressBar pb = new ProgressBar(context,null,android.R.attr.progressBarStyleHorizontal);
        pb.setProgress(Integer.parseInt(groupNames[groupPosition]));

        pb.getProgressDrawable().setColorFilter(Color.GREEN, android.graphics.PorterDuff.Mode.SRC_IN); //Setting color
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(600, 60);
        pb.setLayoutParams(params );
        */
        if(view == null)
        {
            view=inflater.inflate(R.layout.politician_bar, null);
        }

        //GET GROUP/TEAM ITEM
        //SET GROUP NAME
        ImageView img=(ImageView) view.findViewById(R.id.strengthImage);
        RoundCornerProgressBar pb = view.findViewById(R.id.strengthBar);
        pb.setProgress(Integer.parseInt(groupNames[groupPosition]));

        //SET IMAGE
        String icon = (String) match.getStrength().keySet().toArray()[groupPosition];
        if(icon.equals(Constants.DEFENCE_ICON)) img.setImageResource(R.drawable.def);
        if(icon.equals(Constants.WORLD_ICON)) img.setImageResource(R.drawable.wor);
        if(icon.equals(Constants.ENVIRONMENT_ICON)) img.setImageResource(R.drawable.env);
        if(icon.equals(Constants.MONEY_ICON)) img.setImageResource(R.drawable.mon);

        return view;
    }

    /**
     * method generates the textview that encapsulated the appropriate text to a given strength
     *
     * @return A textview with the politicians comment on his/her strength
     */
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final TextView textView = new TextView(context);
        textView.setText(childNames[groupPosition][childPosition]);
        return textView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
