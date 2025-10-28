package com.fase4.fiap.entity.apartamento.model;

import com.fase4.fiap.entity.auxiliary.AbstractEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class Apartamento extends AbstractEntity implements Serializable {

    @NonNull
    private char torre;

    @NonNull
    private byte andar;

    @NonNull
    private byte numero;

    public Apartamento(@NonNull char torre, @NonNull byte andar, @NonNull byte numero) {
        this.torre = torre;
        this.andar = andar;
        this.numero = numero;
    }


}
