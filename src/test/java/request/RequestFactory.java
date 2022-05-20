package request;

import client.RestClient;
import io.restassured.response.Response;

public class RequestFactory {
    RestClient restClient;

    public RequestFactory() {
        restClient = new RestClient();
    }

    public Response makeShuffle(){
        return restClient.sendGetRequest("/deck/new/shuffle");
    }

    public Response makeShuffleByParam(String key, int value) {
        return restClient.sendGetRequestWithParam("/deck/new/shuffle/",key,value);
    }

    public Response drawCardWithNewShuffledDeckByParam(String key,int value) {
        return restClient.sendGetRequestWithParam("/deck/new/draw",key,value);
    }

    public Response createNewDeck() {
        return restClient.sendGetRequest("/deck/new");
    }

    public Response drawCardWithDeckId(String deckId, String key, int value) {
        if(value != 0){
             return restClient.sendGetRequestWithParam("deck/"+deckId+"/draw",key,value);
        } else {
            return restClient.sendGetRequest("deck/"+ deckId + "/draw");
        }
    }

}
