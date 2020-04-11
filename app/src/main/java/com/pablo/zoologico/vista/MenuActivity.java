package com.pablo.zoologico.vista;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.pablo.zoologico.R;
import com.pablo.zoologico.modelo.Product;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    public void menu(View view){
        Intent intent;
        switch (view.getId()){

            case R.id.buttonAnimal:
                 intent=new Intent(this,AnimalActivity.class);
                startActivity(intent);

                Toast.makeText(this, "menuAnimal", Toast.LENGTH_SHORT).show();
                break;
            case R.id.buttonStore:
                 intent=new Intent(this, ProductActivity.class);
                 startActivity(intent);
                break;
            case R.id.buttonMap:
                Toast.makeText(this,"menuMap",Toast.LENGTH_SHORT).show();
                break;
        }


    }
}
