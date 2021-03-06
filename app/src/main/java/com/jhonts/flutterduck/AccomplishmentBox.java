package com.jhonts.flutterduck;
/*
 * @author John Jairo Castaño Echeverri
 * Copyright (c) <2017> <jjce- ..::jhonts::..>
 */


import android.app.Activity;
import android.content.SharedPreferences;

public class AccomplishmentBox{
    public static final int GOLD_POINTS = 140;
    public static final int SILVER_POINTS = 70;
    public static final int BRONZE_POINTS = 35;
    public static final String SAVE_NAME = "ACCOMBLISHMENTS";
    public static final String KEY_POINTS = "points";
    public static final String ACHIEVEMENT_KEY_500_COINS = "achievement_survive_5_minutes";
    public static final String ACHIEVEMENT_KEY_TOASTIFICATION = "achievement_toastification";
    public static final String ACHIEVEMENT_KEY_BRONZE = "achievement_bronze";
    public static final String ACHIEVEMENT_KEY_SILVER = "achievement_silver";
    public static final String ACHIEVEMENT_KEY_GOLD = "achievement_gold";

    public int points;
    public boolean achievement_500_coins;
    public boolean achievement_toastification;
    public boolean achievement_bronze;
    public boolean achievement_silver;
    public boolean achievement_gold;

    public void saveLocal(Activity activity){
        SharedPreferences saves = activity.getSharedPreferences(SAVE_NAME, 0);
        SharedPreferences.Editor editor = saves.edit();

        if(points > saves.getInt(KEY_POINTS, 0)){
            editor.putInt(KEY_POINTS, points);
        }
        if(achievement_500_coins){
            editor.putBoolean(ACHIEVEMENT_KEY_500_COINS, true);
        }
        if(achievement_toastification){
            editor.putBoolean(ACHIEVEMENT_KEY_TOASTIFICATION, true);
        }
        if(achievement_bronze){
            editor.putBoolean(ACHIEVEMENT_KEY_BRONZE, true);
        }
        if(achievement_silver){
            editor.putBoolean(ACHIEVEMENT_KEY_SILVER, true);
        }
        if(achievement_gold){
            editor.putBoolean(ACHIEVEMENT_KEY_GOLD, true);
        }
        editor.commit();
    }

    public static AccomplishmentBox getLocal(Activity activity){
        AccomplishmentBox box = new AccomplishmentBox();
        SharedPreferences saves = activity.getSharedPreferences(SAVE_NAME, 0);

        box.points = saves.getInt(KEY_POINTS, 0);
        box.achievement_500_coins = saves.getBoolean(ACHIEVEMENT_KEY_500_COINS, false);
        box.achievement_toastification = saves.getBoolean(ACHIEVEMENT_KEY_TOASTIFICATION, false);
        box.achievement_bronze = saves.getBoolean(ACHIEVEMENT_KEY_BRONZE, false);
        box.achievement_silver = saves.getBoolean(ACHIEVEMENT_KEY_SILVER, false);
        box.achievement_gold = saves.getBoolean(ACHIEVEMENT_KEY_GOLD, false);

        return box;
    }
}