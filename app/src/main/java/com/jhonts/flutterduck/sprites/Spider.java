/**
 * A spider with web
 * 
 * BTW Spiders have 8 eyes.
 * 
 * @author John Jairo Castaño Echeverri
 * Copyright (c) <2017> <jjce- ..::jhonts::..>
 */
package com.jhonts.flutterduck.sprites;

import android.graphics.Bitmap;

import com.jhonts.flutterduck.Game;
import com.jhonts.flutterduck.GameView;
import com.jhonts.flutterduck.R;
import com.jhonts.flutterduck.Util;

public class Spider extends Sprite {

    private static Bitmap globalBitmap;

    /**
     * Static bitmap to reduce memory usage.
     */

    public Spider(GameView view, Game game, int theme) {
        super(view, game);
        if(theme==0)//por defecto
            setBitmap(R.drawable.spider_full,game);
        else if(theme==1)//bronce
            setBitmap(R.drawable.bspider_full, game);
        else if(theme==2)//plata
            setBitmap(R.drawable.sspider_full, game);
        else if(theme==3)//oro
            setBitmap(R.drawable.gspider_full, game);
        this.width = this.bitmap.getWidth();
        this.height = this.bitmap.getHeight();
    }
    @Override
    public void changeTheme(int s) {
        setBitmap(R.drawable.bspider_full, game);
    }

    public void setBitmap(int b, Game game){
        globalBitmap = Util.getScaledBitmapAlpha8(game, b);
        this.bitmap = globalBitmap;
    }
    /**
     * Sets the position
     * @param x
     * @param y
     */
    public void init(int x, int y){
        this.x = x;
        this.y = y;
    }
}
