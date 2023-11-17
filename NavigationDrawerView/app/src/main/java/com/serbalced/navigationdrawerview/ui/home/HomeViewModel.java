package com.serbalced.navigationdrawerview.ui.home;

import android.graphics.Bitmap;
import android.media.Image;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {

    private final MutableLiveData<String> mText;
    private final MutableLiveData<String> mName;
    private final MutableLiveData<Integer> mAge;
    private final MutableLiveData<Bitmap> mImgUser;

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mName = new MutableLiveData<>();
        mAge = new MutableLiveData<>();
        mImgUser = new MutableLiveData<>();

        mText.setValue("This is home fragment");
        mName.setValue("Pon aqui tu nombre");
        mAge.setValue(0);
    }

    public LiveData<String> getText() {
        return mText;
    }

    public MutableLiveData<String> getmName() {
        return mName;
    }

    public MutableLiveData<Integer> getmAge() {
        return mAge;
    }

    public MutableLiveData<Bitmap> getmImgUser() {
        return mImgUser;
    }

    public void setName(String s){
        mName.setValue(s);
    }

    public void setAge(int i){
        mAge.setValue(i);
    }

    public void setmImgUser(Bitmap b){
        mImgUser.setValue(b);
    }
}