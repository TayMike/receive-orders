package com.fase4.fiap.bdd.morador;

import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.not;

public class SearchByApartamentoMoradorBDD {

    private Response response;
    private String apartamentoId;

    @LocalServerPort
    private int port;

    @Dado("que existem vários moradores cadastrados - SearchByApartamentoMorador")
    public void que_existem_varios_moradores_cadastrados() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;

        for (int i = 1; i <= 2; i++) {
            String apartamentoJson = String.format("""
                    {
                        "torre": "J",
                        "andar": "20",
                        "numero": "1%d"
                    }
                    """, i);

            apartamentoId = given()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(apartamentoJson)
                    .post("/api/apartamentos")
                    .then()
                    .statusCode(HttpStatus.CREATED.value())
                    .extract()
                    .path("id");

            String moradorJson = String.format("""
                {
                    "cpf": "1234567891%d",
                    "nome": "Teste Total",
                    "telefone": ["11999999999"],
                    "email": "teste@hotmail.com",
                    "apartamentoId": ["%s"]
                }
                """, i, apartamentoId);

            given()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(moradorJson)
                    .post("/api/moradores")
                    .then()
                    .statusCode(HttpStatus.CREATED.value());
        }
    }

    @Quando("os moradores forem buscados - SearchByApartamentoMorador")
    public void os_moradores_forem_buscados() {
        response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("/api/moradores/apartamentos/" + apartamentoId);
    }

    @Então("os moradores serão retornados no sistema - SearchByApartamentoMorador")
    public void os_moradores_serao_retornados_no_sistema() {
        response.then()
                .statusCode(HttpStatus.OK.value())
                .body("$", not(empty()));
    }

}
