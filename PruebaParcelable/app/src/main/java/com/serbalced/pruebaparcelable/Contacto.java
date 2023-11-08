package com.serbalced.pruebaparcelable;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class Contacto implements Parcelable {
    public String nombre;
    public String apellidos;
    public int edad;
    public String telefono;
    public ArrayList<Contacto> familiares;
    public boolean casado;

    public Contacto(String nombre, String apellidos, int edad, String telefono, boolean casado) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.edad = edad;
        this.telefono = telefono;
        this.casado = casado;
        this.familiares = new ArrayList<>();
    }

    protected Contacto(Parcel in) {
        nombre = in.readString();
        apellidos = in.readString();
        edad = in.readInt();
        telefono = in.readString();
        familiares = in.createTypedArrayList(Contacto.CREATOR);
        casado = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nombre);
        dest.writeString(apellidos);
        dest.writeInt(edad);
        dest.writeString(telefono);
        dest.writeTypedList(familiares);
        dest.writeByte((byte) (casado ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Contacto> CREATOR = new Creator<Contacto>() {
        @Override
        public Contacto createFromParcel(Parcel in) {
            return new Contacto(in);
        }

        @Override
        public Contacto[] newArray(int size) {
            return new Contacto[size];
        }
    };

    public void addFamiliares(Contacto c){
        familiares.add(c);
    }

    @Override
    public String toString() {
        return "Contacto{" +
                "nombre='" + nombre + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", edad=" + edad +
                ", telefono='" + telefono + '\'' +
                ", familiares=" + familiares +
                ", casado=" + casado +
                '}';
    }
}
