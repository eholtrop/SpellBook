package com.avianapps.spellbook.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by evanh on 8/14/2016.
 */
public class Spell {
    public String name;
    @SerializedName("desc")
    public String description;
    @SerializedName("higher_level")
    public String higherLevel;
    public String range;
    public String components;
    public String material;
    public String ritual;
    public String duration;
    public Boolean concentration;
    public String castingTime;
    public String level;
    public String school;
    @SerializedName("class")
    public String classes;
    public String circle;
    public String page;
}
