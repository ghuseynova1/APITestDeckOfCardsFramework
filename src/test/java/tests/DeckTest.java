package tests;

import com.aventstack.extentreports.Status;
import io.restassured.response.Response;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.iterableWithSize;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import request.UtilActions;

@ExtendWith(BaseTest.class)
public class DeckTest extends BaseTest {

    @Test
    @DisplayName("Verify shuffled deck by default - /deck/new/shuffle")
    public void getSuffledDeck() {
        Response response = requestFactory.makeShuffle();
        extentReport.addLog(Status.INFO,response.asPrettyString());
        extentReport.addLog(Status.INFO,"Status: "+response.statusCode());
        response.then().log().all().statusCode(200)
                .body("success",equalTo(true))
                .body("deck_id", equalTo(response.body().path("deck_id")))
                .body("remaining", equalTo(52))
                .body("shuffled", equalTo(true));
    }

    @Test
    @DisplayName("Verify shuffled deck by parameter - /deck/new/shuffle/?deck_count=2")
    public void getShuffledDeckByParam() {
        Response response = requestFactory.makeShuffleByParam("deck_count",2);
        extentReport.addLog(Status.INFO,response.asPrettyString());
        extentReport.addLog(Status.INFO,"Status: "+response.statusCode());
        response.then().log().all();
        response.then().statusCode(200)
                .body("success", equalTo(true))
                .body("deck_id",equalTo(response.body().path("deck_id")))
                .body("remaining",equalTo(104))
                .body("shuffled", equalTo(true));
    }

    @Test
    @DisplayName("Verify shuffled deck with count greater than the limit quantity - /deck/new/shuffle/?deck_count=21")
    public void getShuffledDeckWithExceededLimitCount() {
        Response response = requestFactory.makeShuffleByParam("deck_count",21);
        extentReport.addLog(Status.INFO, response.asPrettyString());
        extentReport.addLog(Status.INFO,"Status: "+response.statusCode());
        response.then().log().all();
        response.then().statusCode(200)
                .body("success", equalTo(false))
                .body("error", equalTo("The max number of Decks is 20."));
    }

    @Test
    @Tag("Failing")
    @DisplayName("Verify shuffled deck with unknown key - /deck/new/shuffle/?deck_countssssss=2")
    public void getShuffledDeckWithUnknownKey () {
        Response response = requestFactory.makeShuffleByParam("deck_countssssss",2);
        extentReport.addLog(Status.INFO,response.asPrettyString());
        extentReport.addLog(Status.INFO,"Status: "+response.statusCode());
        response.then().log().all();
        Assertions.assertEquals(400, response.statusCode(),"The parameter/key was unknown and result should be 400 ");
    }

    @Test
    @DisplayName("Verify draw card with new shuffled deck and parameter - /deck/new/draw/?count=20")
    public void drawCardWithNewShuffledDeck() {
        Response response = requestFactory.drawCardWithNewShuffledDeckByParam("count",20);
        extentReport.addLog(Status.INFO,response.asPrettyString());
        extentReport.addLog(Status.INFO,"Status: "+response.statusCode());
        response.then().log().all();
        response.then().statusCode(200)
                .body("success",equalTo(true))
                .body("deck_id",equalTo(response.body().path("deck_id")))
                .body("cards", iterableWithSize(20))
                .body("remaining", equalTo(32));
    }

    @Test
    @DisplayName("Verify draw card with valid deck and parameter - deck/<<deck_id>>/draw/?count=42")
    public void drawCardWithValidDeckId () {
        String deck_id = UtilActions.getNewDeck();
        Response response = requestFactory.drawCardWithDeckId(deck_id, "count",42);
        extentReport.addLog(Status.INFO,response.asPrettyString());
        extentReport.addLog(Status.INFO,"Status: "+response.statusCode());
        response.then().log().all();
        response.then().statusCode(200)
                .body("success",equalTo(true))
                .body("deck_id", equalTo(deck_id))
                .body("cards", iterableWithSize(42))
                .body("remaining",equalTo(10));
    }

    @Test
    @DisplayName("Verify draw card with valid deck with no parameter - deck/<<deck_id>>/draw")
    public void drawCardWithValidDeckIdNoParam () {
        String deck_id = UtilActions.getNewDeck();
        Response response = requestFactory.drawCardWithDeckId(deck_id,"count",0);
        extentReport.addLog(Status.INFO,response.asPrettyString());
        extentReport.addLog(Status.INFO,"Status: "+response.statusCode());
        response.then().log().all();
        response.then().statusCode(200)
                .body("success",equalTo(true))
                .body("deck_id", equalTo(deck_id))
                .body("cards",iterableWithSize(1))
                .body("remaining",equalTo(51));
    }

    @Test
    @DisplayName("Draw card with unknow deck - deck/<<deck_id>>/draw")
    public void drawCardWithUnknownDeckIdNoParam() {
        Response response = requestFactory.drawCardWithDeckId("pco15x5hdgfz6","count",0);
        extentReport.addLog(Status.INFO, response.asPrettyString());
        extentReport.addLog(Status.INFO,"Status: "+response.statusCode());
        response.then().log().all();
        response.then().statusCode(404)
                .body("success",equalTo(false))
                .body("error", equalTo("Deck ID does not exist."));
    }

    @Test
    @Tag("Failing")
    @DisplayName("Draw cardw with unknown key/parameter - /deck/new/draw/?count=4")
    public void drawCardWithUnknownKey () {
        Response response =requestFactory.drawCardWithNewShuffledDeckByParam("countrrrrrr",4);
        extentReport.addLog(Status.INFO,response.asPrettyString());
        extentReport.addLog(Status.INFO,"Status: "+response.statusCode());
        response.then().log().all();
        Assertions.assertEquals(400,response.statusCode(),
                "The parameter/key was unknown and result should be 400");

    }

}
