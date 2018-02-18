package me.toxicmushroom.broodtimer.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import me.toxicmushroom.broodtimer.MainActivity;
import me.toxicmushroom.broodtimer.R;
import me.toxicmushroom.broodtimer.adapters.BroodAdapter;
import me.toxicmushroom.broodtimer.data.MyDBHandler;

/**
 * Created by Merlijn on 17/02/2018.
 */

public class LoadBroodActivity extends AppCompatActivity {

    private MyDBHandler dbHandler;
    private RecyclerView broodListView;
    private BroodAdapter broodAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_brood);
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

}
