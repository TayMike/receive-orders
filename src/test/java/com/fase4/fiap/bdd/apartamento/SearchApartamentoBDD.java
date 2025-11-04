package com.fase4.fiap.bdd.apartamento;

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

public class SearchApartamentoBDD {

    private Response response;

    @LocalServerPort
    private int port;

    @Dado("que existem vários apartamentos cadastrados - Search")
    public void que_existem_varios_apartamentos_cadastrados() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;

        for(int i = 1; i <= 2; i++) {
            String apartamentoJson = String.format("""
                    {
                        "torre": "A",
                        "andar": "20",
                        "numero": "1%d"
                    }
                    """, i);

            given()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(apartamentoJson)
                    .post("/api/apartamentos")
                    .then()
                    .statusCode(HttpStatus.CREATED.value());
        }
    }

    @Quando("os apartamentos forem buscados - Search")
    public void os_apartamentos_forem_buscados() {
        response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("/api/apartamentos");
    }

    @Então("os apartamentos serão retornados no sistema - Search")
    public void os_apartamentos_serao_retornados_no_sistema() {
        response.then()
                .statusCode(HttpStatus.OK.value())
                .body("$", not(empty()));
    }

}