package com.example.sispizza;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import java.util.ArrayList;
import android.app.AlertDialog;
import android.content.DialogInterface;


import com.example.sispizza.database.DatabaseHelper;

public class MainActivity extends AppCompatActivity {

    HomeFragment homeFragment;
    PizzaFragment pizzaFragment;
    GuarnicionFragment guarnicionFragment;
    EnsaladaFragment ensaladaFragment;
    BebidaFragment bebidaFragment;

    ListaPedidosActivity listaPedidosActivity;


    Button btuno, btdos,  bttres, btcuatro,btCarrito, btsalir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        homeFragment = new HomeFragment();
        pizzaFragment = new PizzaFragment();
        guarnicionFragment = new GuarnicionFragment();
        ensaladaFragment = new EnsaladaFragment();
        bebidaFragment = new BebidaFragment();


        btsalir = findViewById(R.id.salir);

        //getSupportFragmentManager().beginTransaction().add(R.id.flContainer1,homeFragment).commit();

        if (getIntent().getBooleanExtra("inicio_sesion_exitoso", true)) {
            // Si el inicio de sesión fue exitoso, reemplaza el fragmento actual con HomeFragment
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.flContainer1, pizzaFragment)
                    .commit();
        }



        btsalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                //builder.setTitle("Confirmación");
                builder.setMessage("¿Desea salir de la aplicación?");
                builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //Toast.makeText(MainActivity.this, "Sí", Toast.LENGTH_SHORT).show();
                        finish();
                        System.exit(0);
                    }
                });
                builder.setNegativeButton("No", null);
                builder.create().show();
            }
        });





    }
    private void abrirListaPedidos() {
        Intent intent = new Intent(this, ListaPedidosActivity.class);
        startActivity(intent);
    }


}