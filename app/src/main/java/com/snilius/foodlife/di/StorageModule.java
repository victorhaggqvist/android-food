package com.snilius.foodlife.di;

import android.content.Context;


import com.snilius.foodlife.data.CupboardDbHelper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * @author Victor Häggqvist
 * @since 2/10/16
 */
@Module
public class StorageModule {

    @Singleton
    @Provides
    CupboardDbHelper provideCupboardSQLiteOpenHelper(@ForApplication Context context) {
        return new CupboardDbHelper(context);
    }

}
