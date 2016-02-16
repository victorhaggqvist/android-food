package com.snilius.foodlife.di;

import com.snilius.foodlife.ui.DetailActivity;
import com.snilius.foodlife.ui.MainActivity;

import dagger.Component;

/**
 * @author Victor Häggqvist
 * @since 2/10/16
 */
@ActivityScope
@Component(dependencies = BaseComponent.class)
public interface ActivityComponent extends BaseComponent {

    void inject(MainActivity mainActivity);
    void inject(DetailActivity detailActivity);

}