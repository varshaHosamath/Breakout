package com.uidesign.vxh150530sxg143630.breakout;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TwoLineListItem;

import java.util.List;

/*************************************************************************
 * Created by Varsha and Satheesh on 11/29/2015.
 * CustomAdapter class to define the type of two line listview we need for displaying highscores
 **************************************************************************/
public class CustomAdapter<HighScore> extends ArrayAdapter<HighScore> {
    Context context = null;
    public CustomAdapter(Context context, int resource, List<HighScore> objects){

        super(context,resource,objects);
        this.context=context;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        TwoLineListItem row;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = (TwoLineListItem) inflater.inflate(android.R.layout.simple_list_item_2, null);
        } else {
            row = (TwoLineListItem) convertView;
        }
        com.uidesign.vxh150530sxg143630.breakout.HighScore data = HighScoreUtil.getHighScoresList().get(position);
        row.getText1().setTextColor(Color.BLACK);
        row.getText2().setTextColor(Color.BLACK);
        row.getText1().setText(data.getName());
        row.getText2().setText(data.getTime());

        return row;
    }
}
