package com.abhi.flickr.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.abhi.flickr.repository.FlikrRepository;
import com.abhi.flickr.repository.FlikrRepositoryImpl;
import com.abhi.flickr.repository.model.FlikrModel;

import java.util.List;

/**
 * Created by Abhinav.
 */

public class FlikrViewModel extends AndroidViewModel {
    private static final String TAG = FlikrViewModel.class.getSimpleName();
    private FlikrRepository flikrRepository;

    public LiveData<List<FlikrModel>> getFlikerModels() {
        return flikrRepository.getFlikrModels();
    }

    public LiveData<String> getErrorUpdates() {
        return flikrRepository.getErrorStream();
    }


    public FlikrViewModel(@NonNull Application application) {
        super(application);
        flikrRepository = FlikrRepositoryImpl.create(application);
    }

    @Override
    protected void onCleared() {
        Log.d(TAG, "onCleared() called");
        super.onCleared();
    }

    public void fetchData(String searchText, int page) {
        flikrRepository.fetchData(searchText, page);
    }

    @VisibleForTesting
    public FlikrViewModel(@NonNull Application application, FlikrRepositoryImpl repo) {
        super(application);
        this.flikrRepository = repo;
    }

    public void clearData() {
        flikrRepository.clearData();
    }
}

