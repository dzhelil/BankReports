package com.estafet.integration;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.Test;

import javax.servlet.http.HttpServletResponse;

import static com.jayway.restassured.RestAssured.given;
import static org.apache.camel.builder.Builder.simple;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertNotNull;

/**
 * Created by DRamadan on 23-Nov-16.
 */
public class JettyIntegrationTest {
    @Test
    public void testManyRequests() throws InterruptedException {

        for (int i= 0; i < 100; i++) {

            Thread.sleep(500);

            String response = given()
                .contentType(ContentType.JSON)
                .body("{\n" +
                        "\"ibans\": [\n" +
                        "\" BG66 ESTF 0616 0000 0000 01\",\n" +
                        "\" BG66 ESTF 0616 0000 0000 02\",\n" +
                        "\" BG66 ESTF 0616 0000 0000 03\"\n" +
                        "]\n" +
                        "}\n")
                .when()
                .post("http://localhost:20616/estafet/iban/report")
                .then()
                .assertThat()
                .statusCode(200)
                    .extract()
                    .response()
                    .asString();

            assertNotNull(response);
        }
    }

    @Test
    public void challengeRouteJettyInsertAccountsWithJibberish() {

        Response response = given()
                //@formatter:off
                .contentType(ContentType.JSON)
                .body(simple("Not valid json!!!"))
                .when()
                .post("http://localhost:20616/estafet/iban/report")
                .then()
                .assertThat()
                .statusCode(HttpServletResponse.SC_INTERNAL_SERVER_ERROR)
                .assertThat()
                .body(equalTo("Something went wrong."))
                //@formatter:on
                .extract().response();
    }
}
