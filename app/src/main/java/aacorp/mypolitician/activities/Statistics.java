/**
 * @Author Alex Krogh Smythe
 * This is the statistics class. After implementing our main features of
 * fetching and like/disliking the politicians we will implement a way to show statistics
 *
 */

package aacorp.mypolitician.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

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
import aacorp.mypolitician.framework.OnSwipeTouchListener;
import aacorp.mypolitician.framework.Party;
import aacorp.mypolitician.framework.Politician;
import aacorp.mypolitician.patterns.Database;

public class Statistics extends AppCompatActivity{
    private Database db;
    private PieChart pieChart;
    private PieChart pieChartBlock;
    private List<PieEntry> pieEntriesParty;
    private List<PieEntry> pieEntriesBlock;
    private Map<String,Integer> partyPercentages;
    private Map<String, Integer> blockPercentages;
    private RelativeLayout relativeLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        db = Database.getInstance();
        partyPercentages = new HashMap<>();
        blockPercentages = new HashMap<>();
        isAllowedAccess();

        relativeLayout = findViewById(R.id.relativeLayoutStats);
        OnSwipeTouchListener onSwipeTouchListener = new OnSwipeTouchListener(this) {
            @Override
            public void onSwipeRight() {
                Intent intent = new Intent(Statistics.this, Match.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        };
        relativeLayout.setOnTouchListener(onSwipeTouchListener);
        Log.e("layout is set","yeee");
    }

    public void isAllowedAccess(){
        checkForStatistics();
        if (checkForStatistics()){
            createAndInitializePieChartOfParties();
            createAndInitializePieChartOfBlocks();
            makeAllTextInvisible();
        }
        else {
            Toast.makeText(Statistics.this, "You have to match with a politician before generating statistics", Toast.LENGTH_LONG).show();
            makeAllChartsInvisible();

        }
    }

    public void makeAllTextInvisible(){
        //Make error text invisible
        findViewById(R.id.textView3).setVisibility(View.GONE);
        findViewById(R.id.textView4).setVisibility(View.GONE);
        findViewById(R.id.textView5).setVisibility(View.GONE);
    }

    public void makeAllChartsInvisible(){
        //Make charts invisible
        findViewById(R.id.piechartblock).setVisibility(View.GONE);
        findViewById(R.id.piechart).setVisibility(View.GONE);

        //Make error text visible
        findViewById(R.id.textView3).setVisibility(View.VISIBLE);
        findViewById(R.id.textView4).setVisibility(View.VISIBLE);
        findViewById(R.id.textView5).setVisibility(View.VISIBLE);

    }

    public boolean checkForStatistics(){
        if(db.getUser().getLikedPoliticians().size() == 0){
            return false;
        }
        return true;
    }

    private void createAndInitializePieChartOfBlocks(){
        //Initialize pieChart
        pieChartBlock = findViewById(R.id.piechartblock);
        pieChartBlock.setUsePercentValues(true);
        pieChartBlock.getDescription().setEnabled(false);
        pieChartBlock.setExtraOffsets(5,10,5,5);
        pieChartBlock.setDragDecelerationFrictionCoef(0.95f);
        pieChartBlock.setDrawHoleEnabled(true);
        pieChartBlock.setHoleColor(Color.WHITE);
        pieChartBlock.setTransparentCircleRadius(61f);

        //Add entries
        this.pieEntriesBlock = new ArrayList<>();
        addPartyBlockEntriesToPie();

        PieDataSet pieDataSet = new PieDataSet(pieEntriesBlock, "Distribution of blocks");
        pieDataSet.setSliceSpace(3f);
        pieDataSet.setSelectionShift(5f);
        pieDataSet.setColors(new int[] {Color.BLUE,Color.RED});
        PieData pieData = new PieData(pieDataSet);
        pieData.setValueTextSize(10f);
        pieData.setValueTextColor(Color.YELLOW);
        pieChartBlock.setData(pieData);
    }

    private void createAndInitializePieChartOfParties(){
        //Initialize pieChart
        pieChart = findViewById(R.id.piechart);
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

    private void addPartyBlockEntriesToPie(){
        blockPercentages.clear();
        addPartyBlockToPercentage();
        for (String key : blockPercentages.keySet()){
            pieEntriesBlock.add(new PieEntry(blockPercentages.get(key),key));
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right); //Swipe transistion on back

    }

    public void addPartyBlockToPercentage() {
        List<String> politicianIDs = new ArrayList<>();
        List<Party> partyIDs = new ArrayList<>();
        Iterator<PoliticianImpl> i = db.getPoliticiansFixed().iterator();
        politicianIDs.addAll(db.getUser().getLikedPoliticians());
        partyIDs.addAll(db.getParties());

        while (i.hasNext()) {
            Politician p = i.next();
            for (String id : politicianIDs) {
                if (id.equals(p.getId())) {
                    for (Party party : partyIDs){
                        if(party.getName().equals(p.getParty())){
                            if(!blockPercentages.containsKey(party.getColorOfBlock())){
                                blockPercentages.put(party.getColorOfBlock(),1);
                            }
                            else {
                                blockPercentages.put(party.getColorOfBlock(),blockPercentages.get(party.getColorOfBlock())+1);
                            }
                           /* if (blockPercentages.containsKey(party.getColorOfBlock())){
                                blockPercentages.put(party.getColorOfBlock(),blockPercentages.get(party.getColorOfBlock())+1);
                            }

                            else {
                                blockPercentages.put(party.getColorOfBlock(),1);
                            }*/
                        }
                    }
                }
            }
        }
    }

    public void addPartyEntriesToPercentage() {
        List<String> politicianIDs = new ArrayList<>();
        Iterator<PoliticianImpl> i = db.getPoliticiansFixed().iterator();
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

