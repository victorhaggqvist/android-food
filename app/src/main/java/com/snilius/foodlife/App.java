package com.snilius.foodlife;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.snilius.foodlife.di.ActivityComponent;
import com.snilius.foodlife.di.AndroidModule;
import com.snilius.foodlife.di.BaseComponent;
import com.snilius.foodlife.di.DaggerActivityComponent;
import com.snilius.foodlife.di.DaggerBaseComponent;
import com.snilius.foodlife.di.NetworkModule;
import com.snilius.foodlife.di.StorageModule;

import timber.log.Timber;

/**
 * @author Victor Häggqvist
 * @since 2/15/16
 */
public class App extends Application {

    private ActivityComponent activityComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
            Stetho.initializeWithDefaults(this);
        }

        BaseComponent baseComponent = DaggerBaseComponent.builder()
                .androidModule(new AndroidModule(this))
                .networkModule(new NetworkModule(BuildConfig.API_TOKEN))
                .storageModule(new StorageModule())
                .build();

        activityComponent = DaggerActivityComponent.builder()
                .baseComponent(baseComponent)
                .build();
    }

    public ActivityComponent getComponent() {
        return activityComponent;
    }
}
