package com.marc.es.practica1;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.Xml;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;


public class Principal extends Activity implements AdapterView.OnItemLongClickListener {
    private Gestor videojuego = new Gestor();
    private Adaptador a;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_game);
        ArrayList<Videojuego> alj = new ArrayList<Videojuego>();
        alj = alLeer();
        videojuego.alCopiar(alj);
        a = new Adaptador(this, R.layout.list_game, alj);
        ListView lv = (ListView) findViewById(R.id.listajuegos);
        lv.setAdapter(a);
        lv.setOnItemLongClickListener(this);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.anadir) {
            addJuego();
            return true;
        }
        if (id == R.id.ordenarN) {
            videojuego.ordenarNombres();
            ArrayList<Videojuego> alj = videojuego.alMover();
            a.notifyDataSetChanged();
            a = new Adaptador(Principal.this, R.layout.list_game, alj);
            ListView lv = (ListView) findViewById(R.id.listajuegos);
            lv.setAdapter(a);
            return true;

        }
        if (id == R.id.ordenarP) {
            videojuego.ordenarPuntuacion();
            ArrayList<Videojuego> alj= videojuego.alMover();
            a.notifyDataSetChanged();
            a = new Adaptador(Principal.this, R.layout.list_game, alj);
            ListView lv = (ListView) findViewById(R.id.listajuegos);
            lv.setAdapter(a);
            return true;

        }
        if (id == R.id.ordenarID) {
            videojuego.ordenarIdJuego();
            ArrayList<Videojuego> alj= videojuego.alMover();
            a.notifyDataSetChanged();
            a = new Adaptador(Principal.this, R.layout.list_game, alj);
            ListView lv = (ListView) findViewById(R.id.listajuegos);
            lv.setAdapter(a);
            return true;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.menu_secundario, menu);
    }


    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        String[] str = new String[]{"Modificar", "Borrar" };
        final int pos = position;
        AlertDialog ad = new AlertDialog.Builder(
                Principal.this)
                .setTitle("Opciones")
                .setItems(str,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int selected) {
                                if (selected == 0) {
                                    editar(pos);
                                } else if (selected == 1) {
                                    borrar(pos);

                                }
                            }
                        }).create();
        ad.show();
        return true;
    }


    @Override
    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        ArrayList<Videojuego> alj = videojuego.alMover();
        outState.putParcelableArrayList("Videojuego", alj);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);
        ArrayList<Videojuego> vj= savedInstanceState.getParcelableArrayList("Videojuego");
        videojuego.alCopiar(vj);
        a = new Adaptador(this, R.layout.list_game, vj);
        ListView lv = (ListView) findViewById(R.id.listajuegos);
        lv.setAdapter(a);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if ( resultCode == Activity.RESULT_OK ){

            ArrayList <Videojuego> vj = data.getParcelableArrayListExtra("videojuego");


            videojuego.alCopiar(vj);
            a.notifyDataSetChanged();
            a = new Adaptador(Principal.this, R.layout.list_game, vj);
            ListView lv = (ListView) findViewById(R.id.listajuegos);
            lv.setAdapter(a);

        }

    }








    public Toast mensaje(String t) {
        Toast toast =
                Toast.makeText(getApplicationContext(),
                        t + "", Toast.LENGTH_SHORT);
        toast.show();
        return toast;
    }


    //Crea el archivo XMl con los videojuegos almacenados por el metodo addJuego
    public void generaXml()  {

        try {
            File f = new File(getExternalFilesDir(null), "catalogo.xml");
            FileOutputStream fosxml = new FileOutputStream(f);
            XmlSerializer docxml = Xml.newSerializer();
            docxml.setOutput(fosxml, "UTF-8");
            docxml.startDocument(null, Boolean.valueOf(true));
            docxml.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
            docxml.startTag(null, "Videojuegos");


            for (int i=0;i<videojuego.size();i++) {

                docxml.startTag(null, "Videojuego");
                docxml.startTag(null, "Nombre");
                docxml.text(videojuego.get(i).getNombre().toString());
                docxml.endTag(null, "Nombre");
                docxml.startTag(null, "Plataforma");
                docxml.text(videojuego.get(i).getPlataforma().toString());
                docxml.endTag(null, "Plataforma");
                docxml.startTag(null, "Puntuacion");
                docxml.text(videojuego.get(i).getPuntuacion() + "");
                docxml.endTag(null, "Puntuacion");
                docxml.startTag(null, "IdJuego");
                docxml.text(videojuego.get(i).getIdJuego() + "");
                docxml.endTag(null, "IdJuego");
                docxml.endTag(null, "Videojuego");
            }
            docxml.endTag(null, "Videojuegos");
            docxml.endDocument();
            docxml.flush();
            fosxml.close();

        }catch (Exception e){}
    }




    //Añadir un Juego nuevo filtrando los datos y que su idjuego sea unico
    private boolean addJuego() {

        LayoutInflater inflater = LayoutInflater.from(this);
        final View vista = inflater.inflate(R.layout.new_game, null);
        final EditText etext,etext1, etext2,etext3;

        etext = (EditText) vista.findViewById(R.id.etid);
        etext1 = (EditText) vista.findViewById(R.id.etp);
        etext2 = (EditText) vista.findViewById(R.id.etn);
        etext3 = (EditText) vista.findViewById(R.id.etpl);

        etext.setHint("Introduzca ID de Juego");
        etext1.setHint("Introduzca Puntuación");
        etext2.setHint("Introduzca Nombre");
        etext3.setHint("Introduzca Plataforma");


        final AlertDialog ad = new AlertDialog.Builder(this)
                .setView(vista)
                .setTitle("Añadir Videojuego")
                .setPositiveButton(android.R.string.ok, null)
                .setNegativeButton(android.R.string.cancel, null)
                .create();
        ad.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Button bt = ad.getButton(AlertDialog.BUTTON_POSITIVE);
                bt.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {

                        int id = 0;

                        try {
                            id = Integer.parseInt(etext.getText().toString());
                        } catch (Exception e) {
                        }
                        if (videojuego.repetirIdJuego(id) || id == 0) {
                            mensaje("Introduce un id de juego valido.");
                        } else {
                        }
                        if (etext1.getText().toString().length() > 0 && etext1.getText().toString().length() <= 2
                                && etext.getText().toString().length() > 0 && etext.getText().toString().length() <= 2
                                && etext3.getText().length() < 10 && etext2.getText().length() > 0) {
                            int pun = 0;

                            Videojuego vj = new Videojuego();

                            try {
                                pun = Integer.parseInt(etext1.getText().toString());
                            } catch (Exception e) {
                            }

                            try {
                                id = Integer.parseInt(etext.getText().toString());
                            } catch (Exception e) {
                            }
                            if (videojuego.repetirIdJuego(id)) {
                                mensaje("El Id de juego ya existe");
                            }
                            vj.setPuntuacion(pun);
                            vj.setIdJuego(id);
                            vj.setNombre(etext2.getText().toString());
                            vj.setPlataforma(etext3.getText().toString());


                            videojuego.add(vj);
                            ArrayList<Videojuego> datos = videojuego.alMover();
                            a.notifyDataSetChanged();
                            a = new Adaptador(Principal.this, R.layout.list_game, datos);
                            ListView lv = (ListView) findViewById(R.id.listajuegos);
                            lv.setAdapter(a);
                            mensaje("El videojuego " + vj.getNombre() + " ha sido añadido correctamente");
                            generaXml();
                            ad.dismiss();
                        }

                        if (etext2.getText().toString().length() == 0) {
                            mensaje("Por favor introduzca un nombre");
                        }
                        if (etext3.getText().toString().length() == 0) {
                            mensaje("Introduce la plataforma (PS3, Xbox 360, pc, etc.)");
                        }
                        if (etext.getText().toString().length() == 0) {
                            mensaje("Introduce un Id para el juego");
                        }
                        if (etext1.getText().toString().length() == 0) {
                            mensaje("Introduce la puntuación");
                        }

                    }
                });
            }
        });

        ad.show();
        ArrayList<Videojuego> datos=videojuego.alMover();
        a = new Adaptador(Principal.this, R.layout.list_game, datos);
        ListView lv = (ListView) findViewById(R.id.listajuegos);
        lv.setAdapter(a);
        return true;
    }



    public boolean borrar(final int pos){

        AlertDialog.Builder ad = new AlertDialog.Builder(Principal.this);

        ad.setTitle("Atención");
        ad.setMessage("¿ Desea borrar el videojuego seleccionado ?");
        ad.setCancelable(false);
        ad.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface di1, int id) {
                videojuego.remove(pos);
                ArrayList<Videojuego> alj = videojuego.alMover();
                a.notifyDataSetChanged();
                a = new Adaptador(Principal.this, R.layout.list_game, alj);
                ListView lv = (ListView) findViewById(R.id.listajuegos);
                lv.setAdapter(a);
                generaXml();
                mensaje("Videojuego borrado");
            }
        });
        ad.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface di1, int id) {
                onResume();
            }
        });
        ad.show();
        return true;
    }




    public ArrayList<Videojuego> alLeer(){
        ArrayList<Videojuego> lista=new ArrayList<Videojuego>();
        try {
            XmlPullParser lectorxml = Xml.newPullParser();
            lectorxml.setInput(new FileInputStream(new File(getExternalFilesDir(null), "catalogo.xml")), "utf-8");
            int evento = lectorxml.getEventType();
            Videojuego vj=new Videojuego();
            while(evento != XmlPullParser.END_DOCUMENT){
                if(evento == XmlPullParser.START_TAG) {
                    String etiqueta = lectorxml.getName();
                    if (etiqueta.compareTo("Videojuego") == 0) {
                    }
                    if (etiqueta.compareTo("Nombre") == 0) {
                        vj.setNombre(lectorxml.nextText());
                    }
                    if (etiqueta.compareTo("Plataforma") == 0) {
                        vj.setPlataforma(lectorxml.nextText());
                    }
                    if (etiqueta.compareTo("Puntuacion") == 0) {
                        try {
                            int p = Integer.parseInt(lectorxml.nextText());
                            vj.setPuntuacion(p);
                        } catch (Exception e) {
                            mensaje("La lectura de la puntuación ha fallado");
                        }
                    }
                    if (etiqueta.compareTo("IdJuego") == 0) {
                        try {
                            int id = Integer.parseInt(lectorxml.nextText());
                            vj.setIdJuego(id);

                        } catch (Exception e) {
                            mensaje("La lectura del Id del Juego ha fallado");
                        }
                    }

                }
                if (evento == XmlPullParser.END_TAG) {
                    String etiqueta = lectorxml.getName();
                    if (etiqueta.compareTo("Videojuego") == 0) {
                        lista.add(vj);
                        vj=new Videojuego();
                    }
                }
                evento = lectorxml.next();
            }
        }catch(Exception e){}
        return lista;
    }



    private boolean editar(final int index) {
        Videojuego vj = videojuego.get(index);



        LayoutInflater inflater = LayoutInflater.from(this);
        final View vt = inflater.inflate(R.layout.new_game, null);
        final EditText  etext,etext1, etext2,etext3;
        String vpun,vid, vnom, vpla;
        vpun = vj.getPuntuacion()+"";
        vid = vj.getIdJuego()+"";
        vnom = vj.getNombre();
        vpla = vj.getPlataforma();


        etext = (EditText) vt.findViewById(R.id.etid);
        etext1 = (EditText) vt.findViewById(R.id.etp);
        etext2 = (EditText) vt.findViewById(R.id.etn);
        etext3 = (EditText) vt.findViewById(R.id.etpl);

        etext.setText(vid);
        etext1.setText(vpun);
        etext2.setText(vnom);
        etext3.setText(vpla);



        final AlertDialog ad = new AlertDialog.Builder(this)
                .setView(vt)
                .setTitle("Modificar VideoJuego")
                .setPositiveButton(android.R.string.ok, null)
                .setNegativeButton(android.R.string.cancel, null)
                .create();
        ad.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Button bt = ad.getButton(AlertDialog.BUTTON_POSITIVE);
                bt.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {

                        int id = 0;

                        try {
                            id = Integer.parseInt(etext1.getText().toString());
                        } catch (Exception e) {
                        }
                        if (videojuego.repetirIdJuego(id)) {
                            mensaje("El Id esta repetido");
                        } else {
                        }
                        if (etext1.getText().toString().length() > 0 && etext1.getText().toString().length() <= 2
                                && etext.getText().toString().length() > 0 && etext.getText().toString().length() <= 2
                                && etext3.getText().length() < 10 && etext2.getText().length() > 0 && id != 0 && !videojuego.repetirIdJuego(id)) {
                            int pun = 0;
                            Videojuego j = new Videojuego();
                            try {
                                pun = Integer.parseInt(etext.getText().toString());
                            } catch (Exception e) {
                            }
                            try {
                                id = Integer.parseInt(etext1.getText().toString());
                            } catch (Exception e) {
                            }

                            j.setPuntuacion(pun);
                            j.setIdJuego(id);
                            j.setNombre(etext2.getText().toString());
                            j.setPlataforma(etext3.getText().toString());


//                      modificamos jugados y mostramos

                            videojuego.set(index, j);
                            ArrayList<Videojuego> alj = videojuego.alMover();
                            a.notifyDataSetChanged();
                            a = new Adaptador(Principal.this, R.layout.list_game, alj);
                            ListView lv = (ListView) findViewById(R.id.listajuegos);
                            lv.setAdapter(a);
                            generaXml();
                            mensaje("Videojuego modificado");
                            ad.dismiss();
                        }

                        if (etext2.getText().toString().length() == 0) {
                            mensaje("Por favor, introduzca un nombre");
                        }
                        if (etext3.getText().toString().length() == 0) {
                            mensaje("Introduzca una plataforma de juego(PS3, PS4, XBOX 360, PC,...)");
                        }
                        if (etext1.getText().toString().length() == 0) {
                            mensaje("Introduce una puntuación para el juego");
                        }
                        if (etext.getText().toString().length() == 0) {
                            mensaje("Introduce un Id para el juego");
                        }


                    }
                });
            }
        });
        ad.show();
        ArrayList<Videojuego> alj = videojuego.alMover();
        a = new Adaptador(Principal.this, R.layout.list_game, alj);
        ListView lv = (ListView) findViewById(R.id.listajuegos);
        lv.setAdapter(a);
        return true;
    }




}
