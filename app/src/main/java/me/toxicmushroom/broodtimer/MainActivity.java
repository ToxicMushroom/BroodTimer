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
import android.widget.TextView;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import me.toxicmushroom.broodtimer.activities.AddBroodActivity;
import me.toxicmushroom.broodtimer.activities.LoadBroodActivity;
import me.toxicmushroom.broodtimer.adapters.LoadedBroodAdapter;
import me.toxicmushroom.broodtimer.data.Broden;
import me.toxicmushroom.broodtimer.data.MyDBHandler;
import me.toxicmushroom.broodtimer.reminder.PhaseService;

public class MainActivity extends AppCompatActivity implements InputDialogSure.InputDialogSureListener {

    MyDBHandler myDBHandler;
    RecyclerView broodListView;
    LoadedBroodAdapter loadedBroodAdapter;
    FloatingActionButton fab, fab1, fab2;
    public static Action action;
    public static Broden broodToUnload;
    boolean isFABOpen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Start Typing under this
        myDBHandler = new MyDBHandler(this, null, null, 0);
        Toolbar toolbar = findViewById(R.id.toolbar);//Dat blauwe ding dat je altijd ziet
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        toolbar.setTitle("BroodScheduler");
        fab = findViewById(R.id.fab);
        fab1 = findViewById(R.id.fab1);
        fab2 = findViewById(R.id.fab2);

        //Broden lijst gedoe
        broodListView = findViewById(R.id.list);
        broodListView.setLayoutManager(new LinearLayoutManager(this));
        loadedBroodAdapter = new LoadedBroodAdapter(this, myDBHandler.getLoadedBroden());
        broodListView.setAdapter(loadedBroodAdapter);


        fab.setOnClickListener((v) -> {
            if (!isFABOpen) {
                showFABMenu();
            } else {
                closeFABMenu();
            }
        });

        fab1.setOnClickListener((v) -> startActivity(new Intent(getApplicationContext(), LoadBroodActivity.class)));
        fab2.setOnClickListener((v) -> startActivity(new Intent(getApplicationContext(), AddBroodActivity.class)));
        updater(loadedBroodAdapter, this);
    }

    private void showFABMenu() {
        isFABOpen = true;
        fab1.animate().translationY(-getResources().getDimension(R.dimen.standard_55));
        fab2.animate().translationY(-getResources().getDimension(R.dimen.standard_105));
    }

    private void closeFABMenu() {
        isFABOpen = false;
        fab1.animate().translationY(0);
        fab2.animate().translationY(0);
    }

    private ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

    public void updater(LoadedBroodAdapter loadedBroodAdapter, Activity activity) {
        Runnable runnable = () -> activity.runOnUiThread(loadedBroodAdapter::notifyDataSetChanged);
        executorService.scheduleAtFixedRate(runnable, 1, 500, TimeUnit.MILLISECONDS);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        if (id == R.id.action_reset) {
            action = Action.MAIN_RESET;
            InputDialogSure inputDialog = new InputDialogSure();
            inputDialog.show(getSupportFragmentManager(), "test123");
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void okEvent() {
        switch (action) {
            case MAIN_RESET:
                for (Broden brood : myDBHandler.getLoadedBroden()) {
                    myDBHandler.setLoadedState(brood.get_broodnaam(), false);
                    PhaseService.toStop.add(brood.get_broodnaam());
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                }
                break;
            case MAIN_UNLOAD:
                myDBHandler.setLoadedState(broodToUnload.get_broodnaam(), false);
                PhaseService.toStop.add(broodToUnload.get_broodnaam());
                startService(new Intent(getApplicationContext(), MainActivity.class));
                break;
        }
    }

    public static String faseToName(int i) {
        switch (i) {
            case 1:
                return "bloem + water fase";
            case 2:
                return "hydrolyse fase";

            case 3:
                return "gist, zout en boter mengen";

            case 4:
                return "kneden";

            case 5:
                return "eerste rijs";

            case 6:
                return "terugslaan en opbollen";

            case 7:
                return "rusten";

            case 8:
                return "vorm geven";

            case 9:
                return "tweede rijs";

            case 10:
                return "bakken in oven";

            default:
                return "error";

        }
    }
}

