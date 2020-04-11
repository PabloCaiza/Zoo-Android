package com.pablo.zoologico.vista;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import com.google.gson.Gson;
import com.pablo.zoologico.control.ProductTrs;
import com.pablo.zoologico.util.DatePickerFragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.pablo.zoologico.R;
import com.pablo.zoologico.modelo.Product;

import java.io.ByteArrayOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ProductItemActivity extends AppCompatActivity {
    private Product product;
    private EditText editTextNameM,editTextCostM,editTextFabM,editTextExpM;
    private Button btnModificar;
    private ImageView imageViewPM;
    private final int OPEN_GALERY=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_item);
        editTextNameM=findViewById(R.id.editTextNamePM);
        editTextCostM=findViewById(R.id.editTextCostPM);
        editTextExpM=findViewById(R.id.editTextExPM);
        editTextFabM=findViewById(R.id.editTextFabPM);
        btnModificar=findViewById(R.id.buttonSavePM);
        imageViewPM=findViewById(R.id.imageView7);
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
        product=(Product) getIntent().getExtras().getParcelable("producto");
        //load all inputs that we can modified
        editTextNameM.setText(product.getName());
        editTextCostM.setText(String.valueOf(product.getCost()));
        editTextFabM.setText(format.format(product.getManufacture()));
        editTextExpM.setText(format.format(product.getExpire()));
        Bitmap bitmap= BitmapFactory.decodeByteArray(product.getImage(),0,product.getImage().length);
        imageViewPM.setImageBitmap(bitmap);
        //eligir fechas
        editTextExpM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(editTextExpM);
            }
        });
        editTextFabM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(editTextFabM);
            }
        });
        btnModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    modify();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
        imageViewPM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

    }

    public void openGallery(){
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"selecione image"),OPEN_GALERY);
    }


    public void modify() throws ParseException {
        if(validateField()){
           SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
           Date dateM=format.parse(editTextFabM.getText().toString());
           Date dateE=format.parse(editTextExpM.getText().toString());


            Product newProduct=new Product(editTextNameM.getText().toString(),dateM,dateE,
                    Double.parseDouble(editTextCostM.getText().toString()),convertirBytes());
            ProductTrs productTrs=new ProductTrs();
            productTrs.modify(product,newProduct);
            SharedPreferences sharedPreferences=getSharedPreferences("product",MODE_PRIVATE);
            SharedPreferences.Editor editor=sharedPreferences.edit();
            Gson gson=new Gson();
            editor.putString("list",gson.toJson(ProductTrs.listProduct));
            editor.commit();

            Intent intent=new Intent(this,MenuActivity.class);
            startActivity(intent);
        }
    }
    public byte[] convertirBytes(){
        Bitmap bitmap=((BitmapDrawable)imageViewPM.getDrawable()).getBitmap();
        ByteArrayOutputStream out=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,out);
        return out.toByteArray();
    }
    public boolean validateField(){
        if(!editTextNameM.getText().toString().equals("")&&!editTextCostM.getText().toString().equals("")
        &&!editTextFabM.getText().toString().equals("")&&!editTextExpM.getText().toString().equals("")){
            return true;
        }
        return false;
    }

    private void showDatePickerDialog(final EditText editText){
        DatePickerFragment datePickerDialog =new DatePickerFragment(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String fecha=year+"-"+(month+1)+"-"+dayOfMonth;
                editText.setText(fecha);
            }
        });
        datePickerDialog.show(getSupportFragmentManager(),"elije fecha");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==OPEN_GALERY){
            if(requestCode==RESULT_OK){
                imageViewPM.setImageURI(data.getData());
            }
        }
    }
}
