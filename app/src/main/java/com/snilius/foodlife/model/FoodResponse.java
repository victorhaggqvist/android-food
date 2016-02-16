package com.snilius.foodlife.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Victor Häggqvist
 * @since 2/15/16
 */
public class FoodResponse {

    @SerializedName("language_requested")
    @Expose
    public String languageRequested;
    @SerializedName("serving_categories")
    @Expose
    public List<ServingCategory> servingCategories = new ArrayList<>();
    @SerializedName("title_completed")
    @Expose
    public String titleCompleted;
    @SerializedName("serving_sizes")
    @Expose
    public List<ServingSize> servingSizes = new ArrayList<>();
    @SerializedName("title_requested")
    @Expose
    public String titleRequested;
    @SerializedName("food")
    @Expose
    public List<Food> food = new ArrayList<Food>();
}
