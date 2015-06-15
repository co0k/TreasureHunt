package at.tba.treasurehunt.activities;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import at.tba.treasurehunt.R;
import at.tba.treasurehunt.treasures.TreasureChestHolder;
import data_structures.treasure.Achievement;
import data_structures.treasure.Coupon;
import data_structures.treasure.Treasure;

public class TreasureOpenActivity extends Activity {

    private Treasure openedTreasure;

    private TextView experienceGainedText;
    private TextView contentGainedText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_treasure_open);
        openedTreasure = TreasureChestHolder.getInstance().getCurrentSelectedTreasure();
        experienceGainedText = (TextView) findViewById(R.id.received_xp_text);
        contentGainedText = (TextView) findViewById(R.id.received_content_text);
        setTreasureInformationToLayout();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_treasure_open, menu);
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

    private void setTreasureInformationToLayout() {
        experienceGainedText.setText("Experience gained: " + openedTreasure.getXP());

        if (openedTreasure.getContent() != null){
            contentGainedText.setText("You received: " + getContentDescription(openedTreasure.getContent()));
        }else{
            contentGainedText.setText("This treasure has no contents.");
        }
        TreasureChestHolder.getInstance().setCurrentSelectedTreasure(null);
    }

    private String getContentDescription(Treasure.Content content){
        String entryString = "";
        switch (content.getType()) {
            case "COUPON":
                    Coupon c = (Coupon) content;
                    entryString = "Coupon: " + c.getCompanyName() + ", value: " + c.getValue();
                break;
            case "ACHIEVEMENT":
                Achievement a = (Achievement) content;
                    entryString = "Achievement: " + a.getName() + ", description: \n      " + a.getDescription();
                break;
            default:
                entryString = content+"x "+content.getType();
        }
        return entryString;
    }

    public void onButtonFinishClick(View v){
        finish();
    }
}
