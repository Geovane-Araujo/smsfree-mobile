package com.example.sendsms.models;

import java.sql.Timestamp;
import java.util.List;

public class Mensagem {
    private int id;
    private String mensagem;
    private Timestamp DataAgendamento;
    private List<Destino> destinatarios;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public Timestamp getDataAgendamento() {
        return DataAgendamento;
    }

    public void setDataAgendamento(Timestamp dataAgendamento) {
        DataAgendamento = dataAgendamento;
    }

    public List<Destino> getDestinatarios() {
        return destinatarios;
    }

    public void setDestinatarios(List<Destino> destinatarios) {
        this.destinatarios = destinatarios;
    }
}
