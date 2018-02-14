package me.toxicmushroom.broodtimer;

import android.app.Activity;
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
import android.widget.Toast;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import me.toxicmushroom.broodtimer.data.Broden;
import me.toxicmushroom.broodtimer.data.MyDBHandler;
import me.toxicmushroom.broodtimer.reminder.PhaseService;

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
        broodAdapter.setItems(myDBHandler.getAlleBroden());

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), AddBroodActivity.class)));
        updater(broodAdapter, this);
    }

    private ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

    public void updater(BroodAdapter broodAdapter, Activity activity) {
        Runnable runnable = () -> activity.runOnUiThread(broodAdapter::notifyDataSetChanged);
        executorService.scheduleAtFixedRate(runnable, 1, 500, TimeUnit.MILLISECONDS);
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

        if (id == R.id.action_reset) {
            for (Broden brood : myDBHandler.getAlleBroden()) {
                myDBHandler.deleteBrood(brood.get_broodnaam());
                PhaseService.toStop.add(brood.get_broodnaam());
            }
        }

        return super.onOptionsItemSelected(item);
    }
}
