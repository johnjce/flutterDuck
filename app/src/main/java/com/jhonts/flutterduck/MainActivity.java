package com.jhonts.flutterduck;
/*
  Main Activity / Splashscreen with buttons.

  @author John Jairo Castaño Echeverri
 * Copyright (c) <2017> <jjce- ..::jhonts::..>
 */

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.apptracker.android.listener.AppModuleListener;
import com.apptracker.android.track.AppTracker;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.example.games.basegameutils.BaseGameActivity;

import static com.jhonts.flutterduck.GameOverDialog.best_score_key;
import static com.jhonts.flutterduck.GameOverDialog.score_save_name;

public class MainActivity extends BaseGameActivity {
    /**----------------publicidad------------------**/
    private static final String APP_API_KEY 		    = "pE5rOyLUvgj8s1StgNAhekGCYsQL5V9i"; //<-real - prueba-> // "dAICGF8bVShbB7rYTaQs9vI7gLloSI1l"; // change this to your App specific API KEY
    /*----------------publicidad------------------**/

    public static final String medaille_save = "medaille_save";
    public static final String coins = "coins";
    public static final String medaille_key = "medaille_key";
    public static final float DEFAULT_VOLUME = 0.3f;
    public static float volume = DEFAULT_VOLUME;
    private StartscreenView view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = new StartscreenView(this);
        SharedPreferences saves = this.getSharedPreferences(score_save_name, 0);
        Achievement.best_score = saves.getInt(best_score_key, 0); //mejor puntaje en variable de pakage
        saves = this.getSharedPreferences(medaille_key, 0);
        Achievement.medals = saves.getInt(medaille_key, 0);
        setContentView(view);
        setSocket();
        if(savedInstanceState == null){
            AppTracker.setModuleListener(leadboltListener);
            AppTracker.startSession(getApplicationContext(), APP_API_KEY);
        }
    }

    /**----------------publicidad------------------**/
    private AppModuleListener leadboltListener = new AppModuleListener() {
        @Override
        public void onModuleCached(final String placement) {

        }
        @Override
        public void onModuleClicked(String placement) {
            Log.i("AppTracker", "Ad clicked by user - "+ placement);
           // SharedPreferences saves = getSharedPreferences(coins, 0);
            //view.setSocket(saves.getInt(coins, 0));
        }

        @Override
        public void onModuleClosed(final String placement) {}

        @Override
        public void onModuleFailed(String placement, String error, boolean isCache) { }

        @Override
        public void onModuleLoaded(String s) {

            // Add code here to pause game and/or all media including audio
        }

        @Override
        public void onMediaFinished(boolean b) { }
    };
    /**----------------publicidad------------------**/


    public GoogleApiClient getApiClient(){
        return mHelper.getApiClient();
    }

    public void muteToggle() {
        if(volume != 0){
            volume = 0;
            view.setSpeaker(false);
        }else{
            volume = DEFAULT_VOLUME;
            view.setSpeaker(true);
        }
        view.invalidate();
    }

    private void setSocket(){
        SharedPreferences saves = this.getSharedPreferences(medaille_save, 0);
        view.setSocket(saves.getInt(medaille_key, 0));
        view.invalidate();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setSocket();
    }

    @Override
    public void onSignInFailed() { }

    @Override
    public void onSignInSucceeded() { }
    
}
