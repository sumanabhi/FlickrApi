package com.abhi.flickr.repository.datasource;


import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.abhi.flickr.network.ApiInterface;
import com.abhi.flickr.network.RetrofitService;
import com.abhi.flickr.repository.entities.FlikrEntity;
import com.abhi.flickr.repository.mappers.FlickrMapper;
import com.abhi.flickr.repository.model.ResponsePhotoItemHolder;
import com.abhi.flickr.utils.Flickrutils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by Abhinav.
 */

public class RemoteDataSource implements DataSource<List<FlikrEntity>>, Callback<ResponsePhotoItemHolder> {
    private static final String TAG = RemoteDataSource.class.getSimpleName();
    private final FlickrMapper mObjMapper;
    private final MutableLiveData<String> mError = new MutableLiveData<>();
    private final MutableLiveData<List<FlikrEntity>> mDataApi = new MutableLiveData<>();

    public RemoteDataSource(Context appContext, FlickrMapper objMapper) {
        mObjMapper = objMapper;
    }

    @Override
    public LiveData<List<FlikrEntity>> getDataStream() {
        return mDataApi;
    }

    @Override
    public LiveData<String> getErrorStream() {
        return mError;
    }

    @Override
    public void clearData() {

    }

    public void fetch(String searchText, int page) {

        ApiInterface apiInterface = RetrofitService.cteateService(ApiInterface.class);

        Call<ResponsePhotoItemHolder> call = apiInterface.getImageItemList(Flickrutils.API_KEY, page, Flickrutils.DEFAULT_PAGE_SIZE,
                searchText);

        call.enqueue(this);

    }


    @Override
    public void onResponse(Call<ResponsePhotoItemHolder> call, Response<ResponsePhotoItemHolder> response) {
        Log.d(TAG, "Thread->" +
                Thread.currentThread().getName() + "\tGot some network response");
        Log.d(TAG, "Thread->" + Thread.currentThread().getName() + "\n Response:" + response);
        mDataApi.setValue(response.body().getPhotos().getPhoto());
    }

    @Override
    public void onFailure(Call<ResponsePhotoItemHolder> call, Throwable t) {
        Log.d(TAG, "Thread->" +
                Thread.currentThread().getName() + "\tGot network error");
        mError.setValue(t.toString());
    }
}
