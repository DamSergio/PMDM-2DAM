package com.serbalced.practica04_lista_de_la_compra.ui.share_list;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ShareListViewModel extends ViewModel {
    private final MutableLiveData<String> mText;

    public ShareListViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Share List fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
