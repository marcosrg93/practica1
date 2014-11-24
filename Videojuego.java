package com.marc.es.practica1;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by MarcosFrancisco.
 */
public class Videojuego implements  Parcelable,Comparable<Videojuego> {

    private String nombre,plataforma;
    private int puntuacion, idJuego;


    public Videojuego() {

    }

    public Videojuego(String nom, String plat,int id, int pun) {
        this.nombre = nom;
        this.plataforma = plat;
        this.idJuego = id;
        this.puntuacion = pun;
    }


    public Videojuego(Parcel par) {

        this.nombre = par.readString();
        this.plataforma = par.readString();
        this.idJuego = par.readInt();
        this.puntuacion = par.readInt();

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel pl, int flags) {

        pl.writeString(nombre);
        pl.writeString(plataforma);
        pl.writeInt(idJuego);
        pl.writeInt(puntuacion);

    }

    public static final Creator<Videojuego> CREATOR =
            new Creator<Videojuego>() {
                @Override
                public Videojuego createFromParcel(Parcel source) {
                    return new Videojuego(source);
                }

                @Override
                public Videojuego[] newArray(int size) {
                    return new Videojuego[size];
                }
            };



    public String getNombre() {
        return nombre;
    }

    public String getPlataforma() {
        return this.plataforma;
    }

    public int getIdJuego (){
        return  this.idJuego;
    }

    public int getPuntuacion (){
        return this.puntuacion;
    }


    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPlataforma(String plataforma) {
        this.plataforma = plataforma;
    }

    public void setIdJuego(int idJuego) {
        this.idJuego = idJuego;
    }

    public void setPuntuacion(int puntuacion) {
        if(puntuacion <= 10 && puntuacion > 0) {
            this.puntuacion = puntuacion;
        }
    }


    @Override
    public int compareTo(Videojuego v) {
        if (this.idJuego > v.idJuego) {
            return 1;
        } else if (this.idJuego < v.idJuego) {
            return -1;
        }
        return 0;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if(obj == null) return false;
        if(!(obj instanceof Videojuego)) return false;

        Videojuego vj = (Videojuego) obj;

        if(idJuego == 0){
            if(vj.idJuego != 0) return false;
        }
        else return idJuego==(vj.idJuego);

        return true;
    }
    @Override
    public int hashCode() {
        return idJuego;
    }

    @Override
    public  String toString(){
        return "Videojuego: "+
                "Nombre ='"+nombre+
                "Plataforma: "+ plataforma+
                " IdJuego: "+idJuego+
                "Puntuación: "+puntuacion;
    }

}
