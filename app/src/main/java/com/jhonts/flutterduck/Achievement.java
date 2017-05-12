package com.jhonts.flutterduck;
/*
 * About Activity for credits
 *
 * @author John Jairo Castaño Echeverri
 * Copyright (c) <2017> <jjce- ..::jhonts::..>
 */
import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.widget.HorizontalScrollView;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import static com.jhonts.flutterduck.GameOverDialog.best_score_key;
import static com.jhonts.flutterduck.GameOverDialog.score_save_name;


public class Achievement extends Activity {
    private String[] achievementPoints={"50 monedas", "tostada","Nivel: 140","Nivel: 70","Nivel: 35", "Mejor nivel:"};
    private String[] achievements={"50 monedas", "NyanCat", "Medalla de oro", "Medalla de plata","Medalla de bronce", " "};
    private String[] titles = {"Logro", "Necesario", "Obtenido"};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ScrollView sv = new ScrollView(this);
        TableLayout tableLayout = createTableLayout(7);//sumar una para la linea del titulo
        HorizontalScrollView hsv = new HorizontalScrollView(this);
        hsv.addView(tableLayout);
        sv.addView(hsv);
        setContentView(sv);

    }

    private TableLayout createTableLayout(int rows) {
        int columnCount = titles.length;
        TableLayout.LayoutParams tableLayoutParams = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT);
        TableLayout tableLayout = new TableLayout(this);
        tableLayout.setStretchAllColumns(true);

        TableRow.LayoutParams tableRowParams = new TableRow.LayoutParams();
        tableRowParams.setMargins(1, 1, 1, 1);
        tableRowParams.weight = 1;

        String[] achievementsObtains = getAchievementsObtain();

        for (int i = 0; i < rows; i++) {
            TableRow tableRow = new TableRow(this);
            tableRow.setBackgroundColor(Color.LTGRAY);

            for (int j= 0; j < columnCount; j++) {
                TextView textView = new TextView(this);
                textView.setBackgroundColor(Color.WHITE);
                textView.setGravity(Gravity.CENTER);
                if(i==0){
                    textView.setText(titles[j]);
                    textView.setBackgroundColor(Color.BLACK);
                }else if (j==0){
                    textView.setBackgroundColor(Color.WHITE);
                    textView.setGravity(Gravity.CENTER);
                    textView.setText(achievements[i-1]);

                }else if (j==1){
                    textView.setBackgroundColor(Color.WHITE);
                    textView.setGravity(Gravity.START);
                    textView.setText(achievementPoints[i-1]);
                }else{
                    textView.setBackgroundColor(Color.WHITE);
                    textView.setGravity(Gravity.CENTER);
                    textView.setText(achievementsObtains[i-1]);

                }
                tableRow.addView(textView, tableRowParams);
            }
            tableLayout.addView(tableRow, tableLayoutParams);
        }
        return tableLayout;
    }

    private String[] getAchievementsObtain () {
        String[] result = new String[6];

        AccomplishmentBox box = AccomplishmentBox.getLocal(this);
        if(box.achievement_50_coins) result[0] = "sip"; else result[0] = "no50";
        if(box.achievement_toastification) result[1] = "sip"; else result[1] = "nocat";
        if(box.achievement_gold) result[2] = "sip"; else result[2] = "nog";
        if(box.achievement_silver) result[3] = "sip"; else result[3] = "nos";
        if(box.achievement_bronze) result[4] = "sip"; else result[4] = "nob";
        SharedPreferences saves = this.getSharedPreferences(score_save_name, 0);
        result[5] = String.valueOf(saves.getInt(best_score_key, 0));
        return result;
    }
}