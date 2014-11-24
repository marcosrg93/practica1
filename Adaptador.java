package com.marc.es.practica1;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by MarcosFrancisco.
 */
public class Adaptador extends ArrayAdapter<Videojuego> {
    private Context contexto;
    private ArrayList<Videojuego> al;
    private int recurso;
    private static LayoutInflater li;
    private ViewHolder vh;

    static class ViewHolder{
        public TextView tvNom, tvId, tvPunt, tvplat, tvPl,tvN,tvP,tvI;
        public ImageView i;
        public int posicion;
    }

    public Adaptador(Context context, int resource, ArrayList<Videojuego> objects) {
        super(context, resource, objects);
        this.contexto = context;
        this.al = objects;
        this.recurso = resource;
        this.li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Log.v("LOG",""+al.size());
        if(convertView == null){
            convertView = li.inflate(recurso, null);
            vh = new ViewHolder();
            vh.i=(ImageView)convertView.findViewById(R.id.Icono);

            vh.tvNom=(TextView)convertView.findViewById(R.id.tvNom);
            vh.tvplat=(TextView)convertView.findViewById(R.id.tvplat);
            vh.tvPunt=(TextView)convertView.findViewById(R.id.tvPunt);
            vh.tvId=(TextView)convertView.findViewById(R.id.tvId);

            vh.tvN=(TextView)convertView.findViewById(R.id.tvN);
            vh.tvPl=(TextView)convertView.findViewById(R.id.tvPl);
            vh.tvP=(TextView)convertView.findViewById(R.id.tvP);
            vh.tvI=(TextView)convertView.findViewById(R.id.tvI);

            convertView.setTag(vh);
        }else{

            vh=(ViewHolder)convertView.getTag();
        }
        vh.posicion=position;
        vh.tvI.setText(al.get(position).getIdJuego()+"");
        vh.tvP.setText(al.get(position).getPuntuacion()+"");
        vh.tvN.setText(al.get(position).getNombre().toString());
        vh.tvPl.setText(al.get(position).getPlataforma().toString());

        vh.i.setTag(position);
        return convertView;
    }

}
