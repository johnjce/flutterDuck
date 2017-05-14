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
    public static int medals;
    private Activity activity;
    String[] achievementPoints  = new String[6];
    String[] achievements  = new String[6];
    String[] titles  = new String[3];

    Achievement(final Activity activity, int s) {
        super(activity,s);
        this.activity = activity;
        initArrays(this.activity);
        TableLayout logrosView = createTableLayout();
        this.setContentView(logrosView);
        this.setCancelable(true);
        this.show();
    }

    private void initArrays(Activity activit){
        this.achievementPoints[0] = activit.getString(R.string.achievement_500_coins);
        this.achievementPoints[1] = activit.getString(R.string.achievement_toas);
        this.achievementPoints[2] = 140+activit.getString(R.string.achievement_points);
        this.achievementPoints[3] = 70+activit.getString(R.string.achievement_points);
        this.achievementPoints[4] = 35+activit.getString(R.string.achievement_points);
        this.achievementPoints[5] = best_score + activit.getString(R.string.achievement_points);

        this.achievements[0] = activit.getString(R.string.achievement_500_coins);
        this.achievements[1] = activit.getString(R.string.achievement_nyancat);
        this.achievements[2] = activit.getString(R.string.achievement_medail_gold);
        this.achievements[3] = activit.getString(R.string.achievement_medail_silver);
        this.achievements[4] = activit.getString(R.string.achievement_medail_bronze);
        this.achievements[5] = activit.getString(R.string.achievement_best_points);

        this.titles[0] = activit.getString(R.string.achievement_achievement);
        this.titles[1] = activit.getString(R.string.achievement_needed);
        this.titles[2] = " ";
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
                    textView.setBackgroundColor(Color.DKGRAY);
                    textView.setTextColor(Color.WHITE);
                    textView.setGravity(Gravity.CENTER);
                    textView.setText(titles[j]);
                }else if (j==0){
                    if(i!=6) textView.setBackgroundColor(Color.WHITE);
                    textView.setGravity(Gravity.START);
                    textView.setText(achievements[i-1]);

                }else if (j==1){
                    textView.setBackgroundColor(Color.WHITE);
                    textView.setGravity(Gravity.START);
                    textView.setText(achievementPoints[i - 1]);
                }else{
                    textView.setBackgroundColor(Color.WHITE);
                    textView.setGravity(Gravity.CENTER);
                    if(i!=6)textView.setBackground(achievementsObtains[i - 1]);

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
        if(box.achievement_500_coins){
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

