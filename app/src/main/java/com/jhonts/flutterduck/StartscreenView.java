/*
 * @author John Jairo Castaño Echeverri
 * Copyright (c) <2017> <jjce- ..::jhonts::..>
 */
package com.jhonts.flutterduck;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;

public class StartscreenView extends View{

    private static Bitmap splash = null;
    private static Bitmap play = null;
    private static Bitmap achievements = null;
  //  private static Bitmap leaderBoard = null;
    private static Bitmap speaker = null;
    private static Bitmap info = null;
    private static Bitmap socket = null;
    
    // Button regions: left, top, right, bottom
    private final static float[] REGION_PLAY = {169/720.0f, 400/1280f, 553/720.0f, 510/1280.0f};//l,t,r,d
    private final static float[] REGION_INFO = {585/720.0f, 1141/1280f, 700/720.0f, 1256/1280.0f};
    private final static float[] REGION_SPEAKER = {25/720.0f, 1140/1280f, 140/720.0f, 1255/1280.0f};
    private final static float[] REGION_SOCKET = {233/720.0f, 1149/1280f, 487/720.0f, 1248/1280.0f};
    private final static float[] REGION_ACHIEVEMENT = {283/720.0f, 1050/1280f, 427/720.0f, 1148/1280.0f};
   // private final static float[] REGION_LEADERBOARD = {413/720.0f, 708/1280f, 553/720.0f, 849/1280.0f};
    
    private Rect dstSplash;
    private Rect srcSplash;
    private Rect dstPlay;
    private Rect srcPlay;
    private Rect dstAchievements;
    private Rect srcAchievements;
//    private Rect dstLeaderBoard;
//    private Rect srcLeaderBoard;
    private Rect dstSpeaker;
    private Rect srcSpeaker;
    private Rect dstInfo;
    private Rect srcInfo;
    private Rect dstSocket;
    private Rect srcSocket;
    private Game game = getGame();
    private MainActivity mainActivity;

    public StartscreenView(MainActivity context) {
        super(context);
        this.mainActivity = context;
        if(splash == null) {
            splash = Util.getBitmapAlpha8(mainActivity, R.drawable.splash);
        }
        srcSplash = new Rect(0, 0, splash.getWidth(), splash.getHeight());
        if(play == null) {
            play = Util.getBitmapAlpha8(mainActivity, R.drawable.play_button);
        }
        srcPlay = new Rect(0, 0, play.getWidth(), play.getHeight());
        if(achievements == null) {
            achievements = Util.getBitmapAlpha8(mainActivity, R.drawable.achievement_button);
        }
        srcAchievements = new Rect(0, 0, achievements.getWidth(), achievements.getHeight());
        /*if(leaderBoard == null) {
            leaderBoard = Util.getBitmapAlpha8(mainActivity, R.drawable.highscore_button);
        }
        srcLeaderBoard = new Rect(0, 0, leaderBoard.getWidth(), leaderBoard.getHeight());*/
        if(speaker == null) {
            speaker = Util.getBitmapAlpha8(mainActivity, R.drawable.speaker);
        }
        if(info == null) {
            info = Util.getBitmapAlpha8(mainActivity, R.drawable.about);
        }
        srcInfo = new Rect(0, 0, info.getWidth(), info.getHeight());
        if(socket == null) {
            socket = Util.getBitmapAlpha8(mainActivity, R.drawable.socket);
        }
        setWillNotDraw(false);
        setSpeaker(true);
        setSocket(0);
    }
    
    public void setSpeaker(boolean on) {
        if(on) {
            srcSpeaker = new Rect(0, 0, speaker.getWidth(), speaker.getHeight()/2);
        } else {
            srcSpeaker = new Rect(0, speaker.getHeight()/2, speaker.getWidth(), speaker.getHeight());
        }
    }
    

