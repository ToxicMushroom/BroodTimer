package me.toxicmushroom.broodtimer;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import me.toxicmushroom.broodtimer.data.Broden;
import me.toxicmushroom.broodtimer.data.MyDBHandler;

public class MainActivity extends AppCompatActivity {

    MyDBHandler myDBHandler;
    View emptyView;
    RecyclerView broodListView;
    BroodAdapter broodAdapter;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Start Typing under this
        myDBHandler = new MyDBHandler(this, null, null, 0);
        emptyView = findViewById(R.id.empty_view);
        toolbar = findViewById(R.id.toolbar);//Dat blauwe ding dat je altijd ziet
        setSupportActionBar(toolbar);
        toolbar.setTitle("BroodSchedulder");


        //Broden lijst gedoe
        broodListView = findViewById(R.id.list);
        broodListView.setLayoutManager(new LinearLayoutManager(this));
        broodAdapter = new BroodAdapter(this, myDBHandler.getAlleBroden());
        broodListView.setAdapter(broodAdapter);

        for (Broden brood : myDBHandler.getAlleBroden()) {
            RelativeLayout relativeLayout = findViewById(R.id.broodDisplay);
            TextView broodNaam = findViewById(R.id.recycle_title);
            TextView phaseProgress = findViewById(R.id.recycle_brood_phase_now);
            TextView phaseIncomming = findViewById(R.id.recycle_brood_phase_incomming);
            ImageView thumbNailImage = findViewById(R.id.thumbnail_image);
            broodNaam.setText(brood.get_broodnaam());
            broodListView.addView(relativeLayout);
        }

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), AddBroodActivity.class)));
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

        return super.onOptionsItemSelected(item);
    }
}
