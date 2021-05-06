package com.example.sendsms;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements Runnable {
    public static final int MY_PERMISSION_SEND_SMS = 10;
    public static int button;
    public static String log = "";
    public static TextView tx_log;
    public static String url = "http://192.168.0.104:8088/sms/v1/";
    public static String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.SEND_SMS},10);
        ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.INTERNET},15);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //tx_log = (TextView)findViewById(R.id.txt_logs);
    }

    public void EnviarSMS(View v) {
        Button p = (Button)findViewById(R.id.button_enviar);
        TextView tk = (TextView)findViewById(R.id.id_token);
        if(!tk.equals("")){
            token = tk.getText().toString();
            if(p.getText().equals("Iniciar Serviço")){
                p.setText("Parar Serviço");
                button = 0;
                MainActivity ma = new MainActivity();
                Thread thr = new Thread(ma);
                thr.start();
            }
            else{
                p.setText("Iniciar Serviço");
                button = 1;
            }
        }

    }

    public void sendMessage(String[] numeros, String mensagem){
        SmsManager smsManager = SmsManager.getDefault();
        try{
            for(String n : numeros){
                final ArrayList<String> parts = smsManager.divideMessage(mensagem);
                smsManager.sendMultipartTextMessage(n,null,parts, null,null);
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    public void getMessages(){
        try{
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(url + "checkshipping")
                    .method("GET", null)
                    .addHeader("Authorization", token)
                    .build();
            Response response = client.newCall(request).execute();
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void run() {
        try {
            while(button == 0){
                //tx_log.setText(log);
                log += "\n Thread rodando";
                System.out.println("Thread Rodando");
                getMessages();
                Thread.sleep(5000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}