package com.library;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.LinkedHashMap;
import java.util.List;


public class ExpandableListAdapter extends
        BaseExpandableListAdapter {
    private Context context;
    private List<String> listheader;
    private LinkedHashMap<String, List<String>> listHashMap;

    public ExpandableListAdapter(Context context, List<String> listheader, LinkedHashMap<String, List<String>> listHashMap) {
        this.context = context;
        this.listheader = listheader;
        this.listHashMap = listHashMap;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return this.listHashMap.get(this.listheader.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPositon) {
        return childPositon;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View view, ViewGroup viewGroup) {
        final String childText = (String) getChild(groupPosition, childPosition);

        if(view == null) {
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_childitem, null);
        }

        TextView childTextView = (TextView) view.findViewById(R.id.listchild);
        childTextView.setText(childText);
        return view;
    }
    @Override
    public int getChildrenCount(int groupPosition) {
        return this.listHashMap.get(this.listheader.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.listheader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return  this.listheader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return  groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View view, ViewGroup viewGroup) {
        final String groupText = (String) getGroup(groupPosition);
        if(view == null) {
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_group, null);
        }
        TextView groupTextView = (TextView) view.findViewById(R.id.listheader);
        groupTextView.setTypeface(null, Typeface.BOLD);
        groupTextView.setText(groupText);
        return view;
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
