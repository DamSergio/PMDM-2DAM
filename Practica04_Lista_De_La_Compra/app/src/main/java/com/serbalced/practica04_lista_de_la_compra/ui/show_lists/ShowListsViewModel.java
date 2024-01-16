package com.serbalced.practica04_lista_de_la_compra.ui.show_lists;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ShowListsViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public ShowListsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is gallery fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}