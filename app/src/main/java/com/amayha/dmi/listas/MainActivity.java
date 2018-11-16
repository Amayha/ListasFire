package com.amayha.dmi.listas;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class MainActivity extends AppCompatActivity {

    FirebaseDatabase db;
    ListView lv;

    FirebaseListAdapter<Usuario> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // paso 1 para vincular la base de datos
       db = FirebaseDatabase.getInstance();

       lv= findViewById(R.id.lv_usuarios);

       // paso 2 crear la consulta a la base de datos
        Query referencia = db.getReference().child("usuarios");

        //Paso 3: Firebaselist Options
        FirebaseListOptions<Usuario> options = new FirebaseListOptions.Builder<Usuario>()
                .setLayout(R.layout.renglonusuario)
                .setQuery(referencia,Usuario.class).build();

        //Paso 4 construir el adapatador que sera usado en la lista
         adapter = new FirebaseListAdapter<Usuario>(options) {
            @Override
            //el view que llega es el layout del renglon
            protected void populateView(View v, Usuario model, int position) {

                TextView nombre= v.findViewById(R.id.tv_nombre);
                TextView correo= v.findViewById(R.id.tv_correo);

                // le asigno los valores del modelo a la view renglon
                nombre.setText(model.getNombre());
                correo.setText(model.getCorreo());

                Log.d("calupe","Nombre: " + model.getNombre());

            }
        };// ya queda el adapter

        // agregar el adapter a la lista
        lv.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}
