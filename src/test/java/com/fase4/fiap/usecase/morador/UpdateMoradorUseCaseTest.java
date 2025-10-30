package com.fase4.fiap.usecase.morador;

import com.fase4.fiap.entity.apartamento.exception.ApartamentoNotFoundException;
import com.fase4.fiap.entity.apartamento.gateway.ApartamentoGateway;
import com.fase4.fiap.entity.morador.exception.MoradorNotFoundException;
import com.fase4.fiap.entity.morador.gateway.MoradorGateway;
import com.fase4.fiap.entity.morador.model.Morador;
import com.fase4.fiap.usecase.morador.dto.IMoradorUpdateData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UpdateMoradorUseCaseTest {

    private MoradorGateway moradorGateway;
    private ApartamentoGateway apartamentoGateway;
    private UpdateMoradorUseCase useCase;

    @BeforeEach
    void setUp() {
        moradorGateway = mock(MoradorGateway.class);
        apartamentoGateway = mock(ApartamentoGateway.class);
        useCase = new UpdateMoradorUseCase(moradorGateway, apartamentoGateway);
    }

    @Test
    @DisplayName("Deve atualizar nome, telefone e apartamentoId quando dados válidos são fornecidos")
    void shouldUpdateNomeTelefoneAndApartamentoIdWhenValidDataProvided()
            throws MoradorNotFoundException, ApartamentoNotFoundException {

        UUID moradorId = UUID.randomUUID();
        UUID apartamentoId = UUID.randomUUID();

        IMoradorUpdateData dados = mock(IMoradorUpdateData.class);
        Morador morador = mock(Morador.class);
        when(apartamentoGateway.findById(apartamentoId)).thenReturn(Optional.of(mock()));
        when(dados.nome()).thenReturn("João Silva");
        when(dados.telefone()).thenReturn(List.of("11999999999"));
        when(dados.apartamentoId()).thenReturn(List.of(apartamentoId));
        when(moradorGateway.findById(moradorId)).thenReturn(Optional.of(morador));
        when(moradorGateway.update(morador)).thenReturn(morador);

        Morador resultado = useCase.execute(moradorId, dados);

        assertNotNull(resultado);
        verify(morador).setNome("João Silva");
        verify(morador).setTelefone(List.of("11999999999"));
        verify(morador).setApartamentoId(List.of(apartamentoId));
        verify(moradorGateway).update(morador);
        verify(apartamentoGateway).findById(apartamentoId);
    }

    @Test
    @DisplayName("Deve lançar MoradorNotFoundException quando morador não existe")
    void shouldThrowMoradorNotFoundExceptionWhenMoradorDoesNotExist() {
        UUID moradorId = UUID.randomUUID();
        IMoradorUpdateData dados = mock(IMoradorUpdateData.class);

        when(moradorGateway.findById(moradorId)).thenReturn(Optional.empty());

        assertThrows(MoradorNotFoundException.class, () -> useCase.execute(moradorId, dados));

        verify(moradorGateway).findById(moradorId);
        verify(moradorGateway, never()).update(any());
        verify(apartamentoGateway, never()).findById(any());
    }

    @Test
    @DisplayName("Deve lançar ApartamentoNotFoundException quando apartamento não existe")
    void shouldThrowApartamentoNotFoundExceptionWhenApartamentoDoesNotExist() {
        UUID moradorId = UUID.randomUUID();
        UUID apartamentoId = UUID.randomUUID();

        IMoradorUpdateData dados = mock(IMoradorUpdateData.class);
        Morador morador = mock(Morador.class);

        when(moradorGateway.findById(moradorId)).thenReturn(Optional.of(morador));
        when(dados.apartamentoId()).thenReturn(List.of(apartamentoId));
        when(apartamentoGateway.findById(apartamentoId)).thenReturn(Optional.empty());

        assertThrows(ApartamentoNotFoundException.class, () -> useCase.execute(moradorId, dados));

        verify(apartamentoGateway).findById(apartamentoId);
        verify(moradorGateway, never()).update(any());
    }

    @Test
    @DisplayName("Deve ignorar campos nulos ou vazios")
    void shouldIgnoreNullOrEmptyFields() throws MoradorNotFoundException, ApartamentoNotFoundException {
        UUID moradorId = UUID.randomUUID();
        UUID apartamentoId = UUID.randomUUID();

        IMoradorUpdateData dados = mock(IMoradorUpdateData.class);
        Morador morador = mock(Morador.class);

        when(moradorGateway.findById(moradorId)).thenReturn(Optional.of(morador));
        when(dados.nome()).thenReturn("   ");
        when(dados.telefone()).thenReturn(List.of());
        when(dados.apartamentoId()).thenReturn(List.of(apartamentoId));
        when(apartamentoGateway.findById(apartamentoId)).thenReturn(Optional.of(mock()));
        when(moradorGateway.update(morador)).thenReturn(morador);

        Morador resultado = useCase.execute(moradorId, dados);

        assertNotNull(resultado);

        verify(morador, never()).setNome(any());
        verify(morador, never()).setTelefone(any());
        verify(morador).setApartamentoId(List.of(apartamentoId));
        verify(moradorGateway).update(morador);
    }

}