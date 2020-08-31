package com.abhi.flickr.view.vvmcontract;

import com.abhi.flickr.repository.model.FlikrModel;

import java.util.List;

/**
 * Created by Abhinav.
 */

public interface MainScreen {

    void updateData(List<FlikrModel> data);
    void setError(String msg);
}
