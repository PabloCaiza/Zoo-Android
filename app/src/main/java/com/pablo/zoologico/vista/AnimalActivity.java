package com.pablo.zoologico.vista;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;
import com.pablo.zoologico.R;
import com.pablo.zoologico.modelo.Animal;
import com.pablo.zoologico.modelo.Product;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;

public class AnimalActivity extends AppCompatActivity implements AnimalAdater.ItemClickListener {
    private Spinner spinnerType,spinnerHabit;
    private  RecyclerView rw;
    public static  AnimalAdater adpater;
    private ArrayList<Animal>listAnimal;
    private Button buttonSave;
    private final int IMAGE_GALERY=1;
    private EditText editTextName;
    private ImageView imvAnima;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animal);
        spinnerHabit=(Spinner)findViewById(R.id.spinnerHabit);
        spinnerType=(Spinner)findViewById(R.id.spinnerType);
        editTextName=(EditText)findViewById(R.id.editTextName);
        buttonSave=(Button)findViewById(R.id.buttonSaveAnimal);
        rw=(RecyclerView)findViewById(R.id.rwAnimal);
        imvAnima=(ImageView)findViewById(R.id.imageViewAnimal);
        listAnimal=new ArrayList<>();
        Gson gson=new Gson();
        SharedPreferences sharedPreferences=getSharedPreferences("animal",MODE_PRIVATE);
        String list=sharedPreferences.getString("listAnimal",null);
        Animal[] listA=gson.fromJson(list,Animal[].class);
        adpater=new AnimalAdater(listAnimal,this);
        if(listA!=null){
            copyArray(listAnimal,listA);
            adpater.notifyDataSetChanged();
        }

        rw.setLayoutManager(new LinearLayoutManager(this));
        rw.setAdapter(adpater);



        String[]opcionesH={"terrestre","marino","jungla"};
        String[]opcionesT={"carnívoro ","herbívoro","omnívoro "};
        ArrayAdapter opH=new ArrayAdapter(this,R.layout.spinner_layout,opcionesH);
        ArrayAdapter opT=new ArrayAdapter(this,R.layout.spinner_layout,opcionesT);
        spinnerType.setAdapter(opH);
        spinnerHabit.setAdapter(opT);

    }
    public void copyArray(ArrayList<Animal>listD, Animal[] listO) {
        for (int i = 0; i < listO.length; i++) {
            listD.add(listO[i]);
        }
    }

    public void saveAnimal(View view){
        if(!editTextName.getText().toString().equals("")){
            String type=spinnerType.getSelectedItem().toString();
            String habit=spinnerHabit.getSelectedItem().toString();
            String name=editTextName.getText().toString();

            Animal animal=new Animal(name,type,habit,convertirImageView());
            listAnimal.add(animal);
            adpater.notifyDataSetChanged();
            Gson gson=new Gson();
            SharedPreferences preferences=getSharedPreferences("animal",MODE_PRIVATE);
            String list=gson.toJson(listAnimal);
            SharedPreferences.Editor editor=preferences.edit();
            editor.putString("listAnimal",list);
            editor.commit();



        }else{
           editTextName.setError("ponga un nombre");
           editTextName.setFocusable(true);
        }

    }
    public byte[]convertirImageView(){
        Bitmap bitmap=((BitmapDrawable)imvAnima.getDrawable()).getBitmap();
        ByteArrayOutputStream out=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,out);
        return out.toByteArray();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    public void openGalery(View view ){
        Intent inent=new Intent();
        //el tipo
        inent.setType("image/*");
        //accion
        inent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(inent,"Seleccione imagen"),IMAGE_GALERY);

    }

    /**
     * recupera parametrso de salida
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==IMAGE_GALERY){
            if(resultCode==RESULT_OK){
                Uri uri=data.getData();
                imvAnima.setImageURI(uri);

            }else{
                Toast.makeText(this,"error al escoger imagen",Toast.LENGTH_SHORT).show();
            }
        }
    }



    @Override
    public void onClick(int position) {
        Toast.makeText(this,String.valueOf(position),Toast.LENGTH_SHORT).show();
    }

}
