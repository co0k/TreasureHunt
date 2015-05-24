package at.tba.treasurehunt.activities;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import at.tba.treasurehunt.R;
import at.tba.treasurehunt.utils.DummyDataProvider;
import data_structures.user.HighscoreList;


public class HighscoresActivity extends Activity {

    private ListView listView;
    private ArrayList<String> listItems;
    private ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscores);
        ActivityManager.setCurrentActivity(this);
        listView = (ListView) findViewById(R.id.highscoreListView);
        listItems = new ArrayList<>();
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listItems);
        listView.setAdapter(arrayAdapter);
        setList(DummyDataProvider.getDummyHighscoreList());
    }

    @Override
    protected void onResume(){
        super.onResume();
        ActivityManager.setCurrentActivity(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_highscores, menu);
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

        return super.onOptionsItemSelected(item);
    }

    private void setList(HighscoreList list){
        //listItems.clear();
        int i = 0;
        for (HighscoreList.Entry e: list.getList()) {
            arrayAdapter.insert(e.getName(), i);
            i++;
        }
        arrayAdapter.notifyDataSetChanged();
    }
}
