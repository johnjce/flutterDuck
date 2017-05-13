package com.jhonts.flutterduck;
/*
 * @author John Jairo Castaño Echeverri
 * Copyright (c) <2017> <jjce- ..::jhonts::..>
 */

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Message;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.google.android.gms.games.Games;
import com.jhonts.flutterduck.Game.MyHandler;
import com.jhonts.flutterduck.sprites.Background;
import com.jhonts.flutterduck.sprites.Coin;
import com.jhonts.flutterduck.sprites.Frontground;
import com.jhonts.flutterduck.sprites.NyanCat;
import com.jhonts.flutterduck.sprites.Obstacle;
import com.jhonts.flutterduck.sprites.PauseButton;
import com.jhonts.flutterduck.sprites.PlayableCharacter;
import com.jhonts.flutterduck.sprites.Player;
import com.jhonts.flutterduck.sprites.PowerUp;
import com.jhonts.flutterduck.sprites.Toast;
import com.jhonts.flutterduck.sprites.Tutorial;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class GameView extends SurfaceView{

    public static final long UPDATE_INTERVAL = 50;        // = 20 FPS

    private Timer timer = new Timer();
    private TimerTask timerTask;

    private SurfaceHolder holder;

    private Game game;
    private PlayableCharacter player;
    private Background background;
    private Frontground frontground;
    private List<Obstacle> obstacles = new ArrayList<Obstacle>();
    private List<PowerUp> powerUps = new ArrayList<PowerUp>();
    private int theme;

    private PauseButton pauseButton;
    volatile private boolean paused = true;

    private Tutorial tutorial;
    private boolean tutorialIsShown = true;

    public GameView(Context context) {
        super(context);
        this.game = (Game) context;
        setFocusable(true);

        holder = getHolder();
        player = new Player(this, game);
        background = new Background(this, game);
        frontground = new Frontground(this, game);
        pauseButton = new PauseButton(this, game);
        tutorial = new Tutorial(this, game);
        setTheme(0);
    }

    private void startTimer() {
        setUpTimerTask();
        timer = new Timer();
        timer.schedule(timerTask, UPDATE_INTERVAL, UPDATE_INTERVAL);
    }

    private void stopTimer() {
        if (timer != null) {
            timer.cancel();
            timer.purge();
        }
        if (timerTask != null) {
            timerTask.cancel();
        }
    }

    private void setUpTimerTask() {
        stopTimer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                GameView.this.run();
            }
        };
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        performClick();
        if(event.getAction() == MotionEvent.ACTION_DOWN && !this.player.isDead()){
            if(tutorialIsShown){
                tutorialIsShown = false;
                resume();
                this.player.onTap();
            }else if(paused){
                resume();
            }else if(pauseButton.isTouching((int) event.getX(), (int) event.getY()) && !this.paused){
                pause();
            }else{
                this.player.onTap();
            }
        }
        return true;
    }

    public void run() {
        checkPasses();
        checkOutOfRange();
        checkCollision();
        createObstacle();
        move();
        draw();
    }

    public void showTutorial(){
        player.move();
        pauseButton.move();

        while(!holder.getSurface().isValid()){
            try { Thread.sleep(10); } catch (InterruptedException e) { e.printStackTrace(); }
        }

        Canvas canvas = holder.lockCanvas();
        drawCanvas(canvas, true);
        tutorial.move();
        tutorial.draw(canvas);
        holder.unlockCanvasAndPost(canvas);
    }

    public void pause(){
        stopTimer();
        paused = true;
    }

    public void drawOnce(){
        (new Thread(new Runnable() {
            @Override
            public void run() {
                if(tutorialIsShown){
                    showTutorial();
                } else {
                    draw();
                }
            }
        })).start();
    }

    public void resume(){
        paused = false;
        startTimer();
    }

    private void draw() {
        while(!holder.getSurface().isValid()){
            try { Thread.sleep(10); } catch (InterruptedException e) { e.printStackTrace(); }
        }
        Canvas canvas = holder.lockCanvas();
        drawCanvas(canvas, true);
        holder.unlockCanvasAndPost(canvas);
    }

    private void drawCanvas(Canvas canvas, boolean drawPlayer){
        background.draw(canvas);
        for(Obstacle r : obstacles){
            r.draw(canvas);
        }
        for(PowerUp p : powerUps){
            p.draw(canvas);
        }
        if(drawPlayer){
            player.draw(canvas);
        }
        frontground.draw(canvas);
        pauseButton.draw(canvas);

        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(getScoreTextMetrics());
        canvas.drawText(game.getResources().getString(R.string.onscreen_score_text) + " " + game.accomplishmentBox.points
                        + " / " + game.getResources().getString(R.string.onscreen_coin_text) + " " + game.coins,
                0, getScoreTextMetrics(), paint);
    }

    private void playerDeadFall(){
        player.dead();
        do{
            player.move();
            draw();
            try { Thread.sleep(UPDATE_INTERVAL/4); } catch (InterruptedException e) { e.printStackTrace(); }
        }while(!player.isTouchingGround());
    }

    private void checkPasses(){
        for(Obstacle o : obstacles){
            if(o.isPassed()){
                if(!o.isAlreadyPassed){
                    o.onPass();
                    createPowerUp();
                }
            }
        }
    }

    private void createPowerUp(){
        if(game.accomplishmentBox.points >= Toast.POINTS_TO_TOAST && !(player instanceof NyanCat)){
            if(game.accomplishmentBox.points == Toast.POINTS_TO_TOAST){
                powerUps.add(new Toast(this, game));
            } else if(Math.random()*100 < 33){
                powerUps.add(new Toast(this, game));
            }
        }

        if((powerUps.size() < 1) && (Math.random()*100 < 20)){
            powerUps.add(new Coin(this, game));
        }
    }

    private void checkOutOfRange(){
        for(int i=0; i<obstacles.size(); i++){
            if(this.obstacles.get(i).isOutOfRange()){
                this.obstacles.remove(i);
                i--;
            }
        }
        for(int i=0; i<powerUps.size(); i++){
            if(this.powerUps.get(i).isOutOfRange()){
                this.powerUps.remove(i);
                i--;
            }
        }
    }

    private void checkCollision(){
        for(Obstacle o : obstacles){
            if(o.isColliding(player)){
                o.onCollision();
                gameOver();
            }
        }
        for(int i=0; i<powerUps.size(); i++){
            if(this.powerUps.get(i).isColliding(player)){
                this.powerUps.get(i).onCollision();
                this.powerUps.remove(i);
                i--;
            }
        }
        if(player.isTouchingEdge()){
            gameOver();
        }
    }

    private void createObstacle(){
        if(obstacles.size() < 1){
            obstacles.add(new Obstacle(this, game, getTheme()));
        }
    }

    private void move(){
        for(Obstacle o : obstacles){
            o.setSpeedX(-getSpeedX());
            o.move();
        }
        for(PowerUp p : powerUps){
            p.move();
        }

        background.setSpeedX(-getSpeedX()/2);
        background.move();

        frontground.setSpeedX(-getSpeedX()*4/3);
        frontground.move();

        pauseButton.move();

        player.move();
    }

    public void changeToNyanCat(){
        game.accomplishmentBox.achievement_toastification = true;
        game.handler.sendMessage(Message.obtain(game.handler,1,R.string.toast_achievement_toastification, MyHandler.SHOW_TOAST));

        PlayableCharacter tmp = this.player;
        this.player = new NyanCat(this, game);
        this.player.setX(tmp.getX());
        this.player.setY(tmp.getY());
        this.player.setSpeedX(tmp.getSpeedX());
        this.player.setSpeedY(tmp.getSpeedY());

        game.musicShouldPlay = true;
        Game.musicPlayer.start();
    }

    public void changeToDuck(){
        game.accomplishmentBox.achievement_toastification = false;

        PlayableCharacter tmp = this.player;
        this.player = new Player(this, game);
        this.player.setX(tmp.getX());
        this.player.setY(tmp.getY());
        this.player.setSpeedX(tmp.getSpeedX());
        this.player.setSpeedY(tmp.getSpeedY());

        game.musicShouldPlay = true;
        Game.musicPlayer.stop();
    }

    public int getSpeedX(){
        int speedDefault = this.getWidth() / 45;
        int speedIncrease = (int) (this.getWidth() / 600f * (game.accomplishmentBox.points / 4));
        int speed = speedDefault + speedIncrease;
        return Math.min(speed, 2*speedDefault);
    }

    public void gameOver(){
        pause();
        playerDeadFall();
        game.gameOver();
    }

    public void revive() {
        game.numberOfRevive++;
        new Thread(new Runnable() {
            @Override
            public void run() {
                setupRevive();
            }
        }).start();
    }

    private void setupRevive(){
        game.gameOverDialog.hide();
        player.setY(this.getHeight()/2 - player.getWidth()/2);
        player.setX(this.getWidth()/6);
        obstacles.clear();
        powerUps.clear();
        player.revive();
        for(int i = 0; i < 6; ++i){
            while(!holder.getSurface().isValid()){/*wait*/}
            Canvas canvas = holder.lockCanvas();
            drawCanvas(canvas, i%2 == 0);
            holder.unlockCanvasAndPost(canvas);
            try { Thread.sleep(UPDATE_INTERVAL*6); } catch (InterruptedException e) { e.printStackTrace(); }
        }
        resume();
    }

    public int getScoreTextMetrics(){
        return (int) (this.getHeight() / 21.0f);
    }

    public PlayableCharacter getPlayer(){
        return this.player;
    }

    public Game getGame(){
        return this.game;
    }

    public int getTheme(){return this.theme; }

    public void setTheme(int a){ this.theme=a; }

    public void changeTheme(String medal) {//aqui cambio el fondo y los obstaculos al cambiar de nivel
        if (medal == "gold") {
            background.changeTheme(R.drawable.gbg);
            frontground.changeTheme(R.drawable.gfg);
            setTheme(3);
        }else if (medal == "silver") {
            background.changeTheme(R.drawable.sbg);
            frontground.changeTheme(R.drawable.sfg);
            setTheme(2);
        }else if (medal == "bronze"){
            background.changeTheme(R.drawable.bbg);
            frontground.changeTheme(R.drawable.bfb);
            setTheme(1);
        }
    }
}