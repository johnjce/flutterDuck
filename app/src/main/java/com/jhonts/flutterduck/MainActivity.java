package com.jhonts.flutterduck;
/*
  Main Activity / Splashscreen with buttons.

  @author John Jairo Castaño Echeverri
 * Copyright (c) <2017> <jjce- ..::jhonts::..>
 */

import android.content.SharedPreferences;
import android.os.Bundle;

import com.apptracker.android.listener.AppModuleListener;
import com.apptracker.android.track.AppTracker;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.example.games.basegameutils.BaseGameActivity;

public class MainActivity extends BaseGameActivity {
    /**----------------publicidad------------------**/
    private static final String APP_API_KEY 		    = "pE5rOyLUvgj8s1StgNAhekGCYsQL5V9i"; // change this to your App specific API KEY
    private static final String LOCATION_CODE		    = "inapp";
    /*----------------publicidad------------------**/



    public static final String medaille_save = "medaille_save";
    public static final String medaille_key = "medaille_key";
    public static final float DEFAULT_VOLUME = 0.3f;
    public static float volume = DEFAULT_VOLUME;
    private StartscreenView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = new StartscreenView(this);
        setContentView(view);
        setSocket();
        if(savedInstanceState == null){
            AppTracker.setModuleListener(leadboltListener);
            AppTracker.startSession(getApplicationContext(), APP_API_KEY);
            //creando cache de publicidad
            AppTracker.destroyModule();
            AppTracker.loadModuleToCache(getApplicationContext(), LOCATION_CODE);
        }
    }

    /**----------------publicidad------------------**/
    private AppModuleListener leadboltListener = new AppModuleListener() {
        @Override
        public void onModuleCached(final String placement) { }
        @Override
        public void onModuleClicked(String placement) { }

        @Override
        public void onModuleClosed(final String placement) { runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //creando cache de publicidad
                AppTracker.destroyModule();
                AppTracker.loadModuleToCache(getApplicationContext(), LOCATION_CODE);
            }
        }); }

        @Override
        public void onModuleFailed(String placement, String error, boolean isCache) { }

        @Override
        public void onModuleLoaded(String s) { }

        @Override
        public void onMediaFinished(boolean b) {
            //creando cache de publicidad
            AppTracker.destroyModule();
            AppTracker.loadModuleToCache(getApplicationContext(), LOCATION_CODE);
        }
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
