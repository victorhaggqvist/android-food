package com.snilius.foodlife.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @author Victor Häggqvist
 * @since 2/15/16
 */
public class ServingCategory {

    @SerializedName("linearquantity")
    @Expose
    public double linearquantity;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("name_sv")
    @Expose
    public String nameSv;
    @SerializedName("linearsize")
    @Expose
    public int linearsize;
    @SerializedName("name_ru")
    @Expose
    public String nameRu;
    @SerializedName("oid")
    @Expose
    public int oid;
    @SerializedName("defaultsize")
    @Expose
    public int defaultsize;
    @SerializedName("name_da")
    @Expose
    public String nameDa;
    @SerializedName("name_nl")
    @Expose
    public String nameNl;
    @SerializedName("name_it")
    @Expose
    public String nameIt;
    @SerializedName("created")
    @Expose
    public int created;
    @SerializedName("usemedian")
    @Expose
    public int usemedian;
    @SerializedName("name_no")
    @Expose
    public String nameNo;
    @SerializedName("name_de")
    @Expose
    public String nameDe;
    @SerializedName("lastupdated")
    @Expose
    public int lastupdated;
    @SerializedName("name_fr")
    @Expose
    public String nameFr;
    @SerializedName("name_pt")
    @Expose
    public String namePt;
    @SerializedName("name_en")
    @Expose
    public String nameEn;
    @SerializedName("name_es")
    @Expose
    public String nameEs;
    @SerializedName("name_pl")
    @Expose
    public String namePl;
    @SerializedName("source")
    @Expose
    public Object source;

}
