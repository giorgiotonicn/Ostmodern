package com.giorgio.ostmoderntest.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.giorgio.ostmoderntest.R;
import com.giorgio.ostmoderntest.model.Sets;

import java.util.ArrayList;

/**
 * Created by Giorgio on 10/10/15.
 */
public class SetsAdapter  extends ArrayAdapter<Sets>{

    private ArrayList<Sets> values;

    public SetsAdapter(Context context, int textViewResourceId, ArrayList<Sets> values) {
        super(context, textViewResourceId, values);

        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = convertView;
        ViewHolder holder = null;

        if(this.values != null){
            final Sets set = this.values.get(position);
            if(rowView == null || (rowView != null && rowView.getTag() == null)){
                rowView = inflater.inflate(R.layout.row_sets_list, parent, false);
                holder = new ViewHolder();

                holder.title = (TextView) rowView.findViewById(R.id.set_title);
            }else{
                holder = (ViewHolder) rowView.getTag();
            }

            holder.title.setText(set.getTitle());
        }

        return rowView;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    static class ViewHolder {
        TextView title;
    }
}
