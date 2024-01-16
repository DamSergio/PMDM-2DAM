package com.serbalced.practica04_lista_de_la_compra.ui.new_product;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class NewProductViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public NewProductViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is slideshow fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}