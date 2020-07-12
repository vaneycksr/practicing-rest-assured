package tests;

import domain.Resources;
import org.apache.http.HttpStatus;
import org.junit.Test;
import support.BaseTest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class ResourcesTest extends BaseTest {

    private static final String LIST_RESOURCE_ENDPOINT = "/unknown";
    private static final String SINGLE_RESOURCE_ENDPOINT = "/unknown/{resourceId}";

    @Test
    public void testValidarListaDeRecursosComSucesso(){

        given().
        when().
                get(LIST_RESOURCE_ENDPOINT).
        then().
                statusCode(HttpStatus.SC_OK).
                body(
                        "page",is(1),
                        "per_page",is(6),
                        "data.size()",is(6),
                        "total_pages",is(2),
                        "ad.company",is(startsWith("StatusCode")),
                        "ad.url",is("http://statuscode.org/"),
                        "ad.text",is(containsString("software development"))
                );

    }

    // NÃO FAZER DESSE JEITO
    @Test
    public void testValidarRecursoDeId1(){

        Resources resources = given().
        when().
                get(LIST_RESOURCE_ENDPOINT).
        then().
                statusCode(HttpStatus.SC_OK).
        extract().
                                    // primeiro elemento do array
                body().jsonPath().getObject("data[0]",Resources.class);

        assertThat(resources.getId(),is(1));
        assertThat(resources.getName(),is("cerulean"));
        assertThat(resources.getYear(),is(2000));
        assertThat(resources.getColor(),is("#98B2D1"));
        assertThat(resources.getPantone_value(),is("15-4020"));
    }

    @Test
    public void testValidarRecursoEspecifico(){

        Resources resources = given().
                pathParam("resourceId",6).
        when().
                get(SINGLE_RESOURCE_ENDPOINT).
        then().
                statusCode(HttpStatus.SC_OK).
        extract().
                body().jsonPath().getObject("data",Resources.class);

        assertThat(resources.getId(),is(6));
        assertThat(resources.getName(),is("blue turquoise"));
        assertThat(resources.getYear(),is(2005));
        assertThat(resources.getColor(),is("#53B0AE"));
        assertThat(resources.getPantone_value(),is("15-5217"));
    }

    @Test
    public void testValidarRecursoQueNaoExiste(){

        given().
                pathParam("resourceId",50).
        when().
                get(SINGLE_RESOURCE_ENDPOINT).
        then().
                statusCode(HttpStatus.SC_NOT_FOUND).
                body(equalTo("{}"));
    }

}
