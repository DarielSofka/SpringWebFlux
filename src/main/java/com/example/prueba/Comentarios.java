package com.example.prueba;

import java.util.ArrayList;
import java.util.List;

public class Comentarios {

    private List<String> comentarios;

    public Comentarios() {
        this.comentarios = new ArrayList<>();
    }

    public void addComentarios(String comentario) {
        comentarios.add(comentario);
    }

    @Override
    public String toString() {
        return "Comentarios{" +
                "comentarios=" + comentarios +
                '}';
    }
}
