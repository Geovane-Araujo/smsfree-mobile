package com.example.sendsms;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.sendsms.models.Usuario;
import com.example.sendsms.uteis.Util;
import com.google.gson.Gson;

import org.w3c.dom.Text;

import java.util.Map;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Registro extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.INTERNET},15);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registrar);
    }
    public void onRetorno(View v){
        finish();
    }

    public void onRegistro(View v){

        Usuario us = new Usuario();
        Gson gson = new Gson();
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        ProgressDialog loading = new ProgressDialog(this);
        loading.setMessage("um minutinho, estamos criando sua conta");


        loading.show();

        TextView login = (TextView)findViewById(R.id.txt_registro_email);
        TextView nome = (TextView)findViewById(R.id.txt_registro_nome);
        TextView senha = (TextView)findViewById(R.id.txt_registro_senha);



        us.setNome(nome.getText().toString());
        us.setSenha(senha.getText().toString());
        us.setLogin(login.getText().toString());

        try{
            String json = gson.toJson(us);
            OkHttpClient client = new OkHttpClient();
            MediaType mediaType = MediaType.parse("application/json");
            RequestBody body = RequestBody.create(mediaType, json);
            Request request = new Request.Builder()
                    .url(Util.urlApi + "users")
                    .method("POST", body)
                    .addHeader("Content-Type", "application/json")
                    .build();
            Response response = client.newCall(request).execute();
            String res = response.body().string();
            Map obj = gson.fromJson(res,Map.class);

            if(obj.get("ret").equals("success")){
                finish();
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
