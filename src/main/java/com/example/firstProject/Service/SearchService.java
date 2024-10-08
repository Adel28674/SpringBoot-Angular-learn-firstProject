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
      "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImtpZCI6IktRMnRBY3JFN2xCYVZWR0JtYzVGb2JnZEpvNCJ9.eyJhdWQiOiJmMWFhNTQ1Mi04YTA3LTQ4NmYtYjA3NC05Nzk2OGE1MGY2MjUiLCJpc3MiOiJodHRwczovL2xvZ2luLm1pY3Jvc29mdG9ubGluZS5jb20vMDI2ZjNmOTctZjQ2My00YzQxLWJhNzQtYmMxNTZjM2JlNDk0L3YyLjAiLCJpYXQiOjE3MjQ3NDcyMjcsIm5iZiI6MTcyNDc0NzIyNywiZXhwIjoxNzI0NzUxMTI3LCJhaW8iOiJBWVFBZS84WEFBQUFLZXlGRUZ5MkxzVWR6NFhyYkFVTDNqa1hISHFlV0o3SHBtSDlTUnVNYzJ6RGVDWmpWWEF4Wkg0TWR4N0NKT1ZseThvb0N3RmJIZHZkU2hBOXJBNUtJaGhzWFk3WkMxNnZjYlJRWHdScXZRSURxa0liZnVjaHJuSXYvczF5N096SDNNUExOaHV5WUZiWnovTFdBU2lIQ2l4YUJJanI0RnUxOG9DSzNwb0xEMUk9IiwiYXRfaGFzaCI6Il83OXdjQWt0Vk9CNHlGRGdPRzRsNnciLCJlbWFpbCI6ImFkZWwuaGFtbWljaGVAdnVzaW9uLmNvbSIsIm5hbWUiOiJIQU1NSUNIRSBBZGVsIiwibm9uY2UiOiI2ZGFhNDFhYS0yY2FkLTRmMDEtYjNmOC0yYjhjZTUyODQxM2EiLCJvaWQiOiIxN2FlNDg5Zi0yZmM1LTRkZWItYTdmYi1jZGZmMGEzNDNkZDUiLCJwcmVmZXJyZWRfdXNlcm5hbWUiOiJhZGVsLmhhbW1pY2hlQHZ1c2lvbi5jb20iLCJyaCI6IjAuQVZ3QWx6OXZBbVAwUVV5NmRMd1ZiRHZrbEZKVXF2RUhpbTlJc0hTWGxvcFE5aVZjQU9NLiIsInN1YiI6IkVrUkl4cGQ4cmhMTTFrQ3ZacVlFdHlYNUUtMl9LZjZaVWF0akQtRmpjV2ciLCJ0aWQiOiIwMjZmM2Y5Ny1mNDYzLTRjNDEtYmE3NC1iYzE1NmMzYmU0OTQiLCJ1cG4iOiJhZGVsLmhhbW1pY2hlQHZ1c2lvbi5jb20iLCJ1dGkiOiJiVWU5Z01EYUNraTl1OGJ5QjcwTkFBIiwidmVyIjoiMi4wIn0.VlhrbjWXVQIeDNIDgBs8hOrN8g1qBR0neFe41zi6jj3SURI4T-lcJiBoNq7LXf_K_rO22HEEiMdvJo7Z6dS0JGbfoDRHHQeSQIjbfTGP9FtDf1qdCUQX0BLFAgZNneoNpLQnUASygzK71XBqIarBm96x6vi9j5QaoC3dFUnyRGaSkUuzuKCKqzY8xkcC_9aUfAmaHHyDQn-4_8KAkXcADVz9Dqo99dq58Sk-x_3ax5_5TJ2feX7mjtqaNOBlz75zT-s-4RDrf6rEkN1O2ezRMCtX3e-wWVUdAkYj71-kNDoaskAybkLmcBEJwAPwztSram_TMNvx_Pv4MUELFyPheA";
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
