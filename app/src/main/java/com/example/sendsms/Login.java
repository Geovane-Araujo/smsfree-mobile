package com.example.sendsms;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.StrictMode;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.sendsms.models.LoginUser;
import com.example.sendsms.models.Usuario;
import com.example.sendsms.uteis.Util;
import com.google.gson.Gson;

import java.util.Map;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

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

    public void onLogin(View v){

        LoginUser us = new LoginUser();
        Gson gson = new Gson();
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        ProgressDialog loading = new ProgressDialog(this);


        try{

            TextView login = (TextView)findViewById(R.id.txt_email);
            TextView senha = (TextView)findViewById(R.id.txt_senha);

            us.setSenha(senha.getText().toString());
            us.setLogin(login.getText().toString());

            String json = gson.toJson(us);
            OkHttpClient client = new OkHttpClient();
            MediaType mediaType = MediaType.parse("application/json");
            RequestBody body = RequestBody.create(mediaType, json);
            Request request = new Request.Builder()
                    .url(Util.urlApi + "login")
                    .method("POST", body)
                    .addHeader("Content-Type", "application/json")
                    .build();
            Response response = client.newCall(request).execute();
            String res = response.body().string();
            Map obj = gson.fromJson(res,Map.class);

            loading.dismiss();

            if(obj.get("ret").equals("success")){
                Intent intent = new Intent(Login.this,MainActivity.class);

                String a = gson.toJson(obj.get("obj"));
                Usuario usuario = gson.fromJson(a,Usuario.class);
                intent.putExtra("usuario", usuario);
                startActivity(intent);
            }
            else {
                alert.setMessage(obj.get("motivo").toString());
                alert.show();
            }

        } catch (Exception ex){
            alert.setMessage(ex.getMessage());
            alert.show();
        }
    }
}
