package com.fase4.fiap.bdd.coletaEncomenda;

import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.time.OffsetDateTime;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class CreateColetaEncomendaBDD {

    private String apartamentoId;
    private String recebimentoEncomendaId;
    private String coletaEncomendaJson;
    private Response response;
    private final String dataEntrega = OffsetDateTime.now().toString();

    @LocalServerPort
    private int port;

    @Dado("que foi criado um coletaEncomenda cadastrado - Criar")
    public void que_foi_criado_um_coleta_encomenda_cadastrado() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;

        String apartamentoJson = """
                {
                    "torre": "D",
                    "andar": "20",
                    "numero": "1"
                }
                """;

        Response responseApartamento = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(apartamentoJson)
                .post("/api/apartamentos");

        responseApartamento.then().statusCode(HttpStatus.CREATED.value());
        apartamentoId = responseApartamento.jsonPath().getString("id");
    }

    @E("que foi construído um apartamento com recebimentoEncomenda para coletaEncomenda - Criar")
    public void que_foi_construido_um_apartamento_com_recebimento_encomenda_para_coleta_encomenda() {
        String recebimentoEncomendaJson = String.format("""
                {
                    "apartamentoId": "%s",
                    "descricao": "Teste",
                    "dataEntrega": "%s",
                    "estadoColeta": "PENDENTE"
                }
                """, apartamentoId, dataEntrega);

        Response responseRecebimentoEncomenda = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(recebimentoEncomendaJson)
                .post("/api/recebimento-encomendas");

        responseRecebimentoEncomenda.then().statusCode(HttpStatus.CREATED.value());
        recebimentoEncomendaId = responseRecebimentoEncomenda.jsonPath().getString("id");

    }

    @E("que foi construído coletaEncomenda - Criar")
    public void que_foi_construido_um_coleta_encomenda() {
        coletaEncomendaJson = String.format("""
                {
                    "recebimentoEncomendaId": "%s",
                    "cpfMoradorColeta": "12345678919",
                    "nomeMoradorColeta": "Teste",
                    "dataColeta": "%s"
                }
                """, recebimentoEncomendaId, dataEntrega);
    }

    @Quando("o coletaEncomenda for cadastrado - Criar")
    public void o_coleta_encomenda_for_cadastrado() {
        response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(coletaEncomendaJson)
                .when()
                .post("/api/coleta-encomendas");
    }

    @Então("o coletaEncomenda será salvo no sistema - Criar")
    public void o_coleta_encomenda_sera_salvo_no_sistema() {
        response.then().statusCode(HttpStatus.CREATED.value());
    }

    @E("o coletaEncomenda deve estar no formato esperado - Criar")
    public void o_coleta_encomenda_deve_estar_no_formato_esperado() {
        response.then().body(matchesJsonSchemaInClasspath("schemas/coleta-encomenda.schema.json"));
    }

}