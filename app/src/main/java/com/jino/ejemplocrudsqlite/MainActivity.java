package com.jino.ejemplocrudsqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText etId, etNombre, etTelefono;
    Button btnConsultar, btnAlta, btnEditar, btnBorrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // REFERENCIAS AL LAYOUT
        etId =  findViewById(R.id.etIdUsuario);
        etNombre = findViewById(R.id.etNombre);
        etTelefono = findViewById(R.id.etTelefono);

        btnConsultar = findViewById(R.id.btnConsultar);
        btnAlta = findViewById(R.id.btnAlta);
        btnEditar = findViewById(R.id.btnEditar);
        btnBorrar = findViewById(R.id.btnBorrar);

        //ACCIONES PARA LOS BOTONES
        btnConsultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                consulta();
            }
        });

        btnAlta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alta();
            }
        });

        btnBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                borrar();
            }
        });

        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editar();
            }
        });
    }

    //METODO CONSULTAR POR ID
    public void consulta(){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"Administracion",null, 1);
        SQLiteDatabase db = admin.getWritableDatabase();

        //LECTURA ID
        String idUsuario = etId.getText().toString();

        //CONSULTA
        Cursor fila = db.rawQuery("SELECT nombre, telefono FROM usuarios WHERE idUsuario = "+idUsuario,null);

        //SI SE ENCUENTRA EL ID SE MUESTRAN SUS CAMPOS, SINO SE ENVIA UN MENSAJE
        if(fila.moveToFirst()){
            etNombre.setText(fila.getString(0));
            etTelefono.setText(fila.getString(1));
        }else{
            Toast.makeText(this, "No existe ID de usuario", Toast.LENGTH_SHORT).show();
        }
    }

    //METODO ALTA
    private void alta(){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"Administracion",null, 1);
        SQLiteDatabase db = admin.getWritableDatabase();

        //LECTURA DE CAMPOS
        String idUsuario = etId.getText().toString();
        String nombre = etNombre.getText().toString();
        String telefono = etTelefono.getText().toString();

        //VARIABLES PARA REGISTRO
        ContentValues registro = new ContentValues();
        registro.put("idUsuario",idUsuario);
        registro.put("nombre",nombre);
        registro.put("telefono",telefono);

        //SE INSERTA EL REGISTRO EN LA BD
        db.insert("usuarios",null,registro);

        //SE CIERRA CONEXION A BD
        db.close();

        //SE LIMPIAN LOS CAMPOS
        etId.setText("");
        etNombre.setText("");
        etTelefono.setText("");

        Toast.makeText(this, "Usuario Registrado!", Toast.LENGTH_SHORT).show();
    }

    //METODO BORRAR
    private void borrar() {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "Administracion", null, 1);
        SQLiteDatabase db = admin.getWritableDatabase();

        //LECTURA DE ID A BORRAR
        String idUsuario = etId.getText().toString();

        //SE BORRA EL REGISTRO
        int cant = db.delete("usuarios", "idUsuario=" + idUsuario, null);

        //SE CIERRA CONEXION A BD
        db.close();

        //SE LIMPIAN LOS CAMPOS
        etId.setText("");
        etNombre.setText("");
        etTelefono.setText("");

        //EXISTA O NO EL ID SE ENVIA MENSAJE
        if (cant == 1) {
            Toast.makeText(this, "Usuario eliminado!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "ID de Usuario no registrado!", Toast.LENGTH_SHORT).show();
        }
    }

    //METODO EDITAR
    private void editar() {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "Administracion", null, 1);
        SQLiteDatabase db = admin.getWritableDatabase();

        //LECTURA DE CAMPOS
        String idUsuario = etId.getText().toString();
        String nombre = etNombre.getText().toString();
        String telefono = etTelefono.getText().toString();

        //VARIABLES PARA REGISTRO
        ContentValues registro = new ContentValues();
        registro.put("nombre",nombre);
        registro.put("telefono",telefono);

        //SE ACTUALIZA EL REGISTRO
        int cant = db.update("usuarios",registro,"idUsuario="+idUsuario,null);

        //SE CIERRA CONEXION A BD
        db.close();

        //SE LIMPIAN LOS CAMPOS
        etId.setText("");
        etNombre.setText("");
        etTelefono.setText("");

        //EXISTA O NO EL ID SE ENVIA MENSAJE
        if (cant == 1) {
            Toast.makeText(this, "ID de usuario actualizado!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "ID de Usuario no registrado!", Toast.LENGTH_SHORT).show();
        }
    }
}
