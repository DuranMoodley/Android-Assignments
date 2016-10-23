package com.moodley.duran.prjbuildingmanagement;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

class AboutUsAdapter extends BaseExpandableListAdapter {

    private final List<String> listOfTenants;
    private final HashMap<String,List<String>> tenantsInformation;
    private final Context ctx;

    public AboutUsAdapter(Context con, HashMap<String, List<String>> tenantsInfo, List<String> tenantList)
    {
        this.ctx = con;
        listOfTenants = tenantList;
        tenantsInformation = tenantsInfo;
    }
    //*************************************************
    @Override
    public int getGroupCount() {
        return listOfTenants.size();
    }
    //*************************************************
    @Override
    public int getChildrenCount(int groupPosition) {
        return tenantsInformation.get(listOfTenants.get(groupPosition)).size();
    }
    //*************************************************
    @Override
    public Object getGroup(int groupPosition) {
        return listOfTenants.get(groupPosition);
    }
    //*************************************************
    @Override
    public Object getChild(int groupPosition, int childPosition)
    {
        return tenantsInformation.get(listOfTenants.get(groupPosition)).get(childPosition);
    }
    //*************************************************
    @Override
    public long getGroupId(int groupPosition)
    {
        return groupPosition;
    }
    //*************************************************
    @Override
    public long getChildId(int groupPosition, int childPosition)
    {
        return childPosition;
    }
    //*************************************************
    @Override
    public boolean hasStableIds() {
        return false;
    }
    //*************************************************
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String groupHeading = getGroup(groupPosition).toString();

        if(convertView == null)
        {
            LayoutInflater inflator = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflator.inflate(R.layout.parent_layout,parent,false);
        }

        TextView parenttv = (TextView) convertView.findViewById(R.id.tvHeading);
        parenttv.setTypeface(null, Typeface.BOLD);
        parenttv.setText(groupHeading);
        return convertView;
    }
    //*************************************************
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent)
    {
        String childHeading = getChild(groupPosition,childPosition).toString();

        if(convertView == null)
        {
            LayoutInflater inflator = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflator.inflate(R.layout.child_layout,parent,false);
        }

        TextView childtv = (TextView) convertView.findViewById(R.id.tvSub);
        childtv.setText(childHeading);
        return convertView;
    }
    //*************************************************
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
