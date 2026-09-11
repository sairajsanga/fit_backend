package com.project.fitness.service;

import com.project.fitness.DTO.ActivityRequest;
import com.project.fitness.DTO.ActivityResponse;
import com.project.fitness.model.Activity;
import com.project.fitness.model.User;
import com.project.fitness.repository.ActivityRepository;
import com.project.fitness.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ActivityService {

    private final UserRepository userRepository;
    private  final ActivityRepository activityRepository;



    public ActivityResponse createTrack(ActivityRequest activityRequest){
        Activity activity= mapToEntity(activityRequest);
        try{
            activityRepository.save(activity);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        return mapToResponse(activity);
    }


    public Activity mapToEntity(ActivityRequest activityRequest){
       User user=userRepository.findById(activityRequest.getUserId())
                .orElseThrow(()->new RuntimeException("Invalid User"+activityRequest.getUserId()));

        Activity activity=Activity.builder()
                .type(activityRequest.getType())
                .user(user)
                .additionalMetrics(activityRequest.getAdditionalMetrics())
                .duration(activityRequest.getDuration())
                .caloriesBurned(activityRequest.getCaloriesBurned())
                .startTime(activityRequest.getStartTime())
                .build();
        return activity;
    }

    public ActivityResponse mapToResponse(Activity activity){
       ActivityResponse activityResponse=ActivityResponse.builder()
                .id(activity.getId())
                .userId(activity.getUser().getId())
                .type(activity.getType())
                .additionalMetrics(activity.getAdditionalMetrics())
                .duration(activity.getDuration())
                .caloriesBurned(activity.getCaloriesBurned())
                .startTime(activity.getStartTime())
                .createdAt(activity.getCreatedAt())
                .updatedAt(activity.getUpdatedAt())
                .build();
       return activityResponse;
    }

    public List<ActivityResponse> getActivities(String userId){
       List<Activity> list=activityRepository.findByUserId(userId);
       List<ActivityResponse> activityResponseList=new ArrayList<>();
       for(Activity activity:list){
           activityResponseList.add(mapToResponse(activity));
       }
       return activityResponseList;

    }
}
