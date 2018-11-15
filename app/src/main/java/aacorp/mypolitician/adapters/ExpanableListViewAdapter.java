/*
 * Copyright (c) 2018.
 * @author Anders Bille Wiggers
 * for Introduction-to-Human-Computer-InteractionI course.
 *
 */

package aacorp.mypolitician.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import aacorp.mypolitician.framework.Politician;
import aacorp.mypolitician.framework.Strength;

public class ExpanableListViewAdapter extends BaseExpandableListAdapter {

    private final Politician match;
    ProgressBar[] progressBars;

    String[] groupNames;
    String[][] childNames;

    Context context;

    public ExpanableListViewAdapter(Context context, Politician match){
        this.context = context;
        this.match = match;

        groupNames = new String[match.getStrength().size()];
        childNames = new String[match.getStrength().size()][1];

        int i=0;
        for(Strength s : match.getStrength().values()){
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
        return progressBars[groupPosition];
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

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View view, ViewGroup parent) {
        ProgressBar pb = new ProgressBar(context,null,android.R.attr.progressBarStyleHorizontal);
        pb.setProgress(Integer.parseInt(groupNames[groupPosition]));
        TextView textView = new TextView(context);
        textView.setText(groupNames[groupPosition]);
        textView.setTextSize(40);
        return pb;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final TextView textView = new TextView(context);
        textView.setText(childNames[groupPosition][childPosition]);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,textView.getText().toString(),Toast.LENGTH_LONG).show();
            }
        });


        return textView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
