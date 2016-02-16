package com.snilius.foodlife.di;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.snilius.foodlife.data.FoodService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

/**
 * @author Victor Häggqvist
 * @since 2/10/16
 */
@Module
public class NetworkModule {
    private String apiToken;

    public NetworkModule(String apiToken) {
        this.apiToken = apiToken;
    }

    @Singleton
    @Provides
    OkHttpClient provideOkHttpClient() {
        return new OkHttpClient.Builder()
                .addNetworkInterceptor(new HttpLoggingInterceptor(message -> Timber.d(message)))
                .addNetworkInterceptor(new StethoInterceptor())
                .addInterceptor(chain -> {
                    Request request = chain.request().newBuilder()
                            .addHeader("Authorization", apiToken)
                            .build();
                    return chain.proceed(request);
                }).build();
    }

    @Singleton
    @Provides
    Retrofit provideRestAdapter(OkHttpClient httpClient) {
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        return new Retrofit.Builder()
                .baseUrl("https://api.lifesum.com/icebox/v1/foods/")
                .client(httpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    @Singleton
    @Provides
    FoodService provideFoodService(Retrofit retrofit) {
        return retrofit.create(FoodService.class);
    }

}