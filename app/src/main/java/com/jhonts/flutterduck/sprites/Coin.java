/**
 * A Coin
 * 
 * @author John Jairo Castaño Echeverri
 * Copyright (c) <2017> <jjce- ..::jhonts::..>
 */

package com.jhonts.flutterduck.sprites;

import android.graphics.Bitmap;

import com.jhonts.flutterduck.Game;
import com.jhonts.flutterduck.GameView;
import com.jhonts.flutterduck.MainActivity;
import com.jhonts.flutterduck.R;
import com.jhonts.flutterduck.Util;

public class Coin extends PowerUp {
    /**
     * Static bitmap to reduce memory usage.
     */
    public static Bitmap globalBitmap;
    private static int sound = -1;

    public Coin(GameView view, Game game) {
        super(view, game);
        if(globalBitmap == null){
            globalBitmap = Util.getScaledBitmapAlpha8(game, R.drawable.coin);
        }
        this.bitmap = globalBitmap;
        this.width = this.bitmap.getWidth()/(colNr = 12);
        this.height = this.bitmap.getHeight();
        this.frameTime = 1;
        if(sound == -1){
            sound = Game.sound.load(game, R.raw.coin, 1);
        }
    }

    /**
     * When eaten the player will turn into nyan cat.
     */
    @Override
    public void onCollision() {
        super.onCollision();
        playSound();
        game.increaseCoin();
    }
    
    private void playSound(){
        Game.sound.play(sound, MainActivity.volume, MainActivity.volume, 0, 0, 1);
    }
    
    @Override
    public void move() {
        changeToNextFrame();
        super.move();
    }

    @Override
    public void changeTheme(int s){
        globalBitmap = Util.getDownScaledBitmapAlpha8(game,s);
        this.bitmap = globalBitmap;
    }
}
