package com.example.sharemybike.mainPanel.ui.nav_new_bike;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class NewBikeViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public NewBikeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is slideshow fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}