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
import at.tba.treasurehunt.controller.UserDataController;
import at.tba.treasurehunt.utils.DummyDataProvider;
import data_structures.treasure.Achievement;
import data_structures.treasure.Coupon;
import data_structures.user.Inventory;


public class InventoryActivity extends Activity {

    private ListView listView;
    private ArrayList<String> listItems;
    private ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);
        ActivityManager.setCurrentActivity(this);

        listView = (ListView) findViewById(R.id.inventoryListView);
        listItems = new ArrayList<>();
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listItems);
        listView.setAdapter(arrayAdapter);
        setInventoryList(UserDataController.getInstance().getCurrentUserData().getInventory());
    }

    @Override
    protected void onResume(){
        super.onResume();
        ActivityManager.setCurrentActivity(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_inventory, menu);
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

    private void setInventoryList(Inventory inventory){
        int i = 0;
        for (Inventory.Entry e: inventory.getInventoryList()) {
            String entryString;
            switch (e.getContent().getType()) {
                case "COUPON":
                    Coupon c = (Coupon) e.getContent();
                    if(e.getCount() == 1)
                        entryString = "Coupon: " + c.getCompanyName() + ", value: " + c.getValue();
                    else
                        entryString = e.getCount() + " x " + "Coupon: " + c.getCompanyName() + ", value: " + c.getValue();
                    break;
                case "ACHIEVEMENT":
                    Achievement a = (Achievement) e.getContent();
                    if(e.getCount() == 1)
                        entryString = "Achievement: " + a.getName() + ", description: \n      " + a.getDescription();
                    else
                        entryString = e.getCount() + " x " + "Achievement: " + a.getName() + ", description: \n      " + a.getDescription();
                    break;
                default:
                    entryString = e.getCount()+"x "+e.getContent().getType();

            }
            arrayAdapter.insert(entryString, i);
            i++;
        }
        arrayAdapter.notifyDataSetChanged();
    }
}
