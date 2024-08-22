package com.example.firstProject.Controller;

import com.example.firstProject.Model.SearchLabels;
import com.example.firstProject.Service.SearchService;
import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/search")
@RequiredArgsConstructor
public class SearchController {

    @Autowired
    private final SearchService searchService;

    @PostMapping("/searchLabels/{storeId}")
    public ResponseEntity<JsonObject> searchLabels(@PathVariable String storeId, @RequestBody JsonObject body){
        return ResponseEntity.ok(searchService.indexLabels(storeId, body.get("pattern").getAsString()));
    }

//    @GetMapping("/{rc}/{storeNumber}/{labelsNumber}")
//    public ResponseEntity<Map<String, List<SearchLabels>>> getLabels(
//            @PathVariable String rc, @PathVariable int storeNumber, @PathVariable int labelsNumber) {
//
//        final Map<String, List<SearchLabels>> item = searchService.getLabelsByRC(rc, storeNumber, labelsNumber);
//        return ResponseEntity.ok(item);
//    }

}
