/**
 * An obstacle: spider + logHead
 * 
 * @author John Jairo Castaño Echeverri
 * Copyright (c) <2017> <jjce- ..::jhonts::..>
 */

package com.jhonts.flutterduck.sprites;

import android.graphics.Canvas;

import com.jhonts.flutterduck.Game;
import com.jhonts.flutterduck.GameView;
import com.jhonts.flutterduck.MainActivity;
import com.jhonts.flutterduck.R;

public class Obstacle extends Sprite{
    private Spider spider;
    private WoodLog log;
    
    private static int collideSound = -1;
    private static int passSound = -1;
    
    /** Necessary so the onPass method is just called once */
    public boolean isAlreadyPassed = false;

    public Obstacle(GameView view, Game game, int theme) {
        super(view, game);
        spider = new Spider(view, game, theme);
        log = new WoodLog(view, game, theme);
        
        if(collideSound == -1){
            collideSound = Game.sound.load(game, R.raw.crash, 1);
        }
        if(passSound == -1){
            passSound = Game.sound.load(game, R.raw.pass, 1);
        }
        
        initPos();
    }
    
    /**
     * Creates a spider and a wooden log at the right of the screen.
     * With a certain gap between them.
     * The vertical position is in a certain area random.
     */
    private void initPos(){
        int height = game.getResources().getDisplayMetrics().heightPixels;
        int gab = height / 4 - view.getSpeedX();
        if(gab < height / 5){
            gab = height / 5;
        }
        int random = (int) (Math.random() * height * 2 / 5);
        int y1 = (height / 10) + random - spider.height;
        int y2 = (height / 10) + random + gab;
        
        spider.init(game.getResources().getDisplayMetrics().widthPixels, y1);
        log.init(game.getResources().getDisplayMetrics().widthPixels, y2);
    }

    /**
     * Draws spider and log.
     */
    @Override
    public void draw(Canvas canvas) {
        spider.draw(canvas);
        log.draw(canvas);
    }

    /**
     * Checks whether both, spider and log, are out of range.
     */
    @Override
    public boolean isOutOfRange() {
        return spider.isOutOfRange() && log.isOutOfRange();
    }

    /**
     * Checks whether the spider or the log is colliding with the sprite.
     */
    @Override
    public boolean isColliding(Sprite sprite) {
        return spider.isColliding(sprite) || log.isColliding(sprite);
    }

    /**
     * Moves both, spider and log.
     */
    @Override
    public void move() {
        spider.move();
        log.move();
    }

    /**
     * Sets the speed of the spider and the log.
     */
    @Override
    public void setSpeedX(float speedX) {
        spider.setSpeedX(speedX);
        log.setSpeedX(speedX);
    }
    
    /**
     * Checks whether the spider and the log are passed.
     */
    @Override
    public boolean isPassed(){
        return spider.isPassed() && log.isPassed();
    }
    
    /**
     * Will call obstaclePassed of the game, if this is the first pass of this obstacle.
     */
    public void onPass(){
        if(!isAlreadyPassed){
            isAlreadyPassed = true;
            view.getGame().increasePoints();
            Game.sound.play(passSound, MainActivity.volume, MainActivity.volume, 0, 0, 1);
        }
    }

    @Override
    public void onCollision() {
        super.onCollision();
        Game.sound.play(collideSound, MainActivity.volume, MainActivity.volume, 0, 0, 1);
    }

    @Override
    public void changeTheme(int s){
        spider.changeTheme(R.drawable.bspider_full);
        log.changeTheme(R.drawable.blog_full);
    }

}
