package com.example.jdraxler.arcadiaquestguildmanager;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jdraxler on 3/26/2015.
 */
public class SaveArrayAdapter extends ArrayAdapter<Save> {

    private final Context context;
    private final  List<Save> saves;
    private final ArrayList<String> values = null;
    private static final String LOGTAG = "AQGM";

    public SaveArrayAdapter(Context context, List<Save> saves) {

        super(context, R.layout.listview_saves_item_row, saves);

        this.context = context;
        this.saves = saves;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        ViewHolder viewHolder;

        /**
         * WORKING HERE
         *
         * when the save is clicked it loads this layout which right now is just a copy of the savelist
         * we need to get this layout to show the hero info if the save the user selected
         */

//        if(convertView==null){
//
//        } else {
//
//        }

        View rowView = inflater.inflate(R.layout.listview_save_item_row, parent, false);
        TextView textView = (TextView) rowView.findViewById(R.id.label);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);


        Save save = saves.get(position);

        textView.setText(save.getName());
        String guild = save.getGuild();
        guild = String.valueOf(guild).toLowerCase();

        Log.d(LOGTAG, String.valueOf(guild));

        imageView.setImageResource(R.drawable.guild_lion);



        return rowView;
    }

    static class ViewHolder {
        TextView textViewItem;
    }
}
