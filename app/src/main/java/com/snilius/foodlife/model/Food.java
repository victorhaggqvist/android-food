package com.snilius.foodlife.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

/**
 * @author Victor Häggqvist
 * @since 2/15/16
 */
@Parcel
public class Food {

    @SerializedName("headcategoryid")
    @Expose
    int headcategoryid;
    @SerializedName("category")
    @Expose
    String category;
    @SerializedName("sugar")
    @Expose
    float sugar;
    @SerializedName("pcsingram")
    @Expose
    double pcsingram;
    @SerializedName("potassium")
    @Expose
    float potassium;
    @SerializedName("brand")
    @Expose
    String brand;
    @SerializedName("unsaturatedfat")
    @Expose
    float unsaturatedfat;
    @SerializedName("servingcategory")
    @Expose
    int servingcategory;
    @SerializedName("pcstext")
    @Expose
    String pcstext;
    @SerializedName("categoryid")
    @Expose
    int categoryid;
    @SerializedName("gramsperserving")
    @Expose
    double gramsperserving;
    @SerializedName("fat")
    @Expose
    float fat;
    @SerializedName("id")
    @Expose
    Long _id;
    @SerializedName("carbohydrates")
    @Expose
    float carbohydrates;
    @SerializedName("title")
    @Expose
    String title;
    @SerializedName("measurementid")
    @Expose
    int measurementid;
    @SerializedName("verified")
    @Expose
    boolean verified;
    @SerializedName("sodium")
    @Expose
    float sodium;
    @SerializedName("protein")
    @Expose
    float protein;
    @SerializedName("typeofmeasurement")
    @Expose
    int typeofmeasurement;
    @SerializedName("calories")
    @Expose
    int calories;
    @SerializedName("saturatedfat")
    @Expose
    float saturatedfat;
    @SerializedName("defaultserving")
    @Expose
    int defaultserving;
    @SerializedName("showonlysametype")
    @Expose
    int showonlysametype;
    @SerializedName("cholesterol")
    @Expose
    double cholesterol;
    @SerializedName("showmeasurement")
    @Expose
    int showmeasurement;
    @SerializedName("fiber")
    @Expose
    float fiber;
    @SerializedName("source")
    @Expose
    int source;
    @SerializedName("mlingram")
    @Expose
    float mlingram;

    public int getHeadcategoryid() {
        return headcategoryid;
    }

    public String getCategory() {
        return category;
    }

    public float getSugar() {
        return sugar;
    }

    public double getPcsingram() {
        return pcsingram;
    }

    public float getPotassium() {
        return potassium;
    }

    public String getBrand() {
        return brand;
    }

    public float getUnsaturatedfat() {
        return unsaturatedfat;
    }

    public int getServingcategory() {
        return servingcategory;
    }

    public String getPcstext() {
        return pcstext;
    }

    public int getCategoryid() {
        return categoryid;
    }

    public double getGramsperserving() {
        return gramsperserving;
    }

    public float getFat() {
        return fat;
    }

    public Long getId() {
        return _id;
    }

    public float getCarbohydrates() {
        return carbohydrates;
    }

    public String getTitle() {
        return title;
    }

    public int getMeasurementid() {
        return measurementid;
    }

    public boolean isVerified() {
        return verified;
    }

    public float getSodium() {
        return sodium;
    }

    public float getProtein() {
        return protein;
    }

    public int getTypeofmeasurement() {
        return typeofmeasurement;
    }

    public int getCalories() {
        return calories;
    }

    public float getSaturatedfat() {
        return saturatedfat;
    }

    public int getDefaultserving() {
        return defaultserving;
    }

    public int getShowonlysametype() {
        return showonlysametype;
    }

    public double getCholesterol() {
        return cholesterol;
    }

    public int getShowmeasurement() {
        return showmeasurement;
    }

    public float getFiber() {
        return fiber;
    }

    public int getSource() {
        return source;
    }

    public float getMlingram() {
        return mlingram;
    }
}
