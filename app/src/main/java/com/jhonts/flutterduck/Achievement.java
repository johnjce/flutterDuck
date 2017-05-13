package com.jhonts.flutterduck;
/*
 * About Activity for credits
 *
 * @author John Jairo Castaño Echeverri
 * Copyright (c) <2017> <jjce- ..::jhonts::..>
 */
import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;


public class Achievement extends Dialog {
    public static int best_score;
    private String[] achievementPoints={"500 monedas", "tostada","140 Pts","70 Pts","35 Pts", "Mejor Puntaje:"};
    private String[] achievements={"500 monedas", "NyanCat", "Medalla de oro", "Medalla de plata","Medalla de bronce", " "};
    private String[] titles = {"Logro", "Necesario", " "};
    private Activity activity;

    Achievement(final Activity activity, int s) {
        super(activity,s);
        this.activity = activity;
        TableLayout logrosView = createTableLayout();
        this.setContentView(logrosView);
        this.setCancelable(true);
        this.show();
    }

    private TableLayout createTableLayout() {
        int columnCount = titles.length;
        TableLayout tableLayout = new TableLayout(this.getContext());
        tableLayout.setGravity(Gravity.CENTER);
        tableLayout.setBackgroundColor(Color.TRANSPARENT);
        tableLayout.setOrientation(LinearLayout.HORIZONTAL);
        tableLayout.setLayoutParams(new WindowManager.LayoutParams());
        tableLayout.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT));

        tableLayout.setStretchAllColumns(true);

        TableRow.LayoutParams tableRowParams = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
        tableRowParams.setMargins(1, 1, 1, 1);
        tableRowParams.weight = 1;

        Drawable[] achievementsObtains = getAchievementsObtain();

        for (int i = 0; i < 7; i++) {
            TableRow tableRow = new TableRow(this.getContext());

            for (int j= 0; j < columnCount; j++) {
                TextView textView = new TextView(this.getContext());
                if(i==0){
                    textView.setText(titles[j]);
                    textView.setBackgroundColor(Color.DKGRAY);
                    textView.setGravity(Gravity.CENTER);
                }else if (j==0){
                    if(i!=6) textView.setBackgroundColor(Color.WHITE);
                    textView.setGravity(Gravity.CENTER);
                    textView.setText(achievements[i-1]);

                }else if (j==1){
                    textView.setBackgroundColor(Color.WHITE);
                    textView.setGravity(Gravity.START);
                    textView.setText(achievementPoints[i-1]);
                }else{
                    textView.setBackgroundColor(Color.WHITE);
                    textView.setGravity(Gravity.CENTER);
                    if(i==6){
                        textView.setText(best_score+" Pts" );
                    }else{
                        textView.setBackground(achievementsObtains[i - 1]);
                    }
                }
                tableRow.addView(textView, tableRowParams);
            }
            tableLayout.addView(tableRow);
        }
        return tableLayout;
    }

    private Drawable[] getAchievementsObtain () {
        Drawable[] result = new Drawable[5];

        AccomplishmentBox box = AccomplishmentBox.getLocal(this.activity);
        if(box.achievement_50_coins){
            result[0] = getContext().getResources().getDrawable(R.drawable.check);
        } else {
            result[0] = getContext().getResources().getDrawable( R.drawable.uncheck);
        }
        if(box.achievement_toastification){
            result[1] = getContext().getResources().getDrawable( R.drawable.check);
        } else {
            result[1] = getContext().getResources().getDrawable( R.drawable.uncheck);
        }
        if(box.achievement_gold){
            result[2] = getContext().getResources().getDrawable( R.drawable.check);
        } else {
            result[2] = getContext().getResources().getDrawable( R.drawable.uncheck);
        }
        if(box.achievement_silver){
            result[3] = getContext().getResources().getDrawable( R.drawable.check);
        } else {
            result[3] = getContext().getResources().getDrawable( R.drawable.uncheck);
        }
        if(box.achievement_bronze){
            result[4] = getContext().getResources().getDrawable( R.drawable.check);
        } else {
            result[4] = getContext().getResources().getDrawable( R.drawable.uncheck);
        }
        return result;
    }
}

