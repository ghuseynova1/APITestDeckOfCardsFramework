package request;

import io.restassured.response.Response;
import tests.BaseTest;

public class UtilActions extends BaseTest {

    public static String getNewDeck () {
        Response response = requestFactory.createNewDeck();

        if(response.getStatusCode() != 200) {
            throw new RuntimeException("Failed to get new deck of cards: " + response.getStatusLine());
        }

        return response.body().path("deck_id");
    }
}
