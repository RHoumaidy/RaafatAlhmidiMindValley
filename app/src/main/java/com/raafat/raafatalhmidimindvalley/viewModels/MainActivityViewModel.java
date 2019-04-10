package com.raafat.raafatalhmidimindvalley.viewModels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.Nullable;

import com.raafat.raafatalhmidimindvalley.respositories.PhotoesRepository;

/**
 * Created by Raafat Alhoumaidy on 4/10/2019 @5:41 PM.
 */
public class MainActivityViewModel extends ViewModel {


    private MediatorLiveData<PhotoesRepository.APIResponse> photoesResponseObservalbe;
    private LiveData<PhotoesRepository.APIResponse> liveData;

    public MainActivityViewModel() {
        photoesResponseObservalbe = new MediatorLiveData<>();
    }

    public LiveData<PhotoesRepository.APIResponse> getData() {
        liveData = PhotoesRepository.getInstance().getPhotoesList();
        photoesResponseObservalbe.addSource(liveData
                , apiResponse -> photoesResponseObservalbe.setValue(apiResponse));
        return photoesResponseObservalbe;
    }

    public void refresh() {
        photoesResponseObservalbe.removeSource(liveData);
        getData();
    }
}
