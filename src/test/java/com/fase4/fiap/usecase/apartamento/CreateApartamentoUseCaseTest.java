package com.fase4.fiap.usecase.apartamento;

import com.fase4.fiap.entity.apartamento.gateway.ApartamentoGateway;
import com.fase4.fiap.entity.apartamento.model.Apartamento;
import com.fase4.fiap.usecase.apartamento.dto.IApartamentoRegistrationData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CreateApartamentoUseCaseTest {

    private ApartamentoGateway apartamentoGateway;
    private CreateApartamentoUseCase useCase;

    @BeforeEach
    void setUp() {
        apartamentoGateway = mock(ApartamentoGateway.class);
        useCase = new CreateApartamentoUseCase(apartamentoGateway);
    }

    @Test
    @DisplayName("Deve criar apartamento com dados válidos")
    void shouldCreateApartamentoWithValidData() {
        IApartamentoRegistrationData dados = mock(IApartamentoRegistrationData.class);

        when(dados.torre()).thenReturn('A');
        when(dados.andar()).thenReturn((byte) 2);
        when(dados.numero()).thenReturn((byte) 24);

        Apartamento apartamentoMock = mock(Apartamento.class);
        when(apartamentoGateway.save(any(Apartamento.class))).thenReturn(apartamentoMock);

        Apartamento resultado = useCase.execute(dados);

        assertNotNull(resultado);
        verify(apartamentoGateway, times(1)).save(any(Apartamento.class));
    }

}
