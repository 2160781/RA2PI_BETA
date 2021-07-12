package com.example.ra2pi_beta;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ra2pi_beta.Funcoes.PlanoQRCodeActivity;
import com.example.ra2pi_beta.Funcoes.activity_tarefas;
import com.example.ra2pi_beta.Informacao.PlayActivity;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    ListView listViewInicial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listviewInicial();

    }

    public boolean listviewInicial() {

        setContentView(R.layout.activity_lisviewinicial);

        listViewInicial = findViewById(R.id.listviewinicial);

        String[] values = new String[] {
                "Leitura de Qr Code",
                "Lista planos"
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
            }
        });

        return true;
    }

    public boolean listviewTarefas(){

        setContentView(R.layout.activity_listview);

        listView = findViewById(R.id.listview);

        String[] values = new String[] {
                "Receita de bolo - "
                        + PlayActivity.Main.dadosApp_.getNumeroPassosFeitos(0)
                        + " de " + PlayActivity.Main.dadosApp_.getSizeListPassos(0)
                , "Plantação de trigo - "
                + PlayActivity.Main.dadosApp_.getNumeroPassosFeitos(1)
                + " de " + PlayActivity.Main.dadosApp_.getSizeListPassos(1)
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,
                android.R.id.text1,values);

        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                position=position;
                if(position == 0){
                    Intent Tarefa1 = new Intent(view.getContext(),
                            activity_tarefas.class);
                    Tarefa1.putExtra("NumeroPlano",position);
                    startActivity(Tarefa1);
                }

                if (position == 1) {

                    Intent Tarefa2 = new Intent(view.getContext(),
                            activity_tarefas.class);
                    Tarefa2.putExtra("NumeroPlano",position);
                    startActivity(Tarefa2);
                }
            }
        });
        return true;
    }


}

