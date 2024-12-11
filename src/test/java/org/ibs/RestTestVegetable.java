package org.ibs;

import io.restassured.http.ContentType;
import io.restassured.http.Cookies;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class RestTestVegetable {

@Test
@DisplayName("Добавление товара типа 'Овощ' в список")

    void test1() {

        Specifications.installSpecification (
                Specifications.requestSpecification("https://qualit.appline.ru/api"),
                Specifications.responseSpecification(200)
        );

    Cookies addGoods = given()
            .basePath("/food")
            .body("{\n" +
                    "  \"name\": \"Момордика\",\n" +
                    "  \"type\": \"VEGETABLE\",\n" +
                    "  \"exotic\": true\n" +
                    "}")
            .contentType(ContentType.JSON)
            .when()
            .log().all()
            .post()
            .then()
            .log().all()
            .extract().detailedCookies();

    given()
            .cookies(addGoods)
            .basePath("/food")
            .body("{\n" +
                    "  \"name\": \"Картофель\",\n" +
                    "  \"type\": \"VEGETABLE\",\n" +
                    "  \"exotic\": false\n" +
                    "}")
            .contentType(ContentType.JSON)
            .when()
            .log().all()
            .post()
            .then()
            .log().all()
            .extract().detailedCookies();


    given()
            .cookies(addGoods)
            .basePath("/food")
            .when()
            .log().all()
            .get()
            .then()
            .log().all();

        given()
                .cookies(addGoods)
                .basePath("/data/reset")
                .when()
                .log().all()
                .post()
                .then()
                .log().all();

        given()
                .cookies(addGoods)
                .basePath("/food")
                .when()
                .log().all()
                .get()
                .then()
                .log().all();



    }
}
