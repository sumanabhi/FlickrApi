package com.abhi.flickr.repository;



import androidx.lifecycle.LiveData;

import com.abhi.flickr.repository.model.FlikrModel;

import java.util.List;

/**
 * Created by Abhinav.
 */

public interface FlikrRepository {
    LiveData<List<FlikrModel>> getFlikrModels();
    LiveData<String> getErrorStream();
    void fetchData(String searchText, int page);
    void clearData();
}