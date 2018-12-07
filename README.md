# ExpandableListView
## **Synopsis** 
Hello this blog will serve as a helpful guide in under standing the functionality, use, history and how to create an ExpandableListView of your own. expandable list views are a type of view that shows items in a vertically scrolling list by expanding and collapsing the group when user touches it`s header. You may have seen something like this in app menus, options or settings expandable list views are useful because the can show a lot of data or give all options required at a certain time.     

## **History**
This view has been present since API 1 and derives from the folling packge library path 
java.lang.Object
  -->	android.view.View
 	   -->	android.view.ViewGroup
 	 	   -->	android.widget.AdapterView<android.widget.ListAdapter>
 	 	 	   -->	android.widget.AbsListView
 	 	 	 	   -->	android.widget.ListView
 	 	 	 	 	   -->	android.widget.ExpandableListView


## **Code Example**

### **Place in Activity_Main.xml**

        <ExpandableListView
            android:id="@+id/expandableListView"
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:divider="@android:color/darker_gray"
            android:dividerHeight="0.5dp"
            android:indicatorLeft="?android:attr/expandableListPreferredItemIndicatorLeft" />
    </RelativeLayout>
    
 ### **Place in list_group.xml**

    <TextView
        android:id="@+id/listTitle"
        android:layout_width="fill_parent"
        android:layout_height="wrap_content"
        android:paddingLeft="?android:attr/expandableListPreferredItemPaddingLeft"
        android:textColor="@android:color/black"
        android:paddingTop="10dp"
        android:paddingBottom="10dp" />
</LinearLayout>

### **Place in list_items.xml**

    <TextView
        android:id="@+id/expandedListItems"
        android:layout_width="fill_parent"
        android:layout_height="wrap_content"
        android:paddingLeft="?android:attr/expandableListPreferredChildPaddingLeft"
        android:paddingTop="10dp"
        android:paddingBottom="10dp" />
</LinearLayout>

### **CustomExpandableListview.java**

package com.example.solar.expandablelistview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.util.HashMap;
import java.util.List;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;


public class CustomExpandableListview extends BaseExpandableListAdapter {

    private Context context;
    private List<String> expandableListTitle;
    private HashMap<String, List<String>> expandableListDetail;

    public CustomExpandableListview(Context context, List<String>
            expandableListTitle,
                                    HashMap<String, List<String>>
                                            expandableListDetail) {
        this.context = context;
        this.expandableListTitle = expandableListTitle;
        this.expandableListDetail = expandableListDetail;
    }

    @Override
    public Object getChild(int listPosition, int expandedListPosition) {
        return this.expandableListDetail.get(this.expandableListTitle.get(listPosition))
                .get(expandedListPosition);
    }

    @Override
    public long getChildId(int listPosition, int expandedListPosition) {
        return expandedListPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getChildView(int listPosition, final int expandedListPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        final String expandedListText = (String) getChild(listPosition, expandedListPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_items, null);
        }
        TextView expandedListTextView = (TextView) convertView
                .findViewById(R.id.expandedListItems);
        expandedListTextView.setText(expandedListText);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    @Override
    public int getChildrenCount(int listPosition) {
        return this.expandableListDetail.get(this.expandableListTitle.get(listPosition))
                .size();
    }

    @Override
    public Object getGroup(int listPosition) {
        return this.expandableListTitle.get(listPosition);
    }

    @Override
    public int getGroupCount() {
        return this.expandableListTitle.size();
    }

    @Override
    public long getGroupId(int listPosition) {
        return listPosition;
    }

    @Override
    public View getGroupView(int listPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String listTitle = (String) getGroup(listPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_group, null);
        }
        TextView listTitleTextView = (TextView) convertView
                .findViewById(R.id.listTitle);
        listTitleTextView.setTypeface(null, Typeface.BOLD);
        listTitleTextView.setText(listTitle);
        return convertView;
    }
}

### **MainActcivity.java**

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

#### **ExpandableListviewdata class**

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

your code should come out looking like the following pciture.

![outputExpandableListview](jerrehj.github.com/ExpandableListView/img/outputExpandableListview.jpg)

## **Motivation**
A short description of the motivation behind the creation and maintenance of the project. This should explain why the project exists.

## **Installation**
Provide code examples and explanations of how to get the project.

## **API Reference**
Depending on the size of the project, if it is small and simple enough the reference docs can be added to the README. For medium size to larger projects it is important to at least provide a link to where the API reference docs live.

## **Tests**
Describe and show how to run the tests with code examples.

## **Contributors**
Let people know how they can dive into the project, include important links to things like issue trackers, irc, twitter accounts if applicable.

## **License**
A short snippet describing the license (MIT, Apache, etc.)
