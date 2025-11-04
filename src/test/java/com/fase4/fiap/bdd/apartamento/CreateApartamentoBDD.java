package com.fase4.fiap.bdd.apartamento;

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

public class CreateApartamentoBDD {

    private String apartamentoJson;
    private Response response;

    @LocalServerPort
    private int port;

    @Dado("que foi criado um apartamento - Criar")
    public void que_foi_criado_um_apartamento_cadastrado() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;

        apartamentoJson = """
            {
                "torre": "A",
                "andar": "20",
                "numero": "1"
            }
            """;
    }

    @Quando("o apartamento for cadastrado - Criar")
    public void o_apartamento_for_cadastrado() {
        response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(apartamentoJson)
                .post("/api/apartamentos");
    }

    @Então("o apartamento será salvo no sistema - Criar")
    public void o_apartamento_sera_salvo_no_sistema() {
        response.then().statusCode(HttpStatus.CREATED.value());
    }

    @E("o apartamento deve estar no formato esperado - Criar")
    public void o_apartamento_deve_estar_no_formato_esperado() {
        response.then().body(matchesJsonSchemaInClasspath("schemas/apartamento.schema.json"));
    }

}