    public void setSocket(int level) {
        srcSocket = new Rect(0, level*socket.getHeight()/4, socket.getWidth(), (level+1)*socket.getHeight()/4);
    }
    
    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(splash, srcSplash, dstSplash, null);
        canvas.drawBitmap(play, srcPlay, dstPlay, null);
        canvas.drawBitmap(speaker, srcSpeaker, dstSpeaker, null);
        canvas.drawBitmap(info, srcInfo, dstInfo, null);
        canvas.drawBitmap(socket, srcSocket, dstSocket, null);
        canvas.drawBitmap(achievements, srcAchievements, dstAchievements, null);
        //canvas.drawBitmap(leaderBoard, srcLeaderBoard, dstLeaderBoard, null);
    }
    
    @Override
    public boolean performClick() {
        return super.performClick();
    }
    
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        dstSplash = new Rect(0, 0, getWidth(), getHeight());
        dstPlay = new Rect(    (int)(getWidth()*REGION_PLAY[0]),
                            (int)(getHeight()*REGION_PLAY[1]),
                            (int)(getWidth()*REGION_PLAY[2]),
                            (int)(getHeight()*REGION_PLAY[3]));
        dstAchievements = new Rect(    (int)(getWidth()*REGION_ACHIEVEMENT[0]),
                                    (int)(getHeight()*REGION_ACHIEVEMENT[1]),
                                    (int)(getWidth()*REGION_ACHIEVEMENT[2]),
                                    (int)(getHeight()*REGION_ACHIEVEMENT[3]));
        /*dstLeaderBoard = new Rect(    (int)(getWidth()* REGION_LEADERBOARD[0]),
                                    (int)(getHeight()* REGION_LEADERBOARD[1]),
                                    (int)(getWidth()* REGION_LEADERBOARD[2]),
                                    (int)(getHeight()* REGION_LEADERBOARD[3]));*/
        dstSpeaker = new Rect(    (int)(getWidth()*REGION_SPEAKER[0]),
                                (int)(getHeight()*REGION_SPEAKER[1]),
                                (int)(getWidth()*REGION_SPEAKER[2]),
                                (int)(getHeight()*REGION_SPEAKER[3]));
        dstInfo = new Rect(    (int)(getWidth()*REGION_INFO[0]),
                            (int)(getHeight()*REGION_INFO[1]),
                            (int)(getWidth()*REGION_INFO[2]),
                            (int)(getHeight()*REGION_INFO[3]));
        dstSocket = new Rect(    (int)(getWidth()*REGION_SOCKET[0]),
                                (int)(getHeight()*REGION_SOCKET[1]),
                                (int)(getWidth()*REGION_SOCKET[2]),
                                (int)(getHeight()*REGION_SOCKET[3]));
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        performClick();
        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            if(    (event.getX() > REGION_PLAY[0] * getWidth())
                    && (event.getX() < REGION_PLAY[2] * getWidth())
                    && (event.getY() > REGION_PLAY[1] * getHeight())
                    && (event.getY() < REGION_PLAY[3] * getHeight()) ) {
                mainActivity.startActivity(new Intent("com.jhonts.flutterduck.Game"));
            } else if(    (event.getX() > REGION_ACHIEVEMENT[0] * getWidth())
                    && (event.getX() < REGION_ACHIEVEMENT[2] * getWidth())
                    && (event.getY() > REGION_ACHIEVEMENT[1] * getHeight())
                    && (event.getY() < REGION_ACHIEVEMENT[3] * getHeight()) ) {
                new Achievement(this.mainActivity,R.style.ThemeOverlay_AppCompat_Dialog);
            } /*else if(    (event.getX() > REGION_LEADERBOARD[0] * getWidth())
                    && (event.getX() < REGION_LEADERBOARD[2] * getWidth())
                    && (event.getY() > REGION_LEADERBOARD[1] * getHeight())
                    && (event.getY() < REGION_LEADERBOARD[3] * getHeight()) ) {
                //mainActivity.startActivity(new Intent("com.jhonts.flutterduck.LeaderBoard"));//puntuaciones
            } */else if(    (event.getX() > REGION_SPEAKER[0] * getWidth())
                    && (event.getX() < REGION_SPEAKER[2] * getWidth())
                    && (event.getY() > REGION_SPEAKER[1] * getHeight())
                    && (event.getY() < REGION_SPEAKER[3] * getHeight()) ) {
                mainActivity.muteToggle();
            } else if(    (event.getX() > REGION_INFO[0] * getWidth())
                    && (event.getX() < REGION_INFO[2] * getWidth())
                    && (event.getY() > REGION_INFO[1] * getHeight())
                    && (event.getY() < REGION_INFO[3] * getHeight()) ) {
                mainActivity.startActivity(new Intent("com.jhonts.flutterduck.About"));
            }
        }
        return true;
    }

    public Game getGame() {
        return game;
    }
}
