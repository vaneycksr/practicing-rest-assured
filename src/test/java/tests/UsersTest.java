package tests;

import org.apache.http.HttpStatus;
import org.junit.Test;
import support.BaseTest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class UsersTest extends BaseTest {

    private static final String LIST_USERS_ENDPOINT = "/users";


    @Test
    public void testValidarPaginaEspecifica(){

        given().
                param("page","2").

        when().
                get(LIST_USERS_ENDPOINT).

        then().
                statusCode(HttpStatus.SC_OK).
                body("page", is(2)).
                body("data", is(notNullValue()));

    }


}
