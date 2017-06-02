package com.tmq.temp.exportpdf;

/**
 * Created by tmq on 01/06/2017.
 */
public class UserInfo {

    private String name;
    private String favorite;
    private String activity;

    public UserInfo(String name, String favorite, String activity) {
        this.name = name;
        this.favorite = favorite;
        this.activity = activity;
    }

    public String getName() {
        return name;
    }

    public String getFavorite() {
        return favorite;
    }

    public String getActivity() {
        return activity;
    }
}
