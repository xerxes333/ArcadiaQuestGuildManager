package com.example.jdraxler.arcadiaquestguildmanager;

import android.app.ListFragment;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import com.example.jdraxler.arcadiaquestguildmanager.db.SavesDataSource;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jdraxler on 3/26/2015.
 */
public class SaveDetailsFragment extends ListFragment {

    private static final String LOGTAG = "AQGM";

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setListAdapter(getSave());

        Bundle bundle = getArguments();
        if(bundle != null){
            String detail = bundle.getString("SAVE_ID", "no argument pass");
        }

    }

    private ArrayAdapter<Save> getSave(){
        SavesDataSource savesDS = new SavesDataSource(this.getActivity());
        List<Save> saves = savesDS.findAll();

        SaveArrayAdapter adapter = new SaveArrayAdapter(getActivity().getBaseContext(), saves);
        return adapter;

    }


}
