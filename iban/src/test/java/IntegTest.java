import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.Test;

import static com.jayway.restassured.RestAssured.given;
import static org.junit.Assert.assertNotNull;

/**
 * Created by DRamadan on 23-Nov-16.
 */
public class IntegTest {
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
}
