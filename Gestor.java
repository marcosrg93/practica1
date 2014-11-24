package com.marc.es.practica1;

import java.util.ArrayList;
import java.util.Collections;


/**
 * Created by MarcosFrancisco.
 */
public class Gestor  {

    private ArrayList<Videojuego> videojuego;


    public Gestor (){
        videojuego =new ArrayList<Videojuego>();
    }

    public Gestor (ArrayList<Videojuego> vj){
       videojuego=vj;
    }

    public Videojuego get(int i){
        Videojuego vj = videojuego.get(i);
        return vj;
    }

    public int size(){
        return videojuego.size();
    }

    public void set(int i,Videojuego v){
        videojuego.set(i, v);
    }

    public void alCopiar (ArrayList<Videojuego> vj){
        this.videojuego = vj;
    }

    public ArrayList<Videojuego> alMover(){
        ArrayList<Videojuego> vi=new ArrayList<Videojuego>();
        for(int i=0;i<videojuego.size();i++){
            vi.add(videojuego.get(i));
        }
        return vi;
    }

    public void remove(int i){
        videojuego.remove(i);
    }

    public void add(Videojuego vj){
        videojuego.add(vj);
    }

    public boolean repetirIdJuego(int id){
        for(int i=0;i<videojuego.size();i++){
            if(videojuego.get(i).getIdJuego()== id){
                return true;
            }
        }
        return false;
    }

    public boolean isEmpty(){
        if(videojuego.isEmpty())
            return true;
        return false;
    }

    public void ordenarIdJuego(){
        Collections.sort(videojuego, new OrdenarIdJuego());
    }
    public void ordenarPuntuacion(){
        Collections.sort(videojuego, new OrdenarPuntuaciones());
    }
    public void ordenarNombres(){ Collections.sort(videojuego, new OrdenarNombre());
    }


    public int compararIdJuego(int i, Videojuego j1){
        return videojuego.get(i).compareTo(j1);
    }




}
