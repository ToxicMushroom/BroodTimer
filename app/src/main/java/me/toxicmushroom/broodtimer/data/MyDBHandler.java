package me.toxicmushroom.broodtimer.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import java.sql.PreparedStatement;
import java.util.ArrayList;

import me.toxicmushroom.broodtimer.reminder.PhaseService;

/**
 * Created by Merlijn on 27/12/2017.
 */

public class MyDBHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "broden.db";
    public static final String TABLE_BRODEN = "broden";
    public static final String COLUMN_BROODNAAM = "broodnaam";
    public static final String COLUMN_FASE1 = "fase1";
    public static final String COLUMN_FASE2 = "fase2";
    public static final String COLUMN_FASE3 = "fase3";
    public static final String COLUMN_FASE4 = "fase4";
    public static final String COLUMN_FASE5 = "fase5";
    public static final String COLUMN_FASE6 = "fase6";
    public static final String COLUMN_FASE7 = "fase7";
    public static final String COLUMN_FASE8 = "fase8";
    public static final String COLUMN_FASE9 = "fase9";
    public static final String COLUMN_FASE10 = "fase10";
    public static final String COLUMN_LOADED = "loaded";


    public MyDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_BRODEN + " (" +
                COLUMN_BROODNAAM + " TEXT, " +
                COLUMN_FASE1 + " TEXT, " +
                COLUMN_FASE2 + " TEXT, " +
                COLUMN_FASE3 + " TEXT, " +
                COLUMN_FASE4 + " TEXT, " +
                COLUMN_FASE5 + " TEXT, " +
                COLUMN_FASE6 + " TEXT, " +
                COLUMN_FASE7 + " TEXT, " +
                COLUMN_FASE8 + " TEXT, " +
                COLUMN_FASE9 + " TEXT, " +
                COLUMN_FASE10 + " TEXT, " +
                COLUMN_LOADED + " INTEGER" +
                ");");
        db.execSQL("CREATE TABLE IF NOT EXISTS brodenVooruitgang (" +
                COLUMN_BROODNAAM + " TEXT, currentPhase TEXT, nextPhase TEXT, currentPhaseTime TEXT, untilPhaseTime TEXT, totalTimePast TEXT, totalTimeComming TEXT);");
        db.execSQL("CREATE TABLE IF NOT EXISTS latestBrood" + " (" +
                COLUMN_BROODNAAM + " TEXT, " +
                COLUMN_FASE1 + " TEXT, " +
                COLUMN_FASE2 + " TEXT, " +
                COLUMN_FASE3 + " TEXT, " +
                COLUMN_FASE4 + " TEXT, " +
                COLUMN_FASE5 + " TEXT, " +
                COLUMN_FASE6 + " TEXT, " +
                COLUMN_FASE7 + " TEXT, " +
                COLUMN_FASE8 + " TEXT, " +
                COLUMN_FASE9 + " TEXT, " +
                COLUMN_FASE10 + " TEXT " +
                ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BRODEN);
        onCreate(db);
        db.close();
    }

    //voeg een nieuwe rij toe aan de database
    public void addBrood(Broden brood) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS latestBrood");
        db.execSQL("CREATE TABLE IF NOT EXISTS latestBrood" + " (" +
                COLUMN_BROODNAAM + " TEXT, " +
                COLUMN_FASE1 + " TEXT, " +
                COLUMN_FASE2 + " TEXT, " +
                COLUMN_FASE3 + " TEXT, " +
                COLUMN_FASE4 + " TEXT, " +
                COLUMN_FASE5 + " TEXT, " +
                COLUMN_FASE6 + " TEXT, " +
                COLUMN_FASE7 + " TEXT, " +
                COLUMN_FASE8 + " TEXT, " +
                COLUMN_FASE9 + " TEXT, " +
                COLUMN_FASE10 + " TEXT " +
                ");");
        db.execSQL("INSERT INTO latestBrood (" +
                COLUMN_BROODNAAM + ", " +
                COLUMN_FASE1 + ", " +
                COLUMN_FASE2 + ", " +
                COLUMN_FASE3 + ", " +
                COLUMN_FASE4 + ", " +
                COLUMN_FASE5 + ", " +
                COLUMN_FASE6 + ", " +
                COLUMN_FASE7 + ", " +
                COLUMN_FASE8 + ", " +
                COLUMN_FASE9 + ", " +
                COLUMN_FASE10 +
                ") VALUES ('" +
                brood.get_broodnaam() + "', '" +
                brood.getFase1() + "', '" +
                brood.getFase2() + "', '" +
                brood.getFase3() + "', '" +
                brood.getFase4() + "', '" +
                brood.getFase5() + "', '" +
                brood.getFase6() + "', '" +
                brood.getFase7() + "', '" +
                brood.getFase8() + "', '" +
                brood.getFase9() + "', '" +
                brood.getFase10() +
                "');");
        db.execSQL("INSERT INTO brodenVooruitgang (" +
                COLUMN_BROODNAAM + ", currentPhase, nextPhase, currentPhaseTime, untilPhaseTime, totalTimePast, totalTimeComming) VALUES ('" +
                brood.get_broodnaam() + "', '" +
                0 + "', '" +
                1 + "', '" +
                0 + "', '" +
                brood.getFase1() * 60 + "', '" +
                0 + "', '" +
                (brood.getAllFases() * 60) + "');");
        db.execSQL("INSERT INTO " + TABLE_BRODEN + " (" +
                COLUMN_BROODNAAM + ", " +
                COLUMN_FASE1 + ", " +
                COLUMN_FASE2 + ", " +
                COLUMN_FASE3 + ", " +
                COLUMN_FASE4 + ", " +
                COLUMN_FASE5 + ", " +
                COLUMN_FASE6 + ", " +
                COLUMN_FASE7 + ", " +
                COLUMN_FASE8 + ", " +
                COLUMN_FASE9 + ", " +
                COLUMN_FASE10 + ", " +
                COLUMN_LOADED +
                ") VALUES ('" +
                brood.get_broodnaam() + "', '" +
                brood.getFase1() + "', '" +
                brood.getFase2() + "', '" +
                brood.getFase3() + "', '" +
                brood.getFase4() + "', '" +
                brood.getFase5() + "', '" +
                brood.getFase6() + "', '" +
                brood.getFase7() + "', '" +
                brood.getFase8() + "', '" +
                brood.getFase9() + "', '" +
                brood.getFase10() + "', '" +
                0 + "');");
        db.close();
    }

    //delete een rij van de database
    public void deleteBrood(String broodnaam) {
        SQLiteDatabase db = getWritableDatabase();
        if (db != null) {
            db.execSQL("DELETE FROM " + TABLE_BRODEN + " WHERE " + COLUMN_BROODNAAM + "= '" + broodnaam + "';" +
                    " DELETE FROM brodenVooruitgang WHERE " + COLUMN_BROODNAAM + "= '" + broodnaam + "'");
            db.close();
        }
    }

    public String getData(String database, String column, String broodNaam) {
        String toReturn = null;
        SQLiteDatabase db = getWritableDatabase();
        if (db != null) {
            Cursor c = db.rawQuery("SELECT " + column + " FROM " + database + " WHERE " + COLUMN_BROODNAAM + "= '" + broodNaam + "'", null);
            c.moveToFirst();
            toReturn = c.getString(0);
            c.close();
            db.close();
        }
        return toReturn;
    }

    public Broden getLatestBrood() {
        SQLiteDatabase db = getWritableDatabase();
        Broden broden = null;
        Cursor c = db.rawQuery("SELECT * FROM latestBrood WHERE 1", null);
        c.moveToFirst();
        if (!c.isAfterLast()) {
            broden = new Broden();
            broden.set_broodnaam(c.getString(c.getColumnIndex(COLUMN_BROODNAAM)));
            broden.setFases(
                    c.getInt(c.getColumnIndex(COLUMN_FASE1)),
                    c.getInt(c.getColumnIndex(COLUMN_FASE2)),
                    c.getInt(c.getColumnIndex(COLUMN_FASE3)),
                    c.getInt(c.getColumnIndex(COLUMN_FASE4)),
                    c.getInt(c.getColumnIndex(COLUMN_FASE5)),
                    c.getInt(c.getColumnIndex(COLUMN_FASE6)),
                    c.getInt(c.getColumnIndex(COLUMN_FASE7)),
                    c.getInt(c.getColumnIndex(COLUMN_FASE8)),
                    c.getInt(c.getColumnIndex(COLUMN_FASE9)),
                    c.getInt(c.getColumnIndex(COLUMN_FASE10)));
        }
        c.close();
        db.close();
        return broden;
    }

    public void updateBroodProgress(Broden brood, int currentPhase, int currentPhaseProgress, int totalTimePast) {
        long totalTimeComming = (brood.getAllFases() - totalTimePast);
        int nextPhase = currentPhase + 1;
        int untilNextPhaseTime = 0;
        switch (currentPhase) {
            case 0:
                untilNextPhaseTime = brood.getFase1() * 60 - currentPhaseProgress;
                break;
            case 1:
                untilNextPhaseTime = brood.getFase2() * 60 - currentPhaseProgress;
                break;
            case 2:
                untilNextPhaseTime = brood.getFase3() * 60 - currentPhaseProgress;
                break;
            case 3:
                untilNextPhaseTime = brood.getFase4() * 60 - currentPhaseProgress;
                break;
            case 4:
                untilNextPhaseTime = brood.getFase5() * 60 - currentPhaseProgress;
                break;
            case 5:
                untilNextPhaseTime = brood.getFase6() * 60 - currentPhaseProgress;
                break;
            case 6:
                untilNextPhaseTime = brood.getFase7() * 60 - currentPhaseProgress;
                break;
            case 7:
                untilNextPhaseTime = brood.getFase8() * 60 - currentPhaseProgress;
                break;
            case 8:
                untilNextPhaseTime = brood.getFase9() * 60 - currentPhaseProgress;
                break;
            case 9:
                untilNextPhaseTime = brood.getFase10() * 60 - currentPhaseProgress;
                break;
        }
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE brodenVooruitgang SET " +
                "currentPhase= '" + currentPhase + "', " +
                "nextPhase= '" + nextPhase + "', " +
                "currentPhaseTime= '" + currentPhaseProgress + "', " +
                "untilPhaseTime= '" + untilNextPhaseTime + "', " +
                "totalTimePast= '" + totalTimePast + "', " +
                "totalTimeComming= '" + totalTimeComming + "' WHERE " +
                COLUMN_BROODNAAM + "= '" + brood.get_broodnaam() + "';");

    }

    public ArrayList<Broden> getAlleBroden() {
        SQLiteDatabase db = getWritableDatabase();
        ArrayList<Broden> broden = new ArrayList<>();
        Broden brood;
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_BRODEN, null);
        c.moveToFirst();
        while (c.moveToNext()) {
            if (!c.isAfterLast()) {
                brood = new Broden();
                brood.set_broodnaam(c.getString(c.getColumnIndex(COLUMN_BROODNAAM)));
                brood.setFases(
                        c.getInt(c.getColumnIndex(COLUMN_FASE1)),
                        c.getInt(c.getColumnIndex(COLUMN_FASE2)),
                        c.getInt(c.getColumnIndex(COLUMN_FASE3)),
                        c.getInt(c.getColumnIndex(COLUMN_FASE4)),
                        c.getInt(c.getColumnIndex(COLUMN_FASE5)),
                        c.getInt(c.getColumnIndex(COLUMN_FASE6)),
                        c.getInt(c.getColumnIndex(COLUMN_FASE7)),
                        c.getInt(c.getColumnIndex(COLUMN_FASE8)),
                        c.getInt(c.getColumnIndex(COLUMN_FASE9)),
                        c.getInt(c.getColumnIndex(COLUMN_FASE10)));
                broden.add(brood);
            }
        }
        c.close();
        db.close();
        return broden;
    }

    public ArrayList<Broden> getLoadedBroden() {
        SQLiteDatabase db = getWritableDatabase();
        ArrayList<Broden> broden = new ArrayList<>();
        Broden brood;
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_BRODEN + " WHERE " + COLUMN_LOADED + "= '1'", null);
        c.moveToFirst();
        while (c.moveToNext()) {
            if (!c.isAfterLast()) {
                brood = new Broden();
                brood.set_broodnaam(c.getString(c.getColumnIndex(COLUMN_BROODNAAM)));
                brood.setFases(
                        c.getInt(c.getColumnIndex(COLUMN_FASE1)),
                        c.getInt(c.getColumnIndex(COLUMN_FASE2)),
                        c.getInt(c.getColumnIndex(COLUMN_FASE3)),
                        c.getInt(c.getColumnIndex(COLUMN_FASE4)),
                        c.getInt(c.getColumnIndex(COLUMN_FASE5)),
                        c.getInt(c.getColumnIndex(COLUMN_FASE6)),
                        c.getInt(c.getColumnIndex(COLUMN_FASE7)),
                        c.getInt(c.getColumnIndex(COLUMN_FASE8)),
                        c.getInt(c.getColumnIndex(COLUMN_FASE9)),
                        c.getInt(c.getColumnIndex(COLUMN_FASE10)));
                broden.add(brood);
            }
        }
        c.close();
        db.close();
        return broden;
    }

    public void setLoadedState(String broodNaam, boolean loaded) {
        SQLiteDatabase db = getWritableDatabase();
        final int i = loaded ? 1 : 0;
        String sql = "UPDATE " + TABLE_BRODEN + " SET " + COLUMN_LOADED + "=? WHERE " + COLUMN_BROODNAAM + "=?" ;
        SQLiteStatement statement = db.compileStatement(sql);
        statement.bindLong(1, i);
        statement.bindString(2, broodNaam);
        statement.execute();
        db.close();
    }

    public void updateBrood(String broodNaam, Broden broodje) {
        SQLiteDatabase db = getWritableDatabase();
        String sql = "UPDATE " + TABLE_BRODEN + " SET " +
                COLUMN_BROODNAAM + "=?, " +
                COLUMN_FASE1 + "=?, " +
                COLUMN_FASE2 + "=?, " +
                COLUMN_FASE3 + "=?, " +
                COLUMN_FASE4 + "=?, " +
                COLUMN_FASE5 + "=?, " +
                COLUMN_FASE6 + "=?, " +
                COLUMN_FASE7 + "=?, " +
                COLUMN_FASE8 + "=?, " +
                COLUMN_FASE9 + "=?, " +
                COLUMN_FASE10 + "=? " +
                "WHERE " + COLUMN_BROODNAAM + "=?";
        String sql2 = "UPDATE brodenVooruitgang SET " + COLUMN_BROODNAAM + "=? WHERE " + COLUMN_BROODNAAM + "=?";
        SQLiteStatement statement2 = db.compileStatement(sql2);
        statement2.bindString(1, broodje.get_broodnaam());
        statement2.bindString(2, broodNaam);
        statement2.execute();
        SQLiteStatement statement = db.compileStatement(sql);
        statement.bindString(1, broodje.get_broodnaam());
        statement.bindLong(2, broodje.getFase1());
        statement.bindLong(3, broodje.getFase2());
        statement.bindLong(4, broodje.getFase3());
        statement.bindLong(5, broodje.getFase4());
        statement.bindLong(6, broodje.getFase5());
        statement.bindLong(7, broodje.getFase6());
        statement.bindLong(8, broodje.getFase7());
        statement.bindLong(9, broodje.getFase8());
        statement.bindLong(10, broodje.getFase9());
        statement.bindLong(11, broodje.getFase10());
        statement.bindString(12, broodNaam);
        statement.execute();
        db.close();
    }
}
