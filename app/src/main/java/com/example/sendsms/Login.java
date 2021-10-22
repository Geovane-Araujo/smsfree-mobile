package com.example.sendsms;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class Login extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
    }

    public void onLogar(View v){
        setContentView(R.layout.activity_main);
    }
    public void onRegistrar(View v){
        Intent intent = new Intent(Login.this,Registro.class);
        startActivity(intent);
    }
    public void onAlert(View v){

        ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Carma aew, verificando login...");
        dialog.show();

//        AlertDialog.Builder alert = new AlertDialog.Builder(this);
//        alert.setTitle("Olá");
//        alert.setMessage("Erro ao Criar View");
//        alert.show();
    }
}
