package com.fase4.fiap.bdd.morador;

import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class CreateMoradorBDD {

    private String apartamentoId;
    private String moradorJson;
    private Response response;

    @LocalServerPort
    private int port;

    @Dado("que foi criado um apartamento cadastrado - Criar")
    public void que_foi_criado_um_apartamento_cadastrado() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;

        String apartamentoJson = """
                {
                    "torre": "G",
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

    @E("que foi construído um apartamento com morador - Criar")
    public void que_foi_construido_um_apartamento_com_morador() {
        moradorJson = String.format("""
                {
                    "cpf": "12345678900",
                    "nome": "Teste Total",
                    "telefone": ["11999999999"],
                    "email": "teste@hotmail.com",
                    "apartamentoId": ["%s"]
                }
                """, apartamentoId);
    }

    @Quando("o morador for cadastrado - Criar")
    public void o_morador_for_cadastrado() {
        response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(moradorJson)
                .when()
                .post("/api/moradores");
    }

    @Então("o morador será salvo no sistema - Criar")
    public void o_morador_sera_salvo_no_sistema() {
        response.then().statusCode(HttpStatus.CREATED.value());
    }

    @E("o morador deve estar no formato esperado - Criar")
    public void o_morador_deve_estar_no_formato_esperado() {
        response.then().body(matchesJsonSchemaInClasspath("schemas/morador.schema.json"));
    }

}