package tests;

import domain.User;
import org.apache.http.HttpStatus;
import org.junit.Ignore;
import org.junit.Test;
import support.BaseTest;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class UsersTest extends BaseTest {

    private static final String USERS_ENDPOINT = "/users";
    private static final String SINGLE_USER_ENDPOINT = "/users/{userId}";


    @Test
    public void testValidarPaginaEspecifica(){

        given().
                // param -> como se fosse a query na URI
                param("page","2").
        when().
                get(USERS_ENDPOINT).
        then().
                statusCode(HttpStatus.SC_OK).
                body("page", is(2)).
                body("data", is(notNullValue()));

    }

    @Test
    public void testValidarUsuarioEspecifico(){

        User user = given().
                pathParam("userId",2).
        when().
                get(SINGLE_USER_ENDPOINT).
        then().
                statusCode(HttpStatus.SC_OK).

        //  na resposta do get, pega o campo data, e passa como parametro a classe na qual tem os atributos
        extract().
                body().jsonPath().getObject("data",User.class);

        assertThat(user.getEmail(),containsString("janet.weaver@"));
        assertThat(user.getFirst_name(),is("Janet"));
        assertThat(user.getLast_name(),is("Weaver"));

    }

    @Test
    public void testUsuarioNaoEncontrado(){

        given().
                pathParam("userId",50).

        when().
                get(SINGLE_USER_ENDPOINT).

        then().
                statusCode(HttpStatus.SC_NOT_FOUND).
                body(equalTo("{}"));

    }

    @Test
    public void testCriarUsuario(){

        Map<String, String> user = new HashMap<>();
        user.put("name","vaneyck");
        user.put("job","QA");

        given().
                body(user).
        when().
                post(USERS_ENDPOINT).
        then().
                statusCode(HttpStatus.SC_CREATED).
                body("name", is("vaneyck")).
                body("job", is("QA")).
                body("createdAt", is(notNullValue()));

    }

    @Test
    public void testCriaUsuarioApenasComCampoName(){

        Map<String, String> user = new HashMap<>();
        user.put("name","van");

        given().
                body(user).
        when().
                post(USERS_ENDPOINT).
        then().
                statusCode(HttpStatus.SC_CREATED).
                body("job",is(nullValue())).
                body("name",is("van")).
                body("createdAt",is(notNullValue()));

    }

    @Test
    public void testCriarUsuarioApenasComCampoJob(){

        Map<String, String> user = new HashMap<>();
        user.put("job","testador");

        given().
                body(user).
        when().
                post(USERS_ENDPOINT).
        then().
                statusCode(HttpStatus.SC_CREATED).
                body("name",is(nullValue())).
                body("job", is("testador")).
                body("createdAt",is(notNullValue()));

    }

    @Test
    public void testEditarUsuario(){

        Map<String, String> user = new HashMap<>();
        user.put("name","joao");
        user.put("job","dev");

        given().
                pathParam("userId",10).
                body(user).
        when().
                put(SINGLE_USER_ENDPOINT).
        then().
                statusCode(HttpStatus.SC_OK).
                body("name",is("joao")).
                body("job",is("dev")).
                body("updatedAt", is(notNullValue()));

    }

    @Test
    public void testEditarUsuarioApenasComCampoName(){

        Map<String, String> user = new HashMap<>();
        user.put("name","joao");

        given().
                pathParam("userId",10).
                body(user).
        when().
                put(SINGLE_USER_ENDPOINT).
        then().
                statusCode(HttpStatus.SC_OK).
                body("job",is(nullValue())).
                body("name",is("joao")).
                body("updatedAt",is(notNullValue()));

    }

    @Test
    public void testEditarUsuarioApenasComCampoJob(){

        Map<String, String> user = new HashMap<>();
        user.put("job","dev junior");

        given().
                pathParam("userId",10).
                body(user).
        when().
                put(SINGLE_USER_ENDPOINT).
        then().
                statusCode(HttpStatus.SC_OK).
                body("name",is(nullValue())).
                body("job",is("dev junior")).
                body("updatedAt",is(notNullValue()));
    }

    // O teste falha pois o retorno desse requisição não é do tipo JSON
    @Ignore
    public void testDeletarUsuario(){

        given().
                pathParam("userId",50).
        when().
                delete(SINGLE_USER_ENDPOINT).

        then().
                statusCode(HttpStatus.SC_NO_CONTENT);
    }

}
