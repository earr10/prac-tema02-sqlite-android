package com.example.practema02sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    EditText e1, e2, e3, e4;
    Button b1, b2, b3, b4;
    Helper conexion = new Helper(this, "base33", null, 2);
    String nom, ap;
    int id;
    SQLiteDatabase sql, sql2, sql3, sql4;
    ContentValues valores, valores2;

    public ListView lv;
    public ArrayList<String> lista;
    public ArrayAdapter adaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        relacionarVistas();
        lista = llenar_lv();
        adaptador = new ArrayAdapter(this, android.R.layout.simple_spinner_item, lista);
        lv.setAdapter(adaptador);

    }

    public ArrayList llenar_lv() {
        ArrayList<String> lista = new ArrayList<>();
        SQLiteDatabase database = conexion.getReadableDatabase();
        String q = "SELECT * FROM contactos2";
        Cursor registros = database.rawQuery(q, null);
        if (registros.moveToFirst()) {
            do {
                lista.add("ID=" + registros.getString(0) +
                        " NOMBRE = " + registros.getString(1) +
                        "  APELLIDOS= " + registros.getString(2));
            } while (registros.moveToNext());
        }
        return lista;
    }

    public void relacionarVistas() {
        lv = (ListView) findViewById(R.id.lista);
        e1 = findViewById(R.id.u);
        e2 = findViewById(R.id.p);
        e3 = findViewById(R.id.idNum);
        e4 = findViewById(R.id.id);
        b1 = findViewById(R.id.bConsultar);
        b1.setOnClickListener(this);
        b2 = findViewById(R.id.bActualizar);
        b2.setOnClickListener(this);
        b3 = findViewById(R.id.bBorra);
        b3.setOnClickListener(this);
        b4 = findViewById(R.id.bInserta);
        b4.setOnClickListener(this);
    }


    public void limpiarCajas() {
        e1.setText("");
        e2.setText("");
        e3.setText("");
        e4.setText("");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bConsultar:
                ArrayList<String> lista2 = new ArrayList<>();
                String datos = "";
                sql = conexion.getReadableDatabase();//los objetos sql tienen que ser diferentes en cada consulta
                // String args[]=new String[]{"id="+e1.getText().toString()};
                //String campos[]=new String[]{"id,nombre,apellidos"};
                //int variable=Integer.parseInt(e1.getText().toString());
                //String variable[]=new String[]{"id="+e1.getText().toString()};
                //sql.execSQL("SELECT * FROM contactos2 WHERE id="+variable);
                //Cursor registro=sql.query("contactos2",campos,"id like ?",args,null,null,null);
                Cursor registro = sql.rawQuery("SELECT * FROM contactos2", null);
               /*for (registro.moveToFirst();!registro.isAfterLast();registro.moveToNext()) {
                    datos += registro.getString(registro.getColumnIndex("id"));

                    datos += "," + registro.getString(registro.getColumnIndex("nombre"));

                    datos += "," + registro.getString(registro.getColumnIndex("apellidos"));
                    datos += "\n";
                }*/


                /*registro.moveToFirst();
                if (registro.getCount() >= 1) {
                    do {
                        datos += registro.getString(registro.getColumnIndex("id"));
                        datos += ", " + registro.getString(registro.getColumnIndex("nombre"));
                        datos += ", " + registro.getString(registro.getColumnIndex("apellidos"));
                        lista2.add(datos);
                        datos = "";
                        registro.moveToNext();

                    } while (!registro.isAfterLast());
                }*/


                registro.moveToLast();
                if (registro.getCount() >= 1) {
                    while (!registro.isBeforeFirst()) {
                        datos += registro.getString(registro.getColumnIndex("id"));
                        datos += ", " + registro.getString(registro.getColumnIndex("nombre"));
                        datos += ", " + registro.getString(registro.getColumnIndex("apellidos"));
                        lista2.add(datos);
                        datos = "";
                        registro.moveToPrevious();
                    }
                }


                adaptador = new ArrayAdapter(this,
                        android.R.layout.simple_spinner_item, lista2);
                lv.setAdapter(adaptador);
                Toast.makeText(this, datos, Toast.LENGTH_LONG).show();

                break;
            case R.id.bActualizar:
                sql4 = conexion.getWritableDatabase();
                nom = e1.getText().toString();
                ap = e2.getText().toString();
                id = Integer.parseInt(e3.getText().toString());
                valores = new ContentValues();
                valores.put("id", id);//el contenedor de valores solo acepta variables no objetos
                valores.put("nombre", nom);
                valores.put("apellidos", ap);

                String argumentos[] = new String[]{e4.getText().toString()};

                int resultadoUp = sql4.update("contactos2", valores, "nombre like ?", argumentos);
                Toast.makeText(getApplicationContext(), resultadoUp + "Registro actualizado", Toast.LENGTH_SHORT).show();
                limpiarCajas();
                break;
            case R.id.bInserta:
                sql2 = conexion.getWritableDatabase();
               /* String nom = e1.getText().toString();
                String ap = e2.getText().toString();
                int id = Integer.parseInt(e3.getText().toString());
                sql.execSQL("INSERT INTO contactos2 (id,nombre,apellidos) VALUES ("+id+",'"+nom+"','"+ap+"')");
               */
                nom = e1.getText().toString();
                ap = e2.getText().toString();
                id = Integer.parseInt(e3.getText().toString());
                valores2 = new ContentValues();
                valores2.put("id", id);//el contenedor de valores solo acepta variables no objetos
                valores2.put("nombre", nom);
                valores2.put("apellidos", ap);
                long i = sql2.insert("contactos2", null, valores2);
                Toast.makeText(getApplicationContext(), "Registro " + i + " insertados", Toast.LENGTH_SHORT).show();
                limpiarCajas();
                break;
            case R.id.bBorra:
                sql3 = conexion.getWritableDatabase();
                String argumentos2[] = new String[]{e4.getText().toString()};
                //int variable=Integer.parseInt(e4.getText().toString());
                //sql3.execSQL("DELETE FROM contactos2 WHERE id="+variable);
                int resultado = sql3.delete("contactos2", "id=?", argumentos2);
                Toast.makeText(getApplicationContext(), resultado + "Registros borrados", Toast.LENGTH_SHORT).show();
                limpiarCajas();
                break;
        }
    }
}