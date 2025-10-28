package com.fase4.fiap.entity.coletaEncomenda.model;

import com.fase4.fiap.entity.auxiliary.AbstractEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class ColetaEncomenda extends AbstractEntity implements Serializable {

    @NonNull
    private UUID recebimentoEncomendaId;

    @NonNull
    private String cpfMoradorColeta;

    // Signature of the resident collecting the package
    @NonNull
    private String nomeMoradorColeta;

    @NonNull
    private OffsetDateTime dataColeta;

    public ColetaEncomenda(@NonNull UUID recebimentoEncomendaId, @NonNull String cpfMoradorColeta, @NonNull String nomeMoradorColeta, @NonNull OffsetDateTime dataColeta) {
        this.recebimentoEncomendaId = recebimentoEncomendaId;
        this.cpfMoradorColeta = cpfMoradorColeta;
        this.nomeMoradorColeta = nomeMoradorColeta;
        this.dataColeta = dataColeta;
    }

}
