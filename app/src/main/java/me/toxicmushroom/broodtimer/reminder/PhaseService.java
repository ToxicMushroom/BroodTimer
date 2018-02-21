package me.toxicmushroom.broodtimer.reminder;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.media.VolumeProvider;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import me.toxicmushroom.broodtimer.Stuff;
import me.toxicmushroom.broodtimer.data.Broden;
import me.toxicmushroom.broodtimer.data.MyDBHandler;
import me.toxicmushroom.broodtimer.dialogs.DialogNotify;

/**
 * Created by Merlijn on 4/02/2018.
 */

public class PhaseService extends Service {

    public static List<String> paused = new ArrayList<>();
    public static List<String> toStop = new ArrayList<>();
    MyDBHandler dbHandler;

    private ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

    public PhaseService(Broden brood) {
        dbHandler = new MyDBHandler(this, null, null, 0);
    }

    public PhaseService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Broden broodToPass;
        Toast.makeText(this, "Service started.", Toast.LENGTH_LONG).show();
        dbHandler = new MyDBHandler(this, null, null, 0);
        broodToPass = new Broden(intent.getStringExtra("broodnaam"));
        broodToPass.setFases(
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

        timer(broodToPass);
        return START_STICKY;
    }

    HashMap<String, Integer> progress = new HashMap<>();
    HashMap<String, Integer> past = new HashMap<>();
    HashMap<String, Integer> nextFases = new HashMap<>();
    HashMap<String, Integer> rings = new HashMap<>();
    public static HashMap<String, Boolean> finished = new HashMap<>();

    private void timer(Broden brood) {
        progress.put(brood.get_broodnaam(), 0);
        past.put(brood.get_broodnaam(), 0);
        nextFases.put(brood.get_broodnaam(), 1);
        Runnable runnable = () -> {
            if (toStop.contains(brood.get_broodnaam())) {
                toStop.remove(brood.get_broodnaam());
                return;
            }
            int progressInSeconds = progress.get(brood.get_broodnaam());
            int totalTimtPast = past.get(brood.get_broodnaam());
            int nextPhase = nextFases.get(brood.get_broodnaam());
            if (!paused.contains(brood.get_broodnaam())) {
                finished.put(brood.get_broodnaam(), false);
                totalTimtPast++;
                progressInSeconds++;
                switch (nextPhase) {
                    case 1:
                        if (progressInSeconds == brood.getFase1() * 60) {
                            AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                            if (audioManager != null)
                                audioManager.setStreamVolume(AudioManager.STREAM_NOTIFICATION, 100, 0);
                            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                            Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
                            r.play();
                            nextPhase = 2;
                            progressInSeconds = 0;
                            paused.add(brood.get_broodnaam());
                        }
                        break;
                    case 2:
                        if (progressInSeconds == brood.getFase2() * 60) {
                            AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                            if (audioManager != null)
                                audioManager.setStreamVolume(AudioManager.STREAM_NOTIFICATION, 100, 0);
                            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                            Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
                            r.play();
                            nextPhase = 3;
                            progressInSeconds = 0;
                            paused.add(brood.get_broodnaam());
                        }
                        break;
                    case 3:
                        if (progressInSeconds == brood.getFase3() * 60) {
                            AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                            if (audioManager != null)
                                audioManager.setStreamVolume(AudioManager.STREAM_NOTIFICATION, 100, 0);
                            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                            Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
                            r.play();
                            nextPhase = 4;
                            progressInSeconds = 0;
                            paused.add(brood.get_broodnaam());
                        }
                        break;
                    case 4:
                        if (progressInSeconds == brood.getFase4() * 60) {
                            AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                            if (audioManager != null)
                                audioManager.setStreamVolume(AudioManager.STREAM_NOTIFICATION, 100, 0);
                            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                            Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
                            r.play();
                            nextPhase = 5;
                            progressInSeconds = 0;
                            paused.add(brood.get_broodnaam());
                        }
                        break;
                    case 5:
                        if (progressInSeconds == brood.getFase5() * 60) {
                            AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                            if (audioManager != null)
                                audioManager.setStreamVolume(AudioManager.STREAM_NOTIFICATION, 100, 0);
                            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                            Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
                            r.play();
                            nextPhase = 6;
                            progressInSeconds = 0;
                            paused.add(brood.get_broodnaam());
                        }
                        break;
                    case 6:
                        if (progressInSeconds == brood.getFase6() * 60) {
                            AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                            if (audioManager != null)
                                audioManager.setStreamVolume(AudioManager.STREAM_NOTIFICATION, 100, 0);
                            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                            Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
                            r.play();
                            nextPhase = 7;
                            progressInSeconds = 0;
                            paused.add(brood.get_broodnaam());
                        }
                        break;
                    case 7:
                        if (progressInSeconds == brood.getFase7() * 60) {
                            AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                            if (audioManager != null)
                                audioManager.setStreamVolume(AudioManager.STREAM_NOTIFICATION, 100, 0);
                            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                            Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
                            r.play();
                            nextPhase = 8;
                            progressInSeconds = 0;
                            paused.add(brood.get_broodnaam());
                        }
                        break;
                    case 8:
                        if (progressInSeconds == brood.getFase8() * 60) {
                            AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                            if (audioManager != null)
                                audioManager.setStreamVolume(AudioManager.STREAM_NOTIFICATION, 100, 0);
                            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                            Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
                            r.play();
                            nextPhase = 9;
                            progressInSeconds = 0;
                            paused.add(brood.get_broodnaam());
                        }
                        break;
                    case 9:
                        if (progressInSeconds == brood.getFase9() * 60) {
                            AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                            if (audioManager != null)
                                audioManager.setStreamVolume(AudioManager.STREAM_NOTIFICATION, 100, 0);
                            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                            Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
                            r.play();
                            nextPhase = 10;
                            progressInSeconds = 0;
                            paused.add(brood.get_broodnaam());
                        }
                        break;
                    case 10:
                        if (progressInSeconds == brood.getFase10() * 60) {
                            AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                            if (audioManager != null)
                                audioManager.setStreamVolume(AudioManager.STREAM_NOTIFICATION, 100, 0);
                            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                            Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
                            rings.put(brood.get_broodnaam(), 0);
                            Runnable run = () -> {
                                if (rings.get(brood.get_broodnaam()) == 10) return;
                                r.play();
                                rings.put(brood.get_broodnaam(), rings.get(brood.get_broodnaam()) + 1);
                            };
                            executorService.scheduleAtFixedRate(run, 200, 200, TimeUnit.MILLISECONDS);
                            nextPhase = 1;
                            progressInSeconds = 0;
                            paused.add(brood.get_broodnaam());
                            finished.put(brood.get_broodnaam(), true);
                        }
                        break;

                }
            }
            progress.put(brood.get_broodnaam(), progressInSeconds);
            past.put(brood.get_broodnaam(), totalTimtPast);
            nextFases.put(brood.get_broodnaam(), nextPhase);
            dbHandler.updateBroodProgress(brood, nextPhase - 1, progressInSeconds, totalTimtPast);
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
