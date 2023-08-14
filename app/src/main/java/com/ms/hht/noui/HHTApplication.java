package com.ms.hht.noui;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ms.hht.ui.home.HomeScreen;

public class HHTApplication extends Application implements Application.ActivityLifecycleCallbacks {
    private boolean homeScreenAvailableBack =false;
    @Override
    public void onCreate() {
        super.onCreate();
        // Register to be notified of activity state changes
        registerActivityLifecycleCallbacks(this);
    }

    @Override
    public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {

    }

    @Override
    public void onActivityStarted(@NonNull Activity activity) {

    }

    @Override
    public void onActivityResumed(@NonNull Activity activity) {
        if(activity instanceof HomeScreen)
            homeScreenAvailableBack = true;
    }

    @Override
    public void onActivityPaused(@NonNull Activity activity) {

    }

    @Override
    public void onActivityStopped(@NonNull Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(@NonNull Activity activity) {
        if(activity instanceof HomeScreen)
            homeScreenAvailableBack = false;
    }
    /**
     * Check if Home screen is paused or in the activity stack
     * */
    public boolean isHomeScreenPaused() {
        return homeScreenAvailableBack;
    }
}
