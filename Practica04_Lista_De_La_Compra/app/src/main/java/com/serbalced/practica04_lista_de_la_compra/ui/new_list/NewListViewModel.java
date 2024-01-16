package com.serbalced.practica04_lista_de_la_compra.ui.new_list;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class NewListViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public NewListViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}