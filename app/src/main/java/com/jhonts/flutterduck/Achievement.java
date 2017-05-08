/**
 * About Activity for credits
 *
 * @author John Jairo Castaño Echeverri
 * Copyright (c) <2017> <jjce- ..::jhonts::..>
 */

package com.jhonts.flutterduck;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.HorizontalScrollView;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;


public class Achievement extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String[] titles = { "#", "Puntos", "medalla", };
        ScrollView sv = new ScrollView(this);
        TableLayout tableLayout = createTableLayout(20, titles);
        HorizontalScrollView hsv = new HorizontalScrollView(this);
        hsv.addView(tableLayout);
        sv.addView(hsv);
        setContentView(sv);

    }

    private TableLayout createTableLayout(int rows, String [] cv) {
        int rowCount = rows;
        int columnCount = cv.length;
        // 1) Create a tableLayout and its params
        TableLayout.LayoutParams tableLayoutParams = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT);
        TableLayout tableLayout = new TableLayout(this);
        tableLayout.setBackgroundColor(Color.BLUE);

        // 2) create tableRow params
        TableRow.LayoutParams tableRowParams = new TableRow.LayoutParams();
        tableRowParams.setMargins(1, 1, 1, 1);
        tableRowParams.weight = 1;

        for (int i = 0; i < rowCount; i++) {
            // 3) create tableRow
            TableRow tableRow = new TableRow(this);
            tableRow.setBackgroundColor(Color.BLACK);

            for (int j= 0; j < columnCount; j++) {
                // 4) create textView
                TextView textView = new TextView(this);
                //  textView.setText(String.valueOf(j));
                textView.setBackgroundColor(Color.WHITE);
                textView.setGravity(Gravity.CENTER);

                int position = Integer.parseInt(Integer.toString(i)+Integer.toString(j));
                if(i==0){
                    textView.setText(cv[j]);
                    textView.setBackgroundColor(Color.BLACK);
                }else {
                    textView.setBackgroundColor(Color.WHITE);
                    textView.setGravity(Gravity.CENTER);
                    //textView.setText(""+position);

                    if(position==23){///para acceder a una posicion determinada
                        textView.setText("ID=23");

                    }
                }

                // 5) add textView to tableRow
                tableRow.addView(textView, tableRowParams);
            }

            // 6) add tableRow to tableLayout
            tableLayout.addView(tableRow, tableLayoutParams);
        }

        return tableLayout;
    }
}