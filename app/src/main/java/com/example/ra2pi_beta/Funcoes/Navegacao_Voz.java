package com.example.ra2pi_beta.Funcoes;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.view.KeyEvent;
import androidx.appcompat.app.AppCompatActivity;
import com.example.ra2pi_beta.MainActivity;
import java.text.Normalizer;
import java.util.ArrayList;


public class Navegacao_Voz extends AppCompatActivity implements TextToSpeech.OnInitListener {
    private static final int RECOGNIZE_SPEECH_ACTIVITY = 2;
    private static final int Reconhecedor_Voz = 7;
    private String ouvir;
    private ArrayList <Resposta> respostas;
    private TextToSpeech ler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inicializar();
    }

    @Override
    protected void onActivityResult ( int requestCode, int resultCode, Intent data ) {
        super.onActivityResult( requestCode, resultCode, data );

        if (resultCode == RESULT_OK && requestCode == Reconhecedor_Voz) {
            ArrayList<String> reconhecido =
                    data.getStringArrayListExtra(
                            RecognizerIntent.EXTRA_RESULTS );
            String ouve = reconhecido.get( 0 );
            ouvir = ouve;
            prepararRespuesta(ouve);
        }
    }

    private void prepararRespuesta ( String ouvir ) {
        String normalizar = Normalizer.normalize(
                ouvir, Normalizer.Form.NFD );
        String sint = normalizar.replaceAll(
                "[^\\p{ASCII}]", "" );

        for (int i = 0; i < respostas.size(); i++) {
            int resultado = sint.toLowerCase().indexOf(
                    respostas.get(i).getFala());
            if (resultado != -1) {
                responder(respostas.get(i));
                return;
            }
        }

    }

    private void responder (Resposta resposta) {
        startActivity(resposta.getIntent());
    }

    private void inicializar () {

        respostas = provarDados();

        ler = new TextToSpeech( this, this );
    }

    private ArrayList <Resposta> provarDados () {
        ArrayList <Resposta> resposta = new ArrayList <>();
        resposta.add( new Resposta( "scan",
                new Intent(this,
                        PlanoQRCodeActivity.class)));
        resposta.add( new Resposta( "inicio",
                new Intent(this,
                MainActivity.class)));

        return resposta;
    }

    @Override
    public boolean dispatchKeyEvent( KeyEvent event) {
        int action = event.getAction();
        int keyCode = event.getKeyCode();
        switch (keyCode) {
            case KeyEvent.KEYCODE_ENTER:
                if (action == KeyEvent.ACTION_DOWN) {
                        Intent falar = new Intent(
                                RecognizerIntent.ACTION_RECOGNIZE_SPEECH );
                        falar.putExtra(
                                RecognizerIntent.EXTRA_LANGUAGE_MODEL, "es-MX" );
                        startActivityForResult(
                                falar, Reconhecedor_Voz);
                }
                return true;
            default:
                return super.dispatchKeyEvent(event);
        }
    }

    @Override
    public void onInit(int status) {

    }
}
