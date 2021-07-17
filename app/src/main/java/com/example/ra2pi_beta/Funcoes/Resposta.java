package com.example.ra2pi_beta.Funcoes;

import android.content.Intent;

public class Resposta {
    private String fala;
    private Boolean resposta;
    private Intent intent;

    public Resposta(String fala, Boolean resposta, Intent intent ) {
        this.fala = fala;
        this.resposta = resposta;
        this.intent = intent;

    }


    public String getFala() {
        return fala;
    }

    public boolean getResposta(){
        return resposta;
    }

    public Intent getIntent () {
        return intent;
    }
}
