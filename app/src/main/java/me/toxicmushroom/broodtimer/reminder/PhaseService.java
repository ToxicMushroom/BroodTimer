package me.toxicmushroom.broodtimer.reminder;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import me.toxicmushroom.broodtimer.data.Broden;
import me.toxicmushroom.broodtimer.data.MyDBHandler;

/**
 * Created by Merlijn on 4/02/2018.
 */

public class PhaseService extends Service {

    int progressInSeconds = 0;
    int totalTimtPast = 0;
    int nextPhase = 1;
    private Broden brood;
    MyDBHandler dbHandler;


    private ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

    public PhaseService(Broden brood) {
        this.brood = brood;
        dbHandler = new MyDBHandler(this, null, null, 0);
    }

    public PhaseService() {
        this.brood = null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "Service started.", Toast.LENGTH_LONG).show();
        dbHandler = new MyDBHandler(this, null, null, 0);
        brood = new Broden(intent.getStringExtra("broodnaam"));
        brood.setFases(
                intent.getIntExtra("fase1", dbHandler.getLatestBrood().getFase1()),
                intent.getIntExtra("fase2", dbHandler.getLatestBrood().getFase2()),
                intent.getIntExtra("fase3", dbHandler.getLatestBrood().getFase3()),
                intent.getIntExtra("fase4", dbHandler.getLatestBrood().getFase4()),
                intent.getIntExtra("fase5", dbHandler.getLatestBrood().getFase5()),
                intent.getIntExtra("fase6", dbHandler.getLatestBrood().getFase6()),
                intent.getIntExtra("fase7", dbHandler.getLatestBrood().getFase7()),
                intent.getIntExtra("fase8", dbHandler.getLatestBrood().getFase8()),
                intent.getIntExtra("fase9", dbHandler.getLatestBrood().getFase9()),
                intent.getIntExtra("fase10", dbHandler.getLatestBrood().getFase10())
        );

        timer();
        return START_STICKY;
    }

    private void timer() {
        Runnable runnable = () -> {
            totalTimtPast++;
            progressInSeconds++;
            switch (nextPhase) {
                case 1:
                    if (progressInSeconds == brood.getFase1() * 60) {
                        nextPhase = 2;
                        progressInSeconds = 0;
                    }
                    break;
                case 2:
                    if (progressInSeconds == brood.getFase2() * 60) {
                        nextPhase = 3;
                        progressInSeconds = 0;
                    }
                    break;
                case 3:
                    if (progressInSeconds == brood.getFase3() * 60) {
                        nextPhase = 4;
                        progressInSeconds = 0;
                    }
                    break;
                case 4:
                    if (progressInSeconds == brood.getFase4() * 60) {
                        nextPhase = 5;
                        progressInSeconds = 0;
                    }
                    break;
                case 5:
                    if (progressInSeconds == brood.getFase5() * 60) {
                        nextPhase = 6;
                        progressInSeconds = 0;
                    }
                    break;
                case 6:
                    if (progressInSeconds == brood.getFase6() * 60) {
                        nextPhase = 7;
                        progressInSeconds = 0;
                    }
                    break;
                case 7:
                    if (progressInSeconds == brood.getFase7() * 60) {
                        nextPhase = 8;
                        progressInSeconds = 0;
                    }
                    break;
                case 8:
                    if (progressInSeconds == brood.getFase8() * 60) {
                        nextPhase = 9;
                        progressInSeconds = 0;
                    }
                    break;
                case 9:
                    if (progressInSeconds == brood.getFase9() * 60) {
                        nextPhase = 10;
                        progressInSeconds = 0;
                    }
                    break;
                case 10:
                    if (progressInSeconds == brood.getFase10() *60) {
                        nextPhase = 1;
                        progressInSeconds = 0;
                        stopSelf();
                    }
                    break;

            }
            dbHandler.updateBroodProgress(brood, nextPhase-1, progressInSeconds, totalTimtPast);

        };
        executorService.scheduleAtFixedRate(runnable, 1, 1, TimeUnit.SECONDS);
    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, "Service stopped.", Toast.LENGTH_LONG).show();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
