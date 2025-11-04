package com.fase4.fiap.bdd.recebimentoEncomenda;

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

public class CreateRecebimentoEncomendaBDD {

    private String apartamentoId;
    private String recebimentoEncomendaJson;
    private Response response;
    private final String dataEntrega = OffsetDateTime.now().toString();

    @LocalServerPort
    private int port;

    @Dado("que foi criado um recebimentoEncomenda cadastrado - Criar")
    public void que_foi_criado_um_recebimento_encomenda_cadastrado() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;

        String apartamentoJson = """
                {
                    "torre": "M",
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

    @E("que foi construído um apartamento com recebimentoEncomenda - Criar")
    public void que_foi_construido_um_recebimento_encomenda_com_recebimento_encomenda() {
        recebimentoEncomendaJson = String.format("""
                {
                    "apartamentoId": "%s",
                    "descricao": "Teste",
                    "dataEntrega": "%s",
                    "estadoColeta": "PENDENTE"
                }
                """, apartamentoId, dataEntrega);
    }

    @Quando("o recebimentoEncomenda for cadastrado - Criar")
    public void o_recebimento_encomenda_for_cadastrado() {
        response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(recebimentoEncomendaJson)
                .when()
                .post("/api/recebimento-encomendas");
    }

    @Então("o recebimentoEncomenda será salvo no sistema - Criar")
    public void o_recebimento_encomenda_sera_salvo_no_sistema() {
        response.then().statusCode(HttpStatus.CREATED.value());
    }

    @E("o recebimentoEncomenda deve estar no formato esperado - Criar")
    public void o_recebimento_encomenda_deve_estar_no_formato_esperado() {
        response.then().body(matchesJsonSchemaInClasspath("schemas/recebimento-encomenda.schema.json"));
    }

}