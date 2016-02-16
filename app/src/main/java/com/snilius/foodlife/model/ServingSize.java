package com.snilius.foodlife.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @author Victor Häggqvist
 * @since 2/15/16
 */
public class ServingSize {

    @SerializedName("name_no")
    @Expose
    public String nameNo;
    @SerializedName("countryfilter")
    @Expose
    public String countryfilter;
    @SerializedName("name_pt")
    @Expose
    public String namePt;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("name_sv")
    @Expose
    public String nameSv;
    @SerializedName("name_en")
    @Expose
    public String nameEn;
    @SerializedName("name_de")
    @Expose
    public String nameDe;
    @SerializedName("name_pl")
    @Expose
    public String namePl;
    @SerializedName("name_ru")
    @Expose
    public String nameRu;
    @SerializedName("oid")
    @Expose
    public int oid;
    @SerializedName("source")
    @Expose
    public String source;
    @SerializedName("name_nl")
    @Expose
    public String nameNl;
    @SerializedName("name_fr")
    @Expose
    public String nameFr;
    @SerializedName("servingcategory")
    @Expose
    public int servingcategory;
    @SerializedName("name_da")
    @Expose
    public String nameDa;
    @SerializedName("name_es")
    @Expose
    public String nameEs;
    @SerializedName("name_it")
    @Expose
    public String nameIt;
    @SerializedName("proportion")
    @Expose
    public double proportion;
    @SerializedName("lastupdated")
    @Expose
    public int lastupdated;
    @SerializedName("created")
    @Expose
    public int created;

}
