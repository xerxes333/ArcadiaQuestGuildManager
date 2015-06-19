package com.example.jdraxler.arcadiaquestguildmanager;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.jdraxler.arcadiaquestguildmanager.db.HeroesDataSource;
import com.example.jdraxler.arcadiaquestguildmanager.db.SaveHeroesDataSource;
import com.example.jdraxler.arcadiaquestguildmanager.db.SavesDataSource;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by jdraxler on 2/26/2015.
 */
public class NewSaveDialogFragment extends DialogFragment {

    private static final String LOGTAG = "AQGM";

//    public static interface
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        final View view = inflater.inflate(R.layout.new_save_dialog, null);

        loadSpinnerData(view);

        builder.setView(view)
                .setTitle(R.string.new_save_title)
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // FIRE ZE MISSILES!
                        Toast.makeText(getActivity().getBaseContext(), "FIRE ZE MISSILES!", Toast.LENGTH_SHORT).show();
                        try {
                            createNewSave2();
                            // createFile();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        SaveListFragment saveListFragment = ((MainActivity)getActivity()).getSaveListFragment();
                        saveListFragment.refresh();

                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                        Toast.makeText(getActivity().getBaseContext(), "Fine, take a nap.", Toast.LENGTH_SHORT).show();
                    }
                });

        // Create the AlertDialog object and return it

        return builder.create();
    }



    public void createFile() throws IOException {

//        View view = getView(R.layout.new_save_dialog);
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.new_save_dialog, null);
        Spinner guild = (Spinner) view.findViewById(R.id.spinnerGuild);
        String txt = guild.getSelectedItem().toString();

        String text = "something"+txt;
        FileOutputStream fos = getActivity().openFileOutput("myfilename.txt", Context.MODE_PRIVATE);
        fos.write(text.getBytes());
        fos.close();

    }

    private void loadSpinnerData(View view){

        HeroesDataSource datasource = new HeroesDataSource(this.getActivity());
        List<String> names = datasource.getHeroNames();

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this.getActivity(),android.R.layout.simple_spinner_item, names);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner hero1Spin = (Spinner) view.findViewById(R.id.spinnerHero1);
        Spinner hero2Spin = (Spinner) view.findViewById(R.id.spinnerHero2);
        Spinner hero3Spin = (Spinner) view.findViewById(R.id.spinnerHero3);

        // attaching data adapter to spinner
        hero1Spin.setAdapter(dataAdapter);
        hero2Spin.setAdapter(dataAdapter);
        hero3Spin.setAdapter(dataAdapter);


    }

    public void createNewSave2() throws IOException, JSONException {

        Dialog dlg = getDialog();

        // get the inputs
        EditText nameText = (EditText)dlg.findViewById(R.id.textFileName);
        Spinner guildSpin = (Spinner) dlg.findViewById(R.id.spinnerGuild);
        Spinner hero1Spin = (Spinner) dlg.findViewById(R.id.spinnerHero1);
        Spinner hero2Spin = (Spinner) dlg.findViewById(R.id.spinnerHero2);
        Spinner hero3Spin = (Spinner) dlg.findViewById(R.id.spinnerHero3);

        String name  = nameText.getText().toString();
        String guild = guildSpin.getSelectedItem().toString();
        String hero1 = hero1Spin.getSelectedItem().toString();
        String hero2 = hero2Spin.getSelectedItem().toString();
        String hero3 = hero3Spin.getSelectedItem().toString();

        if(name.isEmpty()){
            Calendar c = Calendar.getInstance();
            SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy HH:mm");
            name = df.format(c.getTime());
        }

        ArrayList<String> foo = new ArrayList<String>();
        foo.add(name);
        foo.add(guild);
        foo.add(hero1);
        foo.add(hero2);
        foo.add(hero3);
        Log.d(LOGTAG, foo.toString());

        // load Heros into list
        HeroesDataSource heroDS;
        heroDS = new HeroesDataSource(getActivity());
        ArrayList<Hero> heroes = new ArrayList<Hero>();
        heroes.add(heroDS.getHeroByName(hero1));
        heroes.add(heroDS.getHeroByName(hero2));
        heroes.add(heroDS.getHeroByName(hero3));

        // Create save object and save to DB
        Save save = new Save();
        save.setName(name);
        save.setGuild(guild);
        save.setHeroes(heroes);

        SavesDataSource saveDS;
        saveDS = new SavesDataSource(getActivity());
        saveDS.open();
        save = saveDS.create(save);

        // Save TABLE_SAVE_HEROES records
        SaveHeroesDataSource saveHeroesDS;
        saveHeroesDS = new SaveHeroesDataSource(getActivity());
        saveHeroesDS.create(save);

    }

    public void createNewSave() throws IOException, JSONException {

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.new_save_dialog, null);

        // get the inputs
        EditText nameText = (EditText)view.findViewById(R.id.textFileName);
        Spinner guildSpin = (Spinner) view.findViewById(R.id.spinnerGuild);
        Spinner hero1Spin = (Spinner) view.findViewById(R.id.spinnerHero1);
        Spinner hero2Spin = (Spinner) view.findViewById(R.id.spinnerHero2);
        Spinner hero3Spin = (Spinner) view.findViewById(R.id.spinnerHero3);

        String name  = nameText.getText().toString();
        String guild = guildSpin.getSelectedItem().toString();
        String hero1 = hero1Spin.getSelectedItem().toString();
        String hero2 = hero2Spin.getSelectedItem().toString();
        String hero3 = hero3Spin.getSelectedItem().toString();

        Log.i("MyActivity", name);

        if(name.isEmpty()){
            Calendar c = Calendar.getInstance();
            SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
            name = "Save_" + df.format(c.getTime());
        }

