package com.jhonts.flutterduck;
/*
 * The Game
 *
 * @author John Jairo Castaño Echeverri
 * Copyright (c) <2017> <jjce- ..::jhonts::..>
 */
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.apptracker.android.track.AppTracker;
import com.google.android.gms.games.Games;
import com.google.example.games.basegameutils.BaseGameActivity;

public class Game extends BaseGameActivity{
    public static final String coin_save = "coin_save";
    public static final String coin_key = "coin_key";
    public static SoundPool sound =new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
    private static final int GAMES_PER_AD = 3;
    private static int gameOverCounter = 1;
    public static MediaPlayer musicPlayer = null;
    public boolean musicShouldPlay = false;
    private static final long DOUBLE_BACK_TIME = 1000;
    private long backPressed;
    public static MyHandler handler;
    public AccomplishmentBox accomplishmentBox;
    GameView view;
    int coins;
    public int numberOfRevive = 1;
    GameOverDialog gameOverDialog;
    public static boolean game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        accomplishmentBox = new AccomplishmentBox();
        view = new GameView(this);
        gameOverDialog = new GameOverDialog(this);
        handler = new MyHandler(this);
        setContentView(view);
        initMusicPlayer();
        loadCoins();
        if(gameOverCounter % GAMES_PER_AD == 0) {
            AppTracker.loadModule(getApplicationContext(), "inapp");
        }
    }

    public void initMusicPlayer(){
        if(musicPlayer == null){
            musicPlayer = MediaPlayer.create(this, R.raw.nyan_cat_theme);
            musicPlayer.setLooping(true);
            musicPlayer.setVolume(MainActivity.volume, MainActivity.volume);
        }
        musicPlayer.seekTo(0);
    }
    
    private void loadCoins(){
        SharedPreferences saves = this.getSharedPreferences(coin_save, 0);
        this.coins = saves.getInt(coin_key, 0);
    }

    @Override
    protected void onPause() {
        view.pause();
        if(musicPlayer.isPlaying()){
            musicPlayer.pause();
        }
        super.onPause();
    }

    @Override
    protected void onResume() {
        view.drawOnce();
        if(musicShouldPlay){
            musicPlayer.start();
        }
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        if(System.currentTimeMillis() - backPressed < DOUBLE_BACK_TIME){
            super.onBackPressed();
        }else{
            backPressed = System.currentTimeMillis();
            Toast.makeText(this, getResources().getString(R.string.on_back_press), Toast.LENGTH_LONG).show();
        }
    }

    public void gameOver(){
        if(gameOverCounter % GAMES_PER_AD == 0) {
            this.handler.sendMessage(Message.obtain(handler, MyHandler.SHOW_AD));
        } else {
            this.handler.sendMessage(Message.obtain(handler, MyHandler.GAME_OVER_DIALOG));
        }
        
    }
    
    public void increaseCoin(){
        this.coins+=10;
        if(this.coins >= 50 && !this.accomplishmentBox.achievement_50_coins){
            this.accomplishmentBox.achievement_50_coins = true;
            this.handler.sendMessage(Message.obtain(handler,1,R.string.toast_achievement_50_coins, MyHandler.SHOW_TOAST));
        }
    }

    public void increasePoints(){
        this.accomplishmentBox.points++;
        
        this.view.getPlayer().upgradeBitmap(accomplishmentBox.points);
        
        if(this.accomplishmentBox.points >= AccomplishmentBox.BRONZE_POINTS){
            if(!this.accomplishmentBox.achievement_bronze){
                this.accomplishmentBox.achievement_bronze = true;
                this.coins+=25;
                this.handler.sendMessage(Message.obtain(this.handler, MyHandler.SHOW_TOAST, R.string.toast_achievement_bronze, MyHandler.SHOW_TOAST));
                this.view.changeTheme("bronze");
            }
            
            if(this.accomplishmentBox.points >= AccomplishmentBox.SILVER_POINTS){
                if(!this.accomplishmentBox.achievement_silver){
                    this.accomplishmentBox.achievement_silver = true;
                    this.coins+=50;
                    this.handler.sendMessage(Message.obtain(this.handler, MyHandler.SHOW_TOAST, R.string.toast_achievement_silver, MyHandler.SHOW_TOAST));
                    this.view.changeTheme("silver");
                }
                
                if(this.accomplishmentBox.points >= AccomplishmentBox.GOLD_POINTS){
                    if(!this.accomplishmentBox.achievement_gold){
                        this.accomplishmentBox.achievement_gold = true;
                        this.coins+=75;
                        this.handler.sendMessage(Message.obtain(this.handler, MyHandler.SHOW_TOAST, R.string.toast_achievement_gold, MyHandler.SHOW_TOAST));
                        this.view.changeTheme("gold");
                    }
                }
            }
        }
    }

    class MyHandler extends Handler{
        public static final int GAME_OVER_DIALOG = 0;
        public static final int SHOW_TOAST = 1;
        public static final int SHOW_AD = 2;
        
        private Game game;
        
        MyHandler(Game game){
            this.game = game;
        }
        
        @Override
        public void handleMessage(Message msg) {
            switch(msg.what){
                case GAME_OVER_DIALOG:
                    showGameOverDialog();
                    break;
                case SHOW_TOAST:
                    Toast.makeText(game, msg.arg1, Toast.LENGTH_SHORT).show();
                    break;
                case SHOW_AD:
                    AppTracker.loadModule(getApplicationContext(), "inapp");
                    showGameOverDialog();// en caso de que la publicidad no cargue pasa y carga esto
                    break;
            }
        }

        private void showGameOverDialog() {
            ++Game.gameOverCounter;
            game.gameOverDialog.init();
            game.gameOverDialog.show();
        }
    }

    @Override
    public void onSignInFailed() {}

    @Override
    public void onSignInSucceeded() {}

}
