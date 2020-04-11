package com.pablo.zoologico.vista;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.pablo.zoologico.R;
import com.pablo.zoologico.control.UserTrs;
import com.pablo.zoologico.modelo.User;

import java.io.File;
import java.io.IOException;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

         File file=new File(getFilesDir(),"users.txt");
         if(file.exists()){
             UserTrs.listar(LoginActivity.this);
         }

    }

    public void showRegister(View view){
        Intent intent=new Intent(this,RegisterActivity.class);
        startActivity(intent);
    }

    public void access(View view ){

        EditText editTextMail=(EditText) findViewById(R.id.editTextCorreo);
        EditText editTextPassword=(EditText) findViewById(R.id.editTextPassword);

        UserTrs admin=new UserTrs();
        User userNow=admin.validate(editTextMail.getText().toString(),editTextPassword.getText().toString());

        if(userNow!=null){
                Intent intent=new Intent(this,MenuActivity.class);
                startActivity(intent);

        }else{
            Toast.makeText(this,"Los datos no coiciden",Toast.LENGTH_SHORT).show();
        }
    }
}
