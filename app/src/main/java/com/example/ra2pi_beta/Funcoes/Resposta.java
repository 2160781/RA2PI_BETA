package com.example.ra2pi_beta.Funcoes;

import android.content.Intent;

public class Resposta {
    private String fala;
    private Intent intent;

    public Resposta(String fala, Intent intent ) {
        this.fala = fala;
        this.intent = intent;

    }


    public String getFala() {
        return fala;
    }


    public Intent getIntent () {
        return intent;
    }
}
