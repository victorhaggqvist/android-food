package com.snilius.foodlife.di;

import java.lang.annotation.Retention;

import javax.inject.Scope;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author Victor Häggqvist
 * @since 2/10/16
 */
@Scope
@Retention(RUNTIME)
public @interface ActivityScope {
}