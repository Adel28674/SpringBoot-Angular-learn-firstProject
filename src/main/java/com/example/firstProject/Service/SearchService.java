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
              "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImtpZCI6IktRMnRBY3JFN2xCYVZWR0JtYzVGb2JnZEpvNCJ9.eyJhdWQiOiJmMWFhNTQ1Mi04YTA3LTQ4NmYtYjA3NC05Nzk2OGE1MGY2MjUiLCJpc3MiOiJodHRwczovL2xvZ2luLm1pY3Jvc29mdG9ubGluZS5jb20vMDI2ZjNmOTctZjQ2My00YzQxLWJhNzQtYmMxNTZjM2JlNDk0L3YyLjAiLCJpYXQiOjE3MjQ2NjAzNjUsIm5iZiI6MTcyNDY2MDM2NSwiZXhwIjoxNzI0NjY0MjY1LCJhaW8iOiJBWVFBZS84WEFBQUErbThsdXpMUWxnNlMxV1YxVDk2a2l0bTd6WWNUbnU2Z2ZZRVNMVWVWWW1nRmI0R3MwZUJHUVgvOU5PdzJqalVYMDdmMnlVYlMrd0F1WmNKaER6ZU95NEs4Qm8vTVRLUCsvdUJPTTZVTlN2bElhQk5XQkFNQUNPL24xeEV2akU5Y252YXBzM1hzeDFickhoSXN1WFEvaEV4N1ducFd1R28rNFlkdWZicWVpeFk9IiwiYXRfaGFzaCI6IjBfZlp2bzI3MHI1d1c3WE5IUGVoMWciLCJlbWFpbCI6ImFkZWwuaGFtbWljaGVAdnVzaW9uLmNvbSIsIm5hbWUiOiJIQU1NSUNIRSBBZGVsIiwibm9uY2UiOiIyMTQyYTFkMC01NGYyLTRmZjUtODY3MS01Y2MyNzU1NjczMWUiLCJvaWQiOiIxN2FlNDg5Zi0yZmM1LTRkZWItYTdmYi1jZGZmMGEzNDNkZDUiLCJwcmVmZXJyZWRfdXNlcm5hbWUiOiJhZGVsLmhhbW1pY2hlQHZ1c2lvbi5jb20iLCJyaCI6IjAuQVZ3QWx6OXZBbVAwUVV5NmRMd1ZiRHZrbEZKVXF2RUhpbTlJc0hTWGxvcFE5aVZjQU9NLiIsInN1YiI6IkVrUkl4cGQ4cmhMTTFrQ3ZacVlFdHlYNUUtMl9LZjZaVWF0akQtRmpjV2ciLCJ0aWQiOiIwMjZmM2Y5Ny1mNDYzLTRjNDEtYmE3NC1iYzE1NmMzYmU0OTQiLCJ1cG4iOiJhZGVsLmhhbW1pY2hlQHZ1c2lvbi5jb20iLCJ1dGkiOiJVcmViMDJWU3JVR2lSeDhIVDhmTkFBIiwidmVyIjoiMi4wIn0.lB9zy0nt0pqFP26irylX5FLy9yQNMSVuWufrYjVQludz7Ryq0wpOTnackSOmrMlvqEaNxj0gSahxNXPLOm4VUOZJ1ErJYjdS5gxsG3aNzETnw-Lqkk1GkEfttMcFstheUs80nZK63a9PbRu5at9nHalvt1wPZWcl9nwVvDPJj2brZQQxnonqanPqrBPOsDmwuyCpkdo9uMaRFmPL4-v16oPZlJuBfQBbUBQa0e5mw5WYS25lY2RXpQfGSxsKV_Y3J5cNv5Zoo54fkgypL46mkAyQ6wwyrdSucOr899p0j9fRlQEYs9F8LzeBnsmfqK_bF81owJXRArgTw0CT_2FrVw";

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
