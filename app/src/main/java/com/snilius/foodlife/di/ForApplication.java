package com.snilius.foodlife.di;

import java.lang.annotation.Retention;

import javax.inject.Qualifier;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author Victor Häggqvist
 * @since 2/10/16
 */
@Qualifier
@Retention(RUNTIME)
public @interface ForApplication {
}
