package com.project.fitness.controller;

import com.project.fitness.DTO.RecomRequest;
import com.project.fitness.DTO.RecomResponse;
import com.project.fitness.service.RecomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recommendation")
@RequiredArgsConstructor
public class RecomController {

    private final RecomService recomService;

    @PostMapping("/generate")
    public ResponseEntity<RecomResponse> generateRecommendations(@RequestBody RecomRequest recomRequest){
        return ResponseEntity.ok(recomService.generate(recomRequest));
    }

    @GetMapping("/get/{userId}")
    public ResponseEntity<List<?>> getUserRecommendations(@PathVariable String userId){
        return ResponseEntity.ok(recomService.getRecommendations(userId));
    }

    @GetMapping("/{activityId}")
    public ResponseEntity<List<RecomResponse>> getActivityRecommendations(@PathVariable String activityId){
        return ResponseEntity.ok(recomService.getActivityRecommendations(activityId));
    }



}
