package com.marc.es.practica1;

import java.util.Comparator;

/**
 * Created by MarcosFrancisco.
 */
public class OrdenarPuntuaciones implements Comparator<Videojuego> {


    @Override
    public int compare(Videojuego v1, Videojuego v2) {
        if(v1.getPuntuacion()>(v2.getPuntuacion()))
            return 1;
        if(v1.getPuntuacion()<(v2.getPuntuacion()))
            return -1;
        return 0;
    }
}
