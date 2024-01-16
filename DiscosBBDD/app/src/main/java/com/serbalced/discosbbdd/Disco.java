package com.serbalced.discosbbdd;

import androidx.annotation.NonNull;

public class Disco {
    public String grupo;
    public String disco;

    public Disco(String grupo, String disco) {
        this.grupo = grupo;
        this.disco = disco;
    }

    @NonNull
    @Override
    public String toString() {
        return grupo + " - " + disco;
    }
}
