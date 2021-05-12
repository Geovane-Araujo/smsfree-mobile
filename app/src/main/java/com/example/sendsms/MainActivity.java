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

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.internal.Internal;

public class MainActivity extends AppCompatActivity implements Runnable {
    public static final int MY_PERMISSION_SEND_SMS = 10;
    public static int button;
    public static String log = "";
    public static TextView tx_log;
    public static String url = "http://192.168.1.191:8088/sms/v1/checkshipping";
    public static String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.SEND_SMS},10);
        ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.INTERNET},15);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void EnviarSMS(View v) {
        Button p = (Button)findViewById(R.id.button_enviar);
        TextView tk = (TextView)findViewById(R.id.id_token);
        tx_log = (TextView)findViewById(R.id.id_log);
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
                tx_log.append("\n Serviço Parado");
            }
        }
    }

    public void sendMessage(List<Mensagem> mensagens){
        SmsManager smsManager = SmsManager.getDefault();
        int i = 0;
        try{

            for(Mensagem men: mensagens){
                for(Destino des : men.getDestinatarios()){
                    final ArrayList<String> parts = smsManager.divideMessage(men.getMensagem());
                    smsManager.sendMultipartTextMessage(des.getFone(),null,parts, null,null);
                    tx_log.post(new Runnable() {
                        public void run() {
                            tx_log.append("\n Enviado para:  " + des.getFone());
                        }
                    });
                    i += 1;
                }
            }
            int finalI = i;
            tx_log.post(new Runnable() {
                public void run() {
                    tx_log.append("\n Enviado " + finalI+ " Mensagens");
                }
            });

        }catch (Exception e){
            tx_log.post(new Runnable() {
                public void run() {
                    tx_log.append("\n " + e.getMessage());
                }
            });
        }
    }
    public void getMessages(){
        try{
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(url)
                    .method("GET", null)
                    .addHeader("Authorization", token)
                    .build();
            Response response = client.newCall(request).execute();
            String json = response.body().string();
            Gson gs = new Gson();
            Map obj = gs.fromJson(json,Map.class);
            String a = gs.toJson(obj.get("obj"));
            ObjectMapper objM = new ObjectMapper();
            List<Mensagem> men = objM.readValue(a, new TypeReference<List<Mensagem>>(){});
            tx_log.post(new Runnable() {
                public void run() {
                    tx_log.append("\n Busca Concluida encontrado "+men.size()+" SMs");
                }
            });
            sendMessage(men);
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void run() {
        try {
            while(button == 0){
                tx_log.post(new Runnable() {
                    public void run() {
                        tx_log.append("\n Buscando SMSs...");
                    }
                });
                System.out.println("Thread Rodando");
                getMessages();
                Thread.sleep(5000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}