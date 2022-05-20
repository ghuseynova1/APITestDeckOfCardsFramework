package client;

import io.restassured.response.Response;
import static io.restassured.RestAssured.*;

public class RestClient {
    public Response sendGetRequest (String uri) {
        return given().when().get(uri);
    }

    public Response sendGetRequestWithParam (String uri,String paramName, int parameter) {
        return given().queryParam(paramName,parameter).when().get(uri);
    }

}
