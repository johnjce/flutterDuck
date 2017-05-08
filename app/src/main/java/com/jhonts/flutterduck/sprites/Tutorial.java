/**
 * The tutorial that says you should tap
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

public class Tutorial extends Sprite {
    public static Bitmap globalBitmap;

    public Tutorial(GameView view, Game game) {
        super(view, game);
        if(globalBitmap == null){
            globalBitmap = Util.getScaledBitmapAlpha8(game, R.drawable.tutorial);
        }
        this.bitmap = globalBitmap;
        this.width = this.bitmap.getWidth();
        this.height = this.bitmap.getHeight();
    }

    /**
     * Sets the position to the center of the view.
     */
    @Override
    public void move() {
        this.x = view.getWidth() / 2 - this.width / 2;
        this.y = view.getHeight() / 2 - this.height / 2;
    }
    @Override
    public void changeTheme(int s){ }

}
