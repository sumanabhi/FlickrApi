package com.abhi.flickr.repository.model;

/**
 * Created by Abhinav.
 */

public enum ImageSize {

    MEDIUM,
    LARGE;

    public String getValue() {
        if (this == MEDIUM) return "z";
        return "h";
    }

}