package com.snilius.foodlife.di;



import com.snilius.foodlife.data.CupboardDbHelper;
import com.snilius.foodlife.data.FoodService;

import javax.inject.Singleton;

import dagger.Component;

/**
 * @author Victor Häggqvist
 * @since 2/10/16
 */
@Singleton
@Component(modules = {StorageModule.class, NetworkModule.class, AndroidModule.class})
public interface BaseComponent {

    FoodService foodService();
    CupboardDbHelper dhHelper();

}
