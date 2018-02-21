package me.toxicmushroom.broodtimer.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import me.toxicmushroom.broodtimer.Action;
import me.toxicmushroom.broodtimer.dialogs.InputDialogSure;
import me.toxicmushroom.broodtimer.MainActivity;
import me.toxicmushroom.broodtimer.R;
import me.toxicmushroom.broodtimer.adapters.BroodAdapter;
import me.toxicmushroom.broodtimer.data.Broden;
import me.toxicmushroom.broodtimer.data.MyDBHandler;
import me.toxicmushroom.broodtimer.reminder.PhaseService;

/**
 * Created by Merlijn on 17/02/2018.
 */

public class LoadBroodActivity extends AppCompatActivity implements InputDialogSure.InputDialogSureListener {

    private MyDBHandler dbHandler;
    private RecyclerView broodListView;
    private BroodAdapter broodAdapter;
    public static Action action;
    public static Broden broodToDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_brood);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Brood Loader");
        dbHandler = new MyDBHandler(this, null, null, 0);
        broodListView = findViewById(R.id.list);
        broodListView.setLayoutManager(new LinearLayoutManager(this));
        broodAdapter = new BroodAdapter(this, dbHandler.getAlleBroden());
        broodListView.setAdapter(broodAdapter);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }

    @Override
    public void okEvent() {
        switch (action) {
            case LIST_REMOVE:
                dbHandler.deleteBrood(broodToDelete.get_broodnaam());
                PhaseService.toStop.add(broodToDelete.get_broodnaam());
                startService(new Intent(getApplicationContext(), MainActivity.class));
                break;
        }
    }
}
