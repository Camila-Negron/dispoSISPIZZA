package com.example.sispizza;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    HomeFragment homeFragment;
    PizzaFragment pizzaFragment;
    GuarnicionFragment guarnicionFragment;
    EnsaladaFragment ensaladaFragment;
    BebidaFragment bebidaFragment;

    Button btuno, btdos,  bttres, btcuatro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        homeFragment = new HomeFragment();
        pizzaFragment = new PizzaFragment();
        guarnicionFragment = new GuarnicionFragment();
        ensaladaFragment = new EnsaladaFragment();
        bebidaFragment = new BebidaFragment();

        btuno = findViewById(R.id.btUno);
        btdos = findViewById(R.id.btDos);
        bttres = findViewById(R.id.btTres);
        btcuatro = findViewById(R.id.btCuatro);

        //getSupportFragmentManager().beginTransaction().add(R.id.flContainer1,homeFragment).commit();

        if (getIntent().getBooleanExtra("inicio_sesion_exitoso", false)) {
            // Si el inicio de sesión fue exitoso, reemplaza el fragmento actual con HomeFragment
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.flContainer1, homeFragment)
                    .commit();
        }

        btuno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction().replace(R.id.flContainer1,pizzaFragment).commit();
            }
        });

        btdos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction().replace(R.id.flContainer1,guarnicionFragment).commit();
            }
        });

        bttres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction().replace(R.id.flContainer1,ensaladaFragment).commit();
            }
        });

        btcuatro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction().replace(R.id.flContainer1,bebidaFragment).commit();
            }
        });


    }
}