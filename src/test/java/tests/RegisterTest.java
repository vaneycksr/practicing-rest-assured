package tests;

import domain.User;
import org.apache.http.HttpStatus;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import support.BaseTest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RegisterTest extends BaseTest {

    private static final String REGISTER_ENDPOINT = "/register";

    @Test
    public void testRegistrarComSucesso(){

        User register = new User();
        register.setEmail("eve.holt@reqres.in");
        register.setPassword("pistol");

        given().
                body(register).
        when().
                post(REGISTER_ENDPOINT).
        then().
                statusCode(HttpStatus.SC_OK).
                body("id",is(notNullValue())).
                body("token", is(notNullValue()));
    }

    @Test
    public void testRegistrarComEmailVazioESenhaCorreta(){

        User register = new User();
        register.setEmail("");
        register.setPassword("pistol");

        given().
                body(register).
        when().
                post(REGISTER_ENDPOINT).
        then().
                statusCode(HttpStatus.SC_BAD_REQUEST).
                body("error",is("Missing email or username"));

    }

    @Test
    public void testRegistrarComEmailNullESenhaCorreta(){

        User register = new User();
        register.setEmail(null);
        register.setPassword("pistol");

        given().
                body(register).
        when().
                post(REGISTER_ENDPOINT).
        then().
                statusCode(HttpStatus.SC_BAD_REQUEST).
                body("error",is("Missing email or username"));

    }

    @Test
    public void testRegistrarComEmailInvalidoESenhaCorreta(){

        User register = new User();
        register.setEmail("eve.holas#!asa");
        register.setPassword("pistol");

        given().
                body(register).
        when().
                post(REGISTER_ENDPOINT).
        then().
                statusCode(HttpStatus.SC_BAD_REQUEST).
                body("error",is("Note: Only defined users succeed registration"));

    }


    @Test
    public void testRegistroApenasComEmail(){

        User register = new User();
        register.setEmail("eve.holt@reqres.in");

        given().
                body(register).
        when().
                post(REGISTER_ENDPOINT).
        then().
                statusCode(HttpStatus.SC_BAD_REQUEST).
                body("error", is("Missing password"));
    }

    @Test
    public void testRegistrarComSenhaVaziaEEmailCorreto(){

        User register = new User();
        register.setEmail("eve.holt@reqres.in");
        register.setPassword("");

        given().
                body(register).
        when().
                post(REGISTER_ENDPOINT).
        then().
                statusCode(HttpStatus.SC_BAD_REQUEST).
                body("error",is("Missing password"));
    }

    @Test
    public void testRegistrarComSenhaNullEEmailCorreto(){

        User register = new User();
        register.setEmail("eve.holt@reqres.in");
        register.setPassword(null);

        given().
                body(register).
        when().
                post(REGISTER_ENDPOINT).
        then().
                statusCode(HttpStatus.SC_BAD_REQUEST).
                body("error",is("Missing password"));
    }

    @Test
    public void testRegistroApenasComSenha(){

        User register = new User();
        register.setPassword("pistol");

        given().
                body(register).
        when().
                post(REGISTER_ENDPOINT).
        then().
                statusCode(HttpStatus.SC_BAD_REQUEST).
                body("error", is("Missing email or username"));
    }

    @Test
    public void testRegistroSemNenhumCampo(){

        given().

        when().
                post(REGISTER_ENDPOINT).
        then().
                statusCode(HttpStatus.SC_BAD_REQUEST).
                body("error", is("Missing email or username"));
    }

    @Test
    public void testRegistrarComEmailVazioESenhaVazia(){

        User register = new User();
        register.setEmail("");
        register.setPassword("");

        given().
                body(register).
        when().
                post(REGISTER_ENDPOINT).
        then().
                statusCode(HttpStatus.SC_BAD_REQUEST).
                body("error", is("Missing email or username"));
    }

    @Test
    public void testRegistrarComEmailNullESenhaNull(){

        User register = new User();
        register.setEmail(null);
        register.setPassword(null);

        given().
                body(register).
        when().
                post(REGISTER_ENDPOINT).
        then().
                statusCode(HttpStatus.SC_BAD_REQUEST).
                body("error", is("Missing email or username"));
    }

    @Test
    public void testRegistrarComCampoAMaisQueNaoSejaEmailEPassword(){

        User register = new User();
        register.setEmail("eve.holt@reqres.in");
        register.setPassword("pistol");
        register.setJob("QA");

        given().
                body(register).
                when().
                post(REGISTER_ENDPOINT).
                then().
                statusCode(HttpStatus.SC_OK).
                body("id",is(notNullValue())).
                body("token", is(notNullValue()));
    }

    public String extrairTokenRegistroDoUsuario(){

        User register = new User();
        register.setEmail("eve.holt@reqres.in");
        register.setPassword("pistol");

        return given().
                body(register).
        when().
                post(REGISTER_ENDPOINT).
        then().
                statusCode(HttpStatus.SC_OK).
        extract().
                path("token");

    }

    // TODO implenentar os testes do recurso UNKNOWN

}
