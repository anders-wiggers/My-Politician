/**
 * @Author Alex Krogh Smythe
 * This is the statistics class. After implementing our main features of
 * fetching and like/disliking the politicians we will implement a way to show statistics
 *
 */

package aacorp.mypolitician.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import aacorp.mypolitician.Implementation.PoliticianImpl;
import aacorp.mypolitician.R;
import aacorp.mypolitician.framework.Politician;
import aacorp.mypolitician.patterns.Database;

public class Statistics extends AppCompatActivity{
    Database db;
    PieChart pieChart;
    List<PieEntry> pieEntriesParty;
    Map<String,Integer> partyPercentages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        db = Database.getInstance();
        partyPercentages = new HashMap<>();

        //Initialize pieChart
        pieChart = (PieChart) findViewById(R.id.piechart);
        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(5,10,5,5);
        pieChart.setDragDecelerationFrictionCoef(0.95f);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(Color.WHITE);
        pieChart.setTransparentCircleRadius(61f);

        //Add entries
        this.pieEntriesParty = new ArrayList<>();
        addPartyEntriesToPie();
        PieDataSet pieDataSet = new PieDataSet(pieEntriesParty, "Party distribution");
        pieDataSet.setSliceSpace(3f);
        pieDataSet.setSelectionShift(5f);
        pieDataSet.setColors(ColorTemplate.LIBERTY_COLORS);
        PieData pieData = new PieData(pieDataSet);
        pieData.setValueTextSize(10f);
        pieData.setValueTextColor(Color.YELLOW);
        pieChart.setData(pieData);

    }

    private void addPartyEntriesToPie() {
        partyPercentages.clear();
        addPartyEntriesToPercentage();
        for (String key : partyPercentages.keySet()){
            pieEntriesParty.add(new PieEntry(partyPercentages.get(key),key));
        }
    }

    public void addPartyEntriesToPercentage() {
        List<String> politicianIDs = new ArrayList<>();
        Iterator<PoliticianImpl> i = (Iterator<PoliticianImpl>) db.getPoliticians();
        politicianIDs.addAll(db.getUser().getLikedPoliticians());
        while (i.hasNext()) {
            Politician p = i.next();
            for (String id : politicianIDs) {
                if (id.equals(p.getId())){
                    if(partyPercentages.containsKey(p.getParty())){
                        partyPercentages.put(p.getParty(),partyPercentages.get(p.getParty())+1);
                    }
                    else {
                        partyPercentages.put(p.getParty(),1);
                    }
                }
            }
        }
    }

}

