package com.example.ra2pi_beta.Funcoes;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.view.KeyEvent;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ra2pi_beta.MainActivity;
import com.example.ra2pi_beta.R;

import java.text.Normalizer;
import java.util.ArrayList;

public class activity_NavegacaoVoz extends AppCompatActivity implements TextToSpeech.OnInitListener {

    private static final int RECOGNIZE_SPEECH_ACTIVITY = 2;
    private static final int RECONHECEDOR_VOZ = 7;
    private TextView ouve;
    private TextView resposta;
    private ArrayList <Resposta> respostas;
    private TextToSpeech ler;
    private Object TextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navegacaovoz);
        inicializar();
    }

    @Override
    protected void onActivityResult ( int requestCode, int resultCode, Intent data ) {
        super.onActivityResult( requestCode, resultCode, data );

        if (resultCode == RESULT_OK && requestCode == RECONHECEDOR_VOZ) {
            ArrayList<String> reconocido =
                    data.getStringArrayListExtra( RecognizerIntent.EXTRA_RESULTS );
            String escuchado = reconocido.get( 0 );
            ouve.setText( escuchado );
            prepararRespuesta( escuchado );
        }
    }

    private void prepararRespuesta ( String escuchado ) {
        String normalizar = Normalizer.normalize( escuchado, Normalizer.Form.NFD );
        String sintilde = normalizar.replaceAll( "[^\\p{ASCII}]", "" );

        for (int i = 0; i < respostas.size(); i++) {
            int resultado = sintilde.toLowerCase().indexOf( respostas.get( i ).getFala() );
            if (resultado != -1) {
                responder( respostas.get( i ) );
                return;
            }
        }

    }

    private void responder ( Resposta resposta ) {
        startActivity( resposta.getIntent() );

    }


    private void inicializar () {
        ouve = (TextView) findViewById( R.id.textView );

        resposta = ( TextView ) findViewById( R.id.tvRespuesta );
        respostas = provarDados();

        ler = new TextToSpeech( this, this );
    }

    private ArrayList <Resposta> provarDados () {
        ArrayList <Resposta> respostas = new ArrayList <>();
        respostas.add( new Resposta( "scan", "  ",new Intent(this,
                PlanoQRCodeActivity.class)));
        respostas.add( new Resposta( "qr code", "  ",new Intent(this,
                PlanoQRCodeActivity.class)));
        respostas.add( new Resposta( "inicio", " ",new Intent(this,
                MainActivity.class)));

        return respostas;
    }

    @Override
    public boolean dispatchKeyEvent( KeyEvent event) {
        int action = event.getAction();
        int keyCode = event.getKeyCode();
        switch (keyCode) {
            case KeyEvent.KEYCODE_ENTER:
                if (action == KeyEvent.ACTION_DOWN) {
                        Intent falar = new Intent( RecognizerIntent.ACTION_RECOGNIZE_SPEECH );
                        falar.putExtra( RecognizerIntent.EXTRA_LANGUAGE_MODEL, "es-MX" );
                        startActivityForResult( falar, RECONHECEDOR_VOZ);
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
