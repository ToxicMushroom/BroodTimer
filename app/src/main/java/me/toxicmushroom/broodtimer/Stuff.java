package me.toxicmushroom.broodtimer;

import android.app.Activity;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import me.toxicmushroom.broodtimer.data.Broden;
import me.toxicmushroom.broodtimer.data.MyDBHandler;

/**
 * Created by Merlijn on 5/02/2018.
 */

public class Stuff {

    public static Broden getBroodByName(String broodnaam, MyDBHandler dbHandler) {
        for (Broden broden : dbHandler.getAlleBroden()) {
            if (broodnaam.equals(broden.get_broodnaam())) return broden;
        }
        return null;
    }

}
