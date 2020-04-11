package com.pablo.zoologico.vista;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.pablo.zoologico.R;
import com.pablo.zoologico.control.UserTrs;
import com.pablo.zoologico.modelo.User;
import com.pablo.zoologico.util.DatePickerFragment;
import com.pablo.zoologico.util.Validator;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class RegisterActivity extends AppCompatActivity {
    private TextView textViewR;
    private Button buttonRegister;
    private EditText editTextName;
    private EditText editTextAge;
    private EditText editTextPassword1;
    private EditText editTextPassword2;
    private EditText editTextMail;
    private  Date dateAge;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        textViewR = (TextView) this.findViewById(R.id.textViewVolver);
        buttonRegister = (Button) findViewById(R.id.buttonSiguiente);
        editTextAge = (EditText) findViewById(R.id.editTextEdad);
        editTextName = (EditText) findViewById(R.id.editTextNombre);
        editTextMail = (EditText) findViewById(R.id.editTextCorreo);
        editTextPassword1 = (EditText) findViewById(R.id.editTextClave1);
        editTextPassword2 = (EditText) findViewById(R.id.editTextClave2);

        textViewR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                RegisterActivity.this.startActivity(intent);
            }

        });

    }

    public void register(View view) throws ParseException {
        //validations
        validateEmptyInputs();
        if (validateFields()) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

             dateAge = format.parse(editTextAge.getText().toString());
            try {
                validateInputs();
            } catch (Exception e) {
               Toast.makeText(this,"no se pudo registrar: "+e.getMessage(),Toast.LENGTH_SHORT).show();
            }

                if (UserTrs.availability(editTextMail.getText().toString())) {
                    if (validatePassword()) {
                        UserTrs admin = new UserTrs();




                        User user = new User(editTextName.getText().toString(), editTextMail.getText().toString(),
                                dateAge, editTextPassword1.getText().toString());
                        admin.save(user, this);
                        Intent intent = new Intent(this, LoginActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "ya existe ese usuario", Toast.LENGTH_SHORT).show();
                }

        } else {
            Toast.makeText(this,"llene los campos",Toast.LENGTH_SHORT).show();
        }


    }
    public void validateInputs() throws Exception {
        if(!Validator.validateMail(editTextMail.getText().toString())){
           throw new Exception("formato del mail invalido");
        }

        if(!Validator.validateDate(dateAge)){
            throw new Exception("debe tener 18 años");
        }
    }
    public void validateEmptyInputs(){

        if(editTextName.getText().toString().equals("")){
            editTextName.setError("Ingrese su nombre");
            editTextName.setFocusable(true);

        }
        if(editTextAge.getText().toString().equals("")){
            editTextAge.setError("Ingrese su edad");

        }
        if(editTextMail.getText().toString().equals("")){
            editTextMail.setError("Ingrese un mail");
            editTextMail.setFocusable(true);
        }

        if(editTextPassword1.getText().toString().equals("")){
            editTextPassword1.setError("Ingrese una contraseña");
            editTextPassword1.setFocusable(true);
        }
        if(editTextPassword2.getText().toString().equals("")){
            editTextPassword2.setError("Ingrese una contraseña");
            editTextPassword2.setFocusable(true);
        }

    }

    public boolean validateFields() {
        if (!editTextName.getText().toString().equals("") && !editTextMail.getText().toString().equals("")
                && !editTextAge.getText().toString().equals("") && !editTextPassword1.getText().toString().equals("")
                && !editTextPassword2.getText().toString().equals("")) {
            return true;

        }
        return false;
    }

    public boolean validatePassword() {
        if (editTextPassword1.getText().toString().equals(editTextPassword2.getText().toString()))
            return true;
        return false;
    }

    public void showDatePickerDialog(View view){
        DatePickerFragment newFragment=new DatePickerFragment(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String date=year+"-"+(month+1)+"-"+dayOfMonth;
                    editTextAge.setText(date);

            }
        });
        newFragment.show(getSupportFragmentManager(),"Date Picker");


    }


}
