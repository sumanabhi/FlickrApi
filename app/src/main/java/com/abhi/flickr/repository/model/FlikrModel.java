package com.abhi.flickr.repository.model;

/**
 * Created by Abhinav.
 */

public class FlikrModel {
    public final String id;
    public final String secret;
    public final String server;
    public final String farm;


    public FlikrModel(String id, String secret, String server, String farm) {
        this.secret = secret;
        this.id = id;
        this.server = server;
        this.farm = farm;

    }
}
