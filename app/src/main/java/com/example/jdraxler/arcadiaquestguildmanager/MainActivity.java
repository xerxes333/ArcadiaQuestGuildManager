package com.example.jdraxler.arcadiaquestguildmanager;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.jdraxler.arcadiaquestguildmanager.db.AQDatabaseHandler;
import com.example.jdraxler.arcadiaquestguildmanager.db.HeroesDataSource;
import com.example.jdraxler.arcadiaquestguildmanager.db.ItemsDataSource;

import java.util.List;


public class MainActivity extends Activity {

    public HeroesDataSource heroesDS;
    public ItemsDataSource itemsDS;

    private SaveListFragment saveListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        saveListFragment = new SaveListFragment();

        if (savedInstanceState == null) {
            getFragmentManager()
                .beginTransaction()
                .add(R.id.container, saveListFragment)
                .commit();
        }

        initializeData();

    }

    public void detailsFragment(){

    }

    public SaveListFragment getSaveListFragment(){
        return saveListFragment;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if (id == R.id.action_new) {
            createSave();
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }
    }


    public void createSave() {
//        Intent intent = new Intent(this, NewSave.class);
//        startActivity(intent);
        FragmentManager manager = getFragmentManager();
        NewSaveDialogFragment d = new NewSaveDialogFragment();
        d.show(manager, "NewSaveDialogFragment");
    }

    @Override
    protected void onResume() {
        heroesDS.open();
        super.onResume();
    }

    private void createData(){
        HeroesPullParser parser = new HeroesPullParser();
        List<Hero> heroes = parser.parseXML(this);

        for (Hero hero : heroes){
            heroesDS.create(hero);
        }
    }

    private void initializeData(){
        AQDatabaseHandler db = new AQDatabaseHandler(this);
        List<Hero> heroes = loadHeroes();
        List<Item> items = loadItems();
    }

    private List<Hero> loadHeroes(){
        heroesDS = new HeroesDataSource(this);
        heroesDS.open();

        List<Hero> heroes = heroesDS.findAll();
        if(heroes.size() == 0){
            createData();
            heroes = heroesDS.findAll();
        }

        return heroes;
    }

    private List<Item> loadItems(){
        itemsDS = new ItemsDataSource(this);
        itemsDS.open();

        List<Item> items = itemsDS.findAll();
        if(items.size() == 0){

            ItemsPullParser parser = new ItemsPullParser();
            List<Item> list = parser.parseXML(this);

            for (Item item : list){
                itemsDS.create(item);
            }

            items = itemsDS.findAll();
        }

        return items;
    }

}

