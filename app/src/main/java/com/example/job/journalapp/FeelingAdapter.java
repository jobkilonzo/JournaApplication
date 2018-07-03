package com.example.job.journalapp;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class FeelingAdapter extends ArrayAdapter<Feelings> {
    public FeelingAdapter( Context context, ArrayList<Feelings> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position,View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (convertView == null){
            listItemView = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.item_event, parent,false);
        }
        TextView eventTitle = (TextView) listItemView.findViewById(R.id.title);
        TextView event = (TextView) listItemView.findViewById(R.id.event);

        Feelings feelings = getItem(position);
        eventTitle.setText(feelings.getmTitle());
        event.setText(feelings.getmEvent());
        return listItemView;
    }
}
