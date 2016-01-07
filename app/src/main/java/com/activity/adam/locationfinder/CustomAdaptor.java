package com.activity.adam.locationfinder;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by adam on 07-Nov-15.
 */
class CustomAdapter extends BaseAdapter implements Filterable{

    private ArrayList<MapData> _data;
    Context _c;



    private static List<MapData> filteredData;


    private Filter filter;


    CustomAdapter (ArrayList<MapData> data, Context c){
        _data = data;
        _c = c;
        filteredData = data;
    }
    public static List<MapData> getFilteredData() {
        return filteredData;
    }
    public int getCount() {
        // TODO Auto-generated method stub
        return filteredData.size();
    }

    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return filteredData.get(position);
    }

    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View v = convertView;
        if (v == null)
        {
            LayoutInflater vi = (LayoutInflater)_c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.listviewdetails, null);
        }



        ImageView image = (ImageView) v.findViewById(R.id.icon);

        TextView subView = (TextView)v.findViewById(R.id.subject);
        TextView descView = (TextView)v.findViewById(R.id.description);

        MapData msg = filteredData.get(position);
        image.setImageResource(R.drawable.arrow);
        if(msg.getAbbr().contains("null"))
        {
            subView.setText(msg.getName());
        }else {
            subView.setText(msg.getAbbr());
        }
        descView.setText(msg.getName());


        return v;
    }

    @Override
    public Filter getFilter() {

        if (filter == null)
            filter = new FilteredData();
        System.out.println("BANTER "+filter.toString());
        return filter;
    }

    private class FilteredData extends Filter {



        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            // We implement here the filter logic
            if (constraint == null || constraint.length() == 0) {
                // No filter implemented we return all the list
                results.values = _data;
                results.count = _data.size();
            }
            else {
                // We perform filtering operation
                List<MapData> list = new ArrayList<MapData>();

                for (MapData p : filteredData) {
                    if (p.getName().toUpperCase().contains(constraint.toString().toUpperCase()))
                         list.add(p);
                }

                results.values =  list;
                results.count =  list.size();

            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint,
                                      FilterResults results) {

            // Now we have to inform the adapter about the new list filtered
            if (results.count == 0)
                notifyDataSetInvalidated();
            else {
                filteredData = (List<MapData>) results.values;
                notifyDataSetChanged();
            }

        }

    }

}
