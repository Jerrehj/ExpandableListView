# ExpandableListView
## **Synopsis** 
Hello, this blog will serve as a helpful guide in under standing the functionality, use, history and how to create an ExpandableListView of your own. expandable list views are a type of view that shows items in a vertically scrolling list by expanding and collapsing the group when user touches it`s header. You may have seen something like this in app menus, options or settings expandable list views are useful because the can show a lot of data or give all options required at a certain time.     

## **History**
This view has been present since API 1 with some methods and attributes being implemented in API 14, 21 and 23 and derives from the following package library path 

java.lang.Object
  -->	android.view.View
 	   -->	android.view.ViewGroup
 	 	   -->	android.widget.AdapterView<android.widget.ListAdapter>
 	 	 	   -->	android.widget.AbsListView
 	 	 	 	   -->	android.widget.ListView
 	 	 	 	 	   -->	android.widget.ExpandableListView


## **Code Example**

your code should come out looking like the following picture.

![outputexpandablelistview](https://user-images.githubusercontent.com/44241497/49681133-b5e59700-fa6a-11e8-96ee-9115f1ee8973.PNG)

it should be able to expand and collaspe with the child elements as shown.

![outputexpandablelistviewclosed](https://user-images.githubusercontent.com/44241497/49681134-b5e59700-fa6a-11e8-9b6f-18adb0068647.PNG)

![outputexpandablelistviewhalf](https://user-images.githubusercontent.com/44241497/49681135-b67e2d80-fa6a-11e8-9bd3-884a839518ba.PNG)


## **Motivation**
This project exists as a method of teaching other the uses of an ExpandableListView as well as how easy it is to create. I believe this will provide a useful tool in creating list views and is easy to understand.

## **Installation**
### **Place in Activity_Main.xml**
This is the actual Ui menu you see
        <ExpandableListView
            android:id="@+id/expandableListView"
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:divider="@android:color/darker_gray"
            android:dividerHeight="0.5dp"
            android:indicatorLeft="?android:attr/expandableListPreferredItemIndicatorLeft" />
    </RelativeLayout>
    
 ### **Place in list_group.xml**
the menu for groups
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
the menu for list items
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

#### **ExpandableListviewdata Class**
 
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
   
  
   
## **Major Methods/Attributes**
 there are many important attributes some of which are:
 * android:childDivider: Which is the drawable or color of the child's divider the height of which is the same as a normal list item divider 
 * android:childIndicator: The indicator beside the child view 
 * android:childIndicatorEnd: The end indicator for child 
 * android:childIndicatorLeft: The left indicator for child 
 * android:childIndicatorRight: The right indicator for child 
 * android:childIndicatorStart: The start indicator for child 
 * android:groupIndicator: Indicator beside the group view and android:indicatorStart:
 * android:indicatorEnd: The end indicator for item
 * android:indicatorLeft: The left indicator for item
 * android:indicatorRight:  The right indicator for item 
 * android:indicatorStart: The start indicator for item 
 
 and the important methods are:
 
 * collapseGroup: Collaspes a group in list view
 * expandGroup: Expands a group in list view (there are two one returns Boolean the other returns animate) introduced in API level 14
 * getAccessibilityClassName: Returns the name of the class object for accessibility  purposes              introduced in API level 23
 * getSelectedId: Gets the Id of selected child or group
 * getExpandableListAdapter: Gets the adapter that provides data to the view
 * getExpandableListPosition: Converts flat list position to a child or group position
 * getFlatListPosition: Converts child or group posiiton to a flat list position
 * getPackedPositionChild: Gets he child position from PACKED_POSITION_TYPE_CHILD
 * getPackedPositionForChild: Returns packed position that represents a child position
 * getPackedPositionForGroup: Returns the packed position that represents a group position 
 * getPackedPositionType: Gets the type of a packed position
 etc
 
 there is only one protected method being dispatchDraw which is called by draw to draw the child's view

## **Tests**
it's important to know that you cannot use wrap_content properly for android:layout_height with ExpandableListviews xmls if the parent to this view does not have a specified size due to that size being on any length if you want to use wrap-content ensure the parent has a size. The ExpandableListViewData object matches header stings with the children strings in this case it matches video games with their console, this is important as its where the keys/options come from when you click on your choice of the first three elements. CustomExpandableListview class provides MainActivity with data from the ExpandableListViewData class and extends the BaseExpandableListAdapter and overrides the methods in the base class to provide the view for the ExpandableListViewData. list_items.xml, list_group.xml and acvtivity_main.xml are the layouts for the views.

## **API And Other Reference**
https://www.journaldev.com/9942/android-expandablelistview-example-tutorial
https://developer.android.com/reference/android/widget/ExpandableListView
https://gist.github.com/jxson/1784669
https://www.youtube.com/watch?v=jZxZIFnJ9jE

