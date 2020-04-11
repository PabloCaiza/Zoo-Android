package com.pablo.zoologico.vista;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.pablo.zoologico.R;
import com.pablo.zoologico.control.ProductTrs;
import com.pablo.zoologico.modelo.Product;
import com.pablo.zoologico.util.DatePickerFragment;
import com.pablo.zoologico.util.ProductAdapter;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class ProductActivity extends AppCompatActivity implements ProductAdapter.ItemClickListener {
    private EditText editTextNameP,editTextCostP,editTextFabP,editTextExP;
    private ImageView imageViewP;
    private ProductAdapter productAdapter;
    private RecyclerView rw;
    private final int IMAGE_P=2;
    private int position=-1;
    private BottomNavigationView bottomNV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        editTextNameP=findViewById(R.id.editTextNameP);
        editTextCostP=findViewById(R.id.editTextCostP);
        imageViewP=findViewById(R.id.imageViewP);
        editTextFabP=findViewById(R.id.editTextFabP);
        editTextExP=findViewById(R.id.editTextExP);
        bottomNV=findViewById(R.id.bottomNV);
        rw=findViewById(R.id.rwP);

        rw.setLayoutManager(new LinearLayoutManager(this));


        imageViewP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                ProductActivity.this.startActivityForResult(Intent.createChooser(intent,"selecione imagen"),IMAGE_P);
            }
        });
        editTextFabP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(editTextFabP);

            }
        });
        editTextExP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(editTextExP);
            }
        });


        bottomNV.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.menuItemEliminar:
                        if(position!=-1){
                            final int auxP=position;
                            final AlertDialog.Builder alertDialog=new AlertDialog.Builder(ProductActivity.this);
                            alertDialog.setTitle("Eliminar Producto");
                            alertDialog.setMessage("¿Desea eliminar el producto?");
                            alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
                            alertDialog.setPositiveButton("si", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    System.out.println("************");
                                    System.out.println(auxP);
                                    ProductTrs.listProduct.remove(auxP);
                                    productAdapter.notifyDataSetChanged();
                                    //save in the shared preference
                                    SharedPreferences preferences=getSharedPreferences("product",MODE_PRIVATE);
                                    SharedPreferences.Editor editor=preferences.edit();
                                    Gson gson=new Gson();
                                    editor.putString("list",gson.toJson(ProductTrs.listProduct));
                                    editor.commit();
                                }
                            });
                            alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                        alertDialog.setCancelable(true);


                                }
                            });
                            alertDialog.show();


                        }
                        position=-1;
                        Toast.makeText(ProductActivity.this,"eliminar",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.menuItemRegresar:
                        Intent intent=new Intent(ProductActivity.this,MenuActivity.class);
                        startActivity(intent);

                        break;
                }
                return true;


            }
        });

        //creacion de la lista

        SharedPreferences sharedPreferences=getSharedPreferences("product",MODE_PRIVATE);
        Gson gson =new Gson();
        String list=sharedPreferences.getString("list",null);
        //Listar
        productAdapter = new ProductAdapter(ProductTrs.listProduct,this);
        rw.setAdapter(productAdapter);
        Product [] listA=gson.fromJson(list,Product[].class);
        if(listA!=null) {
            copyArray(ProductTrs.listProduct, listA);
            productAdapter.notifyDataSetChanged();
        }

    }
    public void copyArray(ArrayList<Product>listD,Product[] listO){
        listD.clear();
        for (int i=0;i<listO.length;i++){
            listD.add(listO[i]);
        }

    }
    public void save(View view) throws ParseException {
        if(validateFields()){
            String name=editTextNameP.getText().toString();
            double cost=Double.parseDouble(editTextCostP.getText().toString());
            Date  dateM,dateE;
            SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
            dateM=format.parse(editTextFabP.getText().toString());
            dateE=format.parse(editTextExP.getText().toString());
            //crearmos un objeto
            Product product=new Product(name,dateM,dateE,cost,convertirBytes(imageViewP));
            ProductTrs.listProduct.add(product);
            productAdapter.notifyDataSetChanged();
            Gson gson=new Gson();
            //creando un shared preferences u obteniento
            SharedPreferences preferences =getSharedPreferences("product",MODE_PRIVATE);
            //editor de shared preference para guardar o editar
            SharedPreferences.Editor editor=preferences.edit();
            String list=gson.toJson(ProductTrs.listProduct);
            editor.putString("list",list);
            editor.commit();

        }else{
            Toast.makeText(this,"Llene los campos",Toast.LENGTH_SHORT).show();
        }

    }
    public boolean validateFields(){
        boolean validate=true;
        if(editTextNameP.getText().toString().equals("")){
            editTextNameP.setError("ingrese un nombre");
            validate=false;
        }
        if(editTextCostP.getText().toString().equals("")){
            editTextCostP.setError("ingrese un costo");
            validate=false;
        }
        if(editTextFabP.getText().toString().equals("")){
            validate=false;
        }
        if(editTextExP.getText().toString().equals("")){
            validate=false;
        }


        return validate;
    }
    public void showDatePickerDialog(final EditText editText){
        DatePickerFragment datePickerFragment=new DatePickerFragment(new DatePickerDialog.OnDateSetListener() {
            //metodo que se ejecuta cada que el usaurio cambia la fecha
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String fecha=year+"-"+(month+1)+"-"+dayOfMonth;
                editText.setText(fecha);
            }
        });
        //mostramos
        datePickerFragment.show(this.getSupportFragmentManager(),"Date Picker");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==IMAGE_P){
            if(resultCode==RESULT_OK){
                Uri uri=data.getData();
                imageViewP.setImageURI(uri);
            }
        }
    }

    @Override
    public void onClick(int position) {
      this.position=position;
    Toast.makeText(this,String.valueOf(position),Toast.LENGTH_SHORT).show();


    }

    @Override
    public void onLongClick(int position) {
        Intent intent=new Intent(this,ProductItemActivity.class);
        intent.putExtra("producto",ProductTrs.listProduct.get(position));
        startActivity(intent);

    }
    public byte[]convertirBytes(ImageView imageView){
        //obtener mapa de bits
        Bitmap bitmap=((BitmapDrawable)imageView.getDrawable()).getBitmap();
        ByteArrayOutputStream out=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,out);
        return out.toByteArray();
    }
}
