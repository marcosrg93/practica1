package com.marc.es.practica1;

import java.util.Comparator;

/**
 * Created by MarcosFrancisco.
 */
public class OrdenarIdJuego implements Comparator<Videojuego> {


    @Override
    public int compare(Videojuego v1, Videojuego v2) {
        if(v1.getIdJuego()>(v2.getIdJuego()))
            return 1;
        if(v1.getIdJuego()<(v2.getIdJuego()))
            return -1;
        return 0;
    }
}