/*
* {	"Name":"Save1",
    "Guild":"Fox",
    "GuildImage":"fox",
    "Heroes": [
        {"HeroID":1, "Items":[1,2,3,4]},
        {"HeroID":2, "Items":[21,22,23,24]},
        {"HeroID":3, "Items":[21,22,23,24]},
        {"HeroID":4, "Items":[21,22,23,24]}
    ]
},
* */
        JSONArray heroes, items;
        JSONObject data, save, object;

        items = new JSONArray();
        items.put(1);
        items.put(2);
        items.put(3);
        items.put(4);

        JSONObject hero1Object = new JSONObject();
        hero1Object.put("heroID",hero1);
        hero1Object.put("items",items);

        JSONObject hero2Object = new JSONObject();
        hero2Object.put("heroID",hero2);
        hero2Object.put("items",items);

        heroes = new JSONArray();
        heroes.put(hero1Object);
        heroes.put(hero2Object);

        save = new JSONObject();
        save.put("name",name);
        save.put("guild",guild);
        save.put("guildImage","imageURI");
        save.put("heroes",heroes);

        object = new JSONObject();
        object.put("saves",save);

        data = new JSONObject();
        data.put("saves",save);

        String json = data.toString();
        Log.i("MyActivity", json);


/////////////////////////// USING GSON
        Hero h1 = new Hero();
        h1.setId(1);
        h1.setName(hero1);

        Hero h2 = new Hero();
        h2.setId(2);
        h2.setName(hero2);

        List<Hero> heroList = new ArrayList<Hero>();
        heroList.add(h1);
        heroList.add(h2);



        Save gsonSave = new Save();
        gsonSave.setName(name);
        gsonSave.setGuild(guild);
//        gsonSave.setHeroes(heroList);

        Gson gson = new Gson();
        json = gson.toJson(gsonSave);
        Log.i("MyActivity", json);


/////////////////////////// USING SQLite
        HeroesDataSource datasource;
        datasource = new HeroesDataSource(getActivity());
        datasource.open();

        Hero h22 = new Hero();
        h22.setName(hero2);
        h22 = datasource.create(h22);


        // FileOutputStream fos = getActivity().openFileOutput("saves", Context.MODE_PRIVATE);
        // fos.close();

    }


}
