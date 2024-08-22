package com.example.firstProject.Service;


import com.google.gson.Gson;
import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
@RequiredArgsConstructor
public class SearchService {

    @Autowired
    private final RestTemplate restTemplate;

    @Autowired
    private final Gson gson;

    private final String apiUrl = "https://api-weua.vusion-dev.io";

    private final String authorization =
            "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImtpZCI6IktRMnRBY3JFN2xCYVZWR0JtYzVGb2JnZEpvNCJ9.eyJhdWQiOiJmMWFhNTQ1Mi04YTA3LTQ4NmYtYjA3NC05Nzk2OGE1MGY2MjUiLCJpc3MiOiJodHRwczovL2xvZ2luLm1pY3Jvc29mdG9ubGluZS5jb20vMDI2ZjNmOTctZjQ2My00YzQxLWJhNzQtYmMxNTZjM2JlNDk0L3YyLjAiLCJpYXQiOjE3MjQzMTQzMDIsIm5iZiI6MTcyNDMxNDMwMiwiZXhwIjoxNzI0MzE4MjAyLCJhaW8iOiJBWVFBZS84WEFBQUF3RXRqcHFTRmpNWnovZzRBMlY3alYyZVFlVzR5aHh6a3Yzd1Q1a2JlSG9rbW5EdCtSK1EyU3BFSGVkZWprdUxwNHNDVU80dTJoQm1oVXFTVkdGSnlDV0FpV2F3UzBLTHJaK2dTcytSTnNGV2dsbXVQS0U5Y21PUlYrVDJYSEcwSGhFcCtMcW9ZTGN3MzZwdjNFbThtZElJeTZhSW8yWUhmUndXSjBLRXh3QTA9IiwiYXRfaGFzaCI6ImZXdElZSUhNVnJyQW9VYS1IMTgyOXciLCJlbWFpbCI6ImFkZWwuaGFtbWljaGVAdnVzaW9uLmNvbSIsIm5hbWUiOiJIQU1NSUNIRSBBZGVsIiwibm9uY2UiOiJmMWJlODc5Ni1kNzhmLTQ1NGQtODRkOS1kZGQ0MDgyNGQzNmMiLCJvaWQiOiIxN2FlNDg5Zi0yZmM1LTRkZWItYTdmYi1jZGZmMGEzNDNkZDUiLCJwcmVmZXJyZWRfdXNlcm5hbWUiOiJhZGVsLmhhbW1pY2hlQHZ1c2lvbi5jb20iLCJyaCI6IjAuQVZ3QWx6OXZBbVAwUVV5NmRMd1ZiRHZrbEZKVXF2RUhpbTlJc0hTWGxvcFE5aVZjQU9NLiIsInN1YiI6IkVrUkl4cGQ4cmhMTTFrQ3ZacVlFdHlYNUUtMl9LZjZaVWF0akQtRmpjV2ciLCJ0aWQiOiIwMjZmM2Y5Ny1mNDYzLTRjNDEtYmE3NC1iYzE1NmMzYmU0OTQiLCJ1cG4iOiJhZGVsLmhhbW1pY2hlQHZ1c2lvbi5jb20iLCJ1dGkiOiJIYjgxMDctTVlrV2JxM3FmZWhWVEFBIiwidmVyIjoiMi4wIn0.YPLr7SQiIvgFzrppeqW3MJ0HPNUvLlpYuzI23k4ciBwosBdV2jPpP-qRI31VLo7ssAuQ2tRI9zvyX-xNog6_NPRYgFWxSWdgA4KUyLtj79GMnyoplcVfQDWz7Puz0oqGPpt1X509WXfxQI07Dvy2pYokp6NUFUHue9V05kHfco1Df8Q5hiyX79ClaV9mp-zNqBsL72_47LXV5-FeRTN7VH5xp7DHrY9lVNtrK08bMJd1Ofif71pSLOdNprtgXNlgqLOLi4wLfhQG-G9ewn4p6NEOaDRzcxDHYeEOt-s8DM_VfkeLZKKoseVgcJsLAmgEwwHB3mvvp_nqlRU50M1vRA";


    public JsonObject indexLabels(String storeId, String pattern){

        String url = apiUrl + "/search/v2/stores/" + storeId + "/labels";
        String body = "{\"query\":{\"bool\":{\"must_not\":[],\"must\":[{\"query_string\":{\"query\":\"0000*\",\"default_operator\":\"and\"}}]}},\"sort\":[{\"modificationDate\":{\"order\":\"asc\"}}],\"pageSize\":10,\"page\":1,\"statistics\":false,\"facets\":true}";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authorization);
        headers.set("Content-Type","application/json");
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN));

        JsonObject jsonObject = searchPattern(gson.fromJson(body, JsonObject.class), pattern);

        HttpEntity<String> entity = new HttpEntity<>(jsonObject.toString(), headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

        return gson.fromJson(response.getBody(), JsonObject.class);
    }


    public JsonObject searchPattern(JsonObject json, String pattern){
        json.getAsJsonObject("query").getAsJsonObject("bool").getAsJsonArray("must").get(0).getAsJsonObject().getAsJsonObject("query_string").addProperty("query", pattern);
        return json;
    }

//    public Map<String, List<SearchLabels>> getLabelsByRC(
//            final String rc, final Integer storesCount, final Integer labelsCount) {
//        final URI url = URI.create(apiUrl + "/search/v2/stores/" + rc + "/labels");
//
//        final Map<String, Object> placeHolders = Map.of(
//                "aggregations.aggs-store-id.terms.size", storesCount,
//                "aggregations.aggs-store-id.aggs.label-per-store.top_hits.size", labelsCount,
//                "query.bool.filter.term.retailChainId.value", (rc));
////        final String query = SearchQueries.readQuery(SearchQueries.RETRIEVE_LABELS_BY_RC, placeHolders);
//
////        final HttpEntity<String> request = new HttpEntity<>(query);
////        final ResponseEntity<JsonObject> response = searchRestTemplate.postForEntity(url, request, JsonObject.class);
//
//        return JsonUtils.getLabels(response.getBody());
//    }


}
