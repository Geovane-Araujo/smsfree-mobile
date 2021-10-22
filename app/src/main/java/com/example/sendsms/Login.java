package com.example.sendsms;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class Login extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.SEND_SMS},10);
        ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.RECEIVE_SMS},10);
        ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.INTERNET},15);
        ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.ACCESS_NETWORK_STATE},15);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
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
