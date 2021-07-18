package com.example.ra2pi_beta;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ra2pi_beta.Funcoes.Navegacao_Voz;
import com.example.ra2pi_beta.Funcoes.PlanoQRCodeActivity;
import com.example.ra2pi_beta.Funcoes.Resposta;
import com.example.ra2pi_beta.Funcoes.activity_tarefas;
import com.example.ra2pi_beta.Informacao.PlayActivity;

import java.text.Normalizer;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    ListView listViewInicial;

    private static final int RECONHECEDOR_VOZ = 7;
    private ArrayList <Resposta> respostas;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       Intent Falar = new Intent( RecognizerIntent.ACTION_RECOGNIZE_SPEECH );
       Falar.putExtra( RecognizerIntent.EXTRA_LANGUAGE_MODEL, "es-MX" );
       startActivityForResult( Falar, RECONHECEDOR_VOZ);
        listviewInicial();
    }

    public boolean listviewInicial() {
        
        setContentView(R.layout.activity_lisviewinicial);

        listViewInicial = findViewById(R.id.listviewinicial);

        String[] values = new String[] {
                "Leitura de Qr Code",
                "Lista planos",
                "Microfone"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,
                android.R.id.text1,values);

        listViewInicial.setAdapter(adapter);

        listViewInicial.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                position=position;
                if(position == 0){
                    Intent scan = new Intent(view.getContext(),
                            PlanoQRCodeActivity.class);
                    startActivity(scan);
                }

                if (position == 1) {
                    listviewTarefas();
                }
                if (position == 2) {
                    Intent micro = new Intent(view.getContext(),
                            Navegacao_Voz.class);
                    startActivity(micro);
                }
            }
        });

        return true;
    }

    public boolean listviewTarefas(){

        setContentView(R.layout.activity_listview);

        listView = findViewById(R.id.listview);

        int numP = PlayActivity.Main.dadosApp_.getNumeroPlanos();

        String[] values = new String[numP];

        for(int i = 0; i < PlayActivity.Main.dadosApp_.getNumeroPlanos(); i++) {
            values[i] = "" + PlayActivity.Main.dadosApp_.getTextPlano(i) + " - "
                    + PlayActivity.Main.dadosApp_.getNumeroPassosFeitos(i)
                    + " de " + PlayActivity.Main.dadosApp_.getSizeListPassos(i);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,
                android.R.id.text1,values);

        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                position=position;

                for(int p = 0; p < PlayActivity.Main.dadosApp_.getNumeroPlanos(); p++) {
                    if(p == position){
                        Intent Tarefa = new Intent(view.getContext(),
                                activity_tarefas.class);
                        Tarefa.putExtra("NumeroPlano",position);
                        startActivity(Tarefa);
                    }
                }
            }
        });
        return true;
    }


    @Override
    protected void onActivityResult ( int requestCode, int resultCode, Intent data ) {
        super.onActivityResult( requestCode, resultCode, data );

        if (resultCode == RESULT_OK && requestCode == RECONHECEDOR_VOZ) {
            ArrayList<String> reconhecido =
                    data.getStringArrayListExtra( RecognizerIntent.EXTRA_RESULTS );
            String ouvir = reconhecido.get(0);
            prepararResposta(ouvir);
        }
    }

    private void prepararResposta ( String ouvir ) {
        String normalizar = Normalizer.normalize( ouvir, Normalizer.Form.NFD );
        String sintilde = normalizar.replaceAll("[^\\p{ASCII}]", "" );


        for (int i = 0; i < respostas.size(); i++) {
            int resultado = sintilde.toLowerCase().indexOf(respostas.get(i).getFala());
            if (resultado != -1) {
                responder(respostas.get(i));
                return;
            }
        }

    }

    private void responder (Resposta resposta) {
        startActivity(resposta.getIntent());
    }

//mudar colocar dentro da resposta  a resposta e na fala o que é dito utilizar um set e um get para
    //obter/mudificar a resposta
   /* private void inicializar() {

        respostas = provarDados();

    }
*/
    private ArrayList <Resposta> provarDados() {
        ArrayList <Resposta> resposta = new ArrayList <>();
        String funcao;

        //Navegção para o QR Code
        resposta.add( new Resposta( "scan",
                null, new Intent(this,
               PlanoQRCodeActivity.class)));
        resposta.add( new Resposta( "can", null
                ,new Intent(this,
                PlanoQRCodeActivity.class)));
        resposta.add( new Resposta( "qr",
                null,new Intent(this,
                PlanoQRCodeActivity.class)));
        resposta.add( new Resposta( "code",
                null,new Intent(this,
                PlanoQRCodeActivity.class)));

        //Navegação para o menu dos planos
        resposta.add( new Resposta( "tarefas",
                listviewTarefas(),new Intent(this, null)));
        resposta.add( new Resposta( "planos",
                listviewTarefas(),new Intent(this, null)));


        return respostas;
    }
}

