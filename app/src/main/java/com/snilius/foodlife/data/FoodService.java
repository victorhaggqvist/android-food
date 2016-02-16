package com.snilius.foodlife.data;

import com.snilius.foodlife.model.FoodResponse;

import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * @author Victor Häggqvist
 * @since 2/15/16
 */
public interface FoodService {

    @GET("en/se/{query}")
    Observable<Response<FoodResponse>> search(@Path("query") String query);

}
