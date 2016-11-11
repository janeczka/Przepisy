package intentlearning.aniastudio.com.przepisy;


import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.graphics.Typeface;

import java.awt.font.TextAttribute;
import java.util.HashMap;
import java.util.List;



/**
 * Created by Ania on 2016-05-18.
 */
public class AdapterOpisu extends BaseExpandableListAdapter {
    private Context mContext;
    private List<String> mExpandableHeaders;
    private HashMap<String, List<String>> mExpandableItems;
    private List<Integer> mlistHeadersIcon;

    public AdapterOpisu(Context context, List<Integer> listHeadersIcons,List<String> listDataHeader, HashMap<String, List<String>> listChildData){
        this.mContext = context;
        this.mExpandableHeaders = listDataHeader;
        this.mExpandableItems = listChildData;
        this.mlistHeadersIcon = listHeadersIcons;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return this.mExpandableItems.get(this.mExpandableHeaders.get(groupPosition)).get(childPosition);
        //return super.getChildType(groupPosition, childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final String childText = (String) getChild(groupPosition,childPosition);

        if(convertView==null){
            LayoutInflater inflater = (LayoutInflater) this.mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item, null);
        }
        TextView txtListChild = (TextView) convertView.findViewById(R.id.expandable_List_item);
        txtListChild.setText(childText);

        Typeface tf = Typeface.createFromAsset(mContext.getAssets(), "fonts/Dual-300.otf");
        txtListChild.setTypeface(tf);

        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this.mExpandableItems.get(this.mExpandableHeaders.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.mExpandableHeaders.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this.mExpandableHeaders.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);

        if(convertView==null){
            LayoutInflater inflater = (LayoutInflater) this.mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(R.layout.list_group,null);
        }

        TextView listheader = (TextView) convertView.findViewById(R.id.expandable_list_header);
        listheader.setTypeface(null, Typeface.BOLD);
        listheader.setText(headerTitle);



       // String fontPath = "fonts/Face Your Fears.ttf";



        // Loading Font Face
        Typeface tf = Typeface.createFromAsset(mContext.getAssets(), "fonts/Dual-300.otf");
        listheader.setTypeface(tf,Typeface.BOLD);


        //comment: Image for group
        ImageView rowGroupIcon = (ImageView) convertView.findViewById(R.id.row_group_icon);
        int id = this.mlistHeadersIcon.get(groupPosition);
        rowGroupIcon.setImageResource(id);


        //rowGroupIcon.setImageResource(R.drawable.przygotowanie);
       // rowGroupIcon.setImageResource(R.drawable.skladniki);




        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }


}
