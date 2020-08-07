package com.appzupp.myapplication.Model;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;

import java.util.ArrayList;
import java.util.List;

public class MyListAdapter extends BaseAdapter implements Filterable {
    Context cx;
    List<String> arrList;
    public MyListAdapter(Context ctx, List arrList){
        this.cx=ctx;
        this.arrList=arrList;

    }
    @Override
    public int getCount() {
        return this.arrList.size();
    }

    @Override
    public Object getItem(int position) {
        return this.arrList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                return null;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

            }
        };
    }




}
