/**
 * 
 * @author John Jairo Castaño Echeverri
 * Copyright (c) <2017> <jjce- ..::jhonts::..>
 */

package com.jhonts.flutterduck.sprites;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.jhonts.flutterduck.Game;
import com.jhonts.flutterduck.GameView;
import com.jhonts.flutterduck.MainActivity;
import com.jhonts.flutterduck.R;
import com.jhonts.flutterduck.Util;

public class Player extends PlayableCharacter {
    
    private static final int POINTS_TO_SIR = 23;
    private static final int POINTS_TO_COOL = 35;

    /** Static bitmap to reduce memory usage. */
    public static Bitmap globalBitmap;

    /** The moo sound */
    private static int sound = -1;
    
    /** sunglasses, hats and stuff */
    private Accessory accessory;

    public Player(GameView view, Game game) {
        super(view, game);
        if(globalBitmap == null){
            globalBitmap = Util.getScaledBitmapAlpha8(game, R.drawable.duck);
        }
        this.bitmap = globalBitmap;
        this.width = this.bitmap.getWidth()/(colNr = 9);    // The image has 9 frames in a row
        this.height = this.bitmap.getHeight()/4;            // and 4 in a column
        this.frameTime = 4;        // the frame will change every 4 runs
        this.y = game.getResources().getDisplayMetrics().heightPixels / 2;    // Startposition in in the middle of the screen
        
        if(sound == -1){
            sound = Game.sound.load(game, R.raw.duck, 1);
        }
        
        this.accessory = new Accessory(view, game);
    }
    
    private void playSound(){
        Game.sound.play(sound, MainActivity.volume, MainActivity.volume, 0, 0, 1);
    }

    @Override
    public void onTap(){
        super.onTap();
        playSound();
    }
    
    /**
     * Calls super.move
     * and manages the frames. (flattering cape)
     */
    @Override
    public void move(){
        changeToNextFrame();
        super.move();
        
        // manage frames
        if(row != 3){
            // not dead
            if(speedY > getTabSpeed() / 3 && speedY < getMaxSpeed() * 1/3){
                row = 0;
            }else if(speedY > 0){
                row = 1;
            }else{
                row = 2;
            }
        }
        
        if(this.accessory != null){
            this.accessory.moveTo(this.x, this.y);
        }
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if(this.accessory != null && !isDead){
            this.accessory.draw(canvas);
        }
    }

    /**
     * Calls super.dead
     * And changes the frame to a dead duck -.-
     */
    @Override
    public void dead() {
        this.row = 3;
        this.frameTime = 3;
        super.dead();
    }
    
    @Override
    public void revive() {
        super.revive();
        this.accessory.setBitmap(Util.getScaledBitmapAlpha8(game, R.drawable.accessory_scumbag));
    }

    @Override
    public void upgradeBitmap(int points) {
        super.upgradeBitmap(points);
        if(points == POINTS_TO_SIR){
            this.accessory.setBitmap(Util.getScaledBitmapAlpha8(game, R.drawable.accessory_sir));
        }else if(points == POINTS_TO_COOL){
            this.accessory.setBitmap(Util.getScaledBitmapAlpha8(game, R.drawable.accessory_sunglasses));
        }
    }

    @Override
    public void changeTheme(int s){
        globalBitmap = Util.getDownScaledBitmapAlpha8(game,s);
        this.bitmap = globalBitmap;
    }
    
}
