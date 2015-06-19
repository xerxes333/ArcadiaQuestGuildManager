package com.example.jdraxler.arcadiaquestguildmanager;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.content.ContentUris;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.jdraxler.arcadiaquestguildmanager.db.SavesDataSource;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jdraxler on 3/26/2015.
 */
public class SaveListFragment extends ListFragment {

    private static final String LOGTAG = "AQGM";

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setListAdapter(getSaves());
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {

        super.onListItemClick(l, v, position, id);
        // Object obj = this.getListAdapter().getItem(position);
        Save save = (Save) (Save) this.getListAdapter().getItem(position);  // Not sure why I have to do type casting twice but it seems to work


        String item = save.getName();
        Toast.makeText(getActivity().getBaseContext(), item, Toast.LENGTH_SHORT).show();

        // we want to load our other Fragment that allows us to manipulate the save
        SaveDetailsFragment saveDetailsFragment = new SaveDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putLong("SAVE_ID", save.getId());
        saveDetailsFragment.setArguments(bundle);

        FragmentTransaction fragTrans = getActivity().getFragmentManager().beginTransaction();
        fragTrans.replace(R.id.container, saveDetailsFragment);
        fragTrans.addToBackStack(null);
        fragTrans.commit();



    }

    public void refresh(){
        setListAdapter(getSaves());
    }

    private ArrayAdapter<Save> getSaves(){
        SavesDataSource savesDS = new SavesDataSource(this.getActivity());
        List<Save> saves = savesDS.findAll();
        List<String> list = new ArrayList<String>();

        if(saves.size() > 0){
            for (Save save: saves){
                list.add(save.getName());
            }
        }

        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1, list);
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),R.layout.listview_saves_item_row, R.id.label, list);
        SavesArrayAdapter adapter = new SavesArrayAdapter(getActivity().getBaseContext(), saves);
        return adapter;

    }

}
