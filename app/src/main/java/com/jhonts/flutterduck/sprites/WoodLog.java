/**
 * A shopped wodden log
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

public class WoodLog extends Sprite {

    /**
     * Static bitmap to reduce memory usage.
     */
    public static Bitmap globalBitmap;

    public WoodLog(GameView view, Game game, int theme) {
        super(view, game);
        if(theme==0)//por defecto
            setBitmap(R.drawable.log_full,game);
        if(theme==1)//bronce
            setBitmap(R.drawable.blog_full, game);
        if(theme==2)//plata
            setBitmap(R.drawable.slog_full, game);
        if(theme==3)//oro
            setBitmap(R.drawable.glog_full, game);
        this.width = this.bitmap.getWidth();
        this.height = this.bitmap.getHeight();
    }

    private void setBitmap(int b, Game game) {
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

    @Override
    public void changeTheme(int s){
        this.bitmap = Util.getDownScaledBitmapAlpha8(game,s);
    }
}
