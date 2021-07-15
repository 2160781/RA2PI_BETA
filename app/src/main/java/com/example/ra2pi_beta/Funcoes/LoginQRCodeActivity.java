package com.example.ra2pi_beta.Funcoes;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ra2pi_beta.MainActivity;
import com.example.ra2pi_beta.R;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

public class LoginQRCodeActivity extends AppCompatActivity {

    List<String> usernames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        usernames = new ArrayList<>();
        usernames.add("2160781");
        usernames.add("2192827");
        usernames.add("cjrf");
        usernames.add("onilferreira");


        setContentView(R.layout.activity_login_qrcode);
        new IntentIntegrator(this).initiateScan();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        String dados = result.getContents();
        Intent intent = null;
        for (String username : usernames) {
            if (username.equals(dados)) {
                intent = new Intent(this, MainActivity.class);

            }
        }


        if (intent != null){
            startActivity(intent);
        }else{
            Context context = getApplicationContext();
            CharSequence text = "QRCode invalido!";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            new IntentIntegrator(this).initiateScan();
        }

    }

    public List<String> getListUsersFile(){
        List<String> usernames = new ArrayList<>();

        usernames.add("2160781");
        usernames.add("2192827");
        usernames.add("cjrf");
        usernames.add("onilferreira");

        FileInputStream fis = null;

        return usernames;
    }

}