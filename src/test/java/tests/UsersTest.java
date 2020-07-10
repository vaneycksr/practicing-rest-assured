package tests;

import org.apache.http.HttpStatus;
import org.junit.Test;
import support.BaseTest;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class UsersTest extends BaseTest {

    private static final String LIST_USERS_ENDPOINT = "/users";
    private static final String SINGLE_USER_ENDPOINT = "/users/{userId}";


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

    @Test
    public void testValidarUsuarioEspecifico(){

        given().
                pathParam("userId",2).

        when().
                get(SINGLE_USER_ENDPOINT).

        then().
                statusCode(HttpStatus.SC_OK);



    }


}
