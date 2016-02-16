package com.snilius.foodlife.di;

import android.content.Context;


import com.snilius.foodlife.App;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * @author Victor Häggqvist
 * @since 2/10/16
 */
@Module
public class AndroidModule {
    private final App application;

    public AndroidModule(App application) {
        this.application = application;
    }

    @Provides
    @Singleton
    @ForApplication
    Context provideApplicationContext() {
        return application;
    }

}