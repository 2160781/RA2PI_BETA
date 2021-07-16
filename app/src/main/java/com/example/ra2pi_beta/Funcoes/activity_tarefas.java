package com.example.ra2pi_beta.Funcoes;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ra2pi_beta.Informacao.PlayActivity;
import com.example.ra2pi_beta.MainActivity;
import com.example.ra2pi_beta.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class activity_tarefas extends AppCompatActivity {

    boolean estadoBoton;
    Button boton;
    TextView textV;
    int numeroPlano = 0;
    int posicao = 0;
    String estado;
    ImageView ver_imagen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tarefas);

        estadoBoton=true;
        boton=findViewById(R.id.Button);
        textV= findViewById(R.id.textView);
        ver_imagen=findViewById(R.id.imageView);

        numeroPlano = getIntent().getIntExtra("NumeroPlano",0);

        if(PlayActivity.Main.dadosApp_.getFeito(numeroPlano,0) == false){
            estado = " Por fazer";
            ver_imagen.setImageResource(R.drawable.errado);
        }else{
            estado = " Feito";
            ver_imagen.setImageResource(R.drawable.certo);
        }

        textV.setText(PlayActivity.Main.dadosApp_.getTextTarefa(numeroPlano,posicao)+":"
                + estado);

    }

    //Metodos
    @Override
    public boolean dispatchKeyEvent( KeyEvent event) {
        int action = event.getAction();
        int keyCode = event.getKeyCode();
        //int PassosFeitos1 = PlayActivity.Main.dadosApp.getPassosFeitos();
        switch (keyCode) {
            //Seguinte
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                if (action == KeyEvent.ACTION_DOWN) {
                    if(posicao<PlayActivity.Main.dadosApp_.getNumeroTarefasDePlano(numeroPlano)-1){
                        posicao++;


                        if(PlayActivity.Main.dadosApp_.getFeito(numeroPlano, posicao) == false){
                            estado = " Por fazer";
                            ver_imagen.setImageResource(R.drawable.errado);
                        }else{
                            estado = " Feito";
                            ver_imagen.setImageResource(R.drawable.certo);
                        }
                        textV.setText(PlayActivity.Main.dadosApp_.getTextTarefa(numeroPlano,
                                posicao)+" : " + estado);

                        estadoBoton= false;
                    }
                }
                return true;
            //Anterior
            case KeyEvent.KEYCODE_DPAD_LEFT:
                if (action == KeyEvent.ACTION_DOWN) {
                    if(posicao>0) {
                        posicao--;
                        estadoBoton = false;
                        if(PlayActivity.Main.dadosApp_.getFeito(numeroPlano,posicao) == false){
                            estado = " Por fazer";
                            ver_imagen.setImageResource(R.drawable.errado);
                        }else{
                            estado = " Feito";
                            ver_imagen.setImageResource(R.drawable.certo);
                        }
                        textV.setText(PlayActivity.Main.dadosApp_.getTextTarefa(numeroPlano,posicao)
                                + " : " + estado);

                    }
                }
                return true;
            //Marcar passo como "Feito"
            case KeyEvent.KEYCODE_DPAD_UP:
                if (action == KeyEvent.ACTION_DOWN) {
                    //Colocar a posição da tarefa "Feito"
                    int position = posicao;
                    if(PlayActivity.Main.dadosApp_.getFeito(numeroPlano,position) == false){
                        PlayActivity.Main.dadosApp_.marcarFeito(numeroPlano,position);
                        ver_imagen.setImageResource(R.drawable.certo);
                        estado = " Feito";
                        textV.setText(PlayActivity.Main.dadosApp_.getTextTarefa(numeroPlano,posicao)
                                + " : " + estado);
                        updateJSON(numeroPlano,position,true);
                    }
                }
                return true;
            //Marcar passo como "Por fazer"
            case KeyEvent.KEYCODE_DPAD_DOWN:
                if (action == KeyEvent.ACTION_DOWN) {

                    int position = posicao;
                    if(PlayActivity.Main.dadosApp_.getFeito(numeroPlano,position) == true){
                        PlayActivity.Main.dadosApp_.marcarErrado(numeroPlano,position);
                        ver_imagen.setImageResource(R.drawable.errado);
                        estado = " Por fazer";
                        textV.setText(PlayActivity.Main.dadosApp_.getTextTarefa(numeroPlano,posicao)
                                + " : " + estado);
                        updateJSON(numeroPlano,position,false);
                    }
                }
                return true;
            case KeyEvent.KEYCODE_ENTER:
                if (action == KeyEvent.ACTION_DOWN) {

                    Intent inicio = new Intent(this,
                            MainActivity.class);
                    startActivity(inicio);
                }
                return true;
            default:
                return super.dispatchKeyEvent(event);
        }

    }

    public void updateJSON(int plano, int tarefa, boolean estado){
        String json;

        try{
            InputStream is = activity_tarefas.this.getAssets().open("ListaPlano.txt");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            json = new String(buffer, "UTF-8");
            JSONArray jsonArray = new JSONArray(json);

            for(int i = 0; i<jsonArray.length();i++){

                if (i == plano){
                    JSONObject obj = jsonArray.getJSONObject(i);

                    JSONArray passos = obj.getJSONArray("passos");

                    for (int o = 0; o < passos.length(); o++){
                        JSONObject itemArr = (JSONObject) passos.get(o);
                        JSONObject itemARR = (JSONObject) jsonArray.get(i);

                        if (tarefa == o){
                            itemArr.put("feito",estado);
                            itemARR.put("passos",passos);

                            File targetFile = new File("src//main//assets//ListaPlano.txt");
                            //OutputStream outStream = new FileOutputStream(targetFile);
                            System.out.println(jsonArray);
                        }
                    }
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = this.getAssets().open("ListaPlano.txt");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }


    public void updateJSO(int plano, int tarefa, boolean estado) {
        String json;

        try{
            InputStream is = activity_tarefas.this.getAssets().open("ListaPlano.txt");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();




        }catch (IOException e){
            e.printStackTrace();
        }
    }
}