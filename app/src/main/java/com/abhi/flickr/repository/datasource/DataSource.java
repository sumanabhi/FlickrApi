package com.abhi.flickr.repository.datasource;


import androidx.lifecycle.LiveData;

/**
 * Created by Abhinav.
 */
public interface DataSource<T> {

    LiveData<T> getDataStream();
    LiveData<String> getErrorStream();
    void clearData();
}
