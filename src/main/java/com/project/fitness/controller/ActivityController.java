package com.project.fitness.controller;

import com.project.fitness.DTO.ActivityRequest;
import com.project.fitness.DTO.ActivityResponse;
import com.project.fitness.service.ActivityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/activities")
public class ActivityController {


       private final ActivityService activityService;


       @PostMapping
       public ResponseEntity<ActivityResponse> TrackActivity(@RequestBody ActivityRequest activityRequest){
           return ResponseEntity.ok(activityService.createTrack(activityRequest));
       }

    @GetMapping
    public ResponseEntity<List<ActivityResponse>> GetUserActivities(@RequestHeader(value = "X-User-ID") String userId){
        return ResponseEntity.ok(activityService.getActivities(userId));
    }


}
