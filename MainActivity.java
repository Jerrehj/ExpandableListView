package com.example.solar.expandablelistview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;
    List<String> expandableListTitle;
    HashMap<String, List<String>> expandableListDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        expandableListView = (ExpandableListView) findViewById(R.id.expandableListView);
        expandableListDetail = ExpandableListViewData.getData();
        expandableListTitle = new ArrayList<String>(expandableListDetail.keySet());
        expandableListAdapter = new CustomExpandableListview(this, expandableListTitle, expandableListDetail);
        expandableListView.setAdapter(expandableListAdapter);

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                Toast.makeText(
                        getApplicationContext(),
                        expandableListTitle.get(groupPosition)
                                + " -> "
                                + expandableListDetail.get(
                                expandableListTitle.get(groupPosition)).get(
                                childPosition), Toast.LENGTH_SHORT
                ).show();
                return false;
            }
        });
    }



    public static class ExpandableListViewData {
        public static HashMap<String, List<String>> getData() {
            HashMap<String, List<String>> expandableListDetail = new HashMap<String, List<String>>();

            List<String> Xbox = new ArrayList<String>();
            Xbox.add("Halo");
            Xbox.add("Gears of War");
            Xbox.add("Cuphead");
            Xbox.add("Tomb Raider");
            Xbox.add("Sea of Thieves");

            List<String> Ps4 = new ArrayList<String>();
            Ps4.add("God of War");
            Ps4.add("Spiderman");
            Ps4.add("horizon Zero Dawn");
            Ps4.add("The Last of Us");
            Ps4.add("Bloodbourne");

            List<String> Switch = new ArrayList<String>();
            Switch.add("Let's Go Pikachu/Eevee");
            Switch.add("Zelda Breath of the Wild");
            Switch.add("Splatoon");
            Switch.add("Super Mario Odyssey");
            Switch.add("Bayonetta 2");

            expandableListDetail.put("Xbox Exclusives", Xbox);
            expandableListDetail.put("Ps4 Exclusives", Ps4);
            expandableListDetail.put("Switch Exclusives", Switch);
            return expandableListDetail;
        }
    }
}
