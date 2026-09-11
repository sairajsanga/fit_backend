package com.project.fitness.service;

import com.project.fitness.DTO.RecomRequest;
import com.project.fitness.DTO.RecomResponse;
import com.project.fitness.model.Activity;
import com.project.fitness.model.Recommendation;
import com.project.fitness.model.User;
import com.project.fitness.repository.ActivityRepository;
import com.project.fitness.repository.RecomRepository;
import com.project.fitness.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RecomService {

    private final RecomRepository recomRepository;
    private  final UserRepository userRepository;
    private  final ActivityRepository activityRepository;

    public RecomResponse generate(RecomRequest recomRequest){
       Recommendation recommendation= mapToEntity(recomRequest);
       recomRepository.save(recommendation);
       return maptoResponse(recommendation);
    }

    public Recommendation mapToEntity(RecomRequest recomRequest){
        User user=userRepository.findById(recomRequest.getUserId()).orElseThrow(()->new RuntimeException("invalid user"+recomRequest.getUserId()));
        Activity activity=activityRepository.findById(recomRequest.getActivityId()).orElseThrow(()->new RuntimeException("Invalid activityId"+recomRequest.getActivityId()));
        Recommendation recommendation=Recommendation.builder()
                .type(recomRequest.getType())
                .user(user)
                .activity(activity)
                .recommendation(recomRequest.getRecommendation())
                .improvements(recomRequest.getImprovements())
                .suggestions(recomRequest.getSuggestions())
                .safety(recomRequest.getSafety())
                .build();
        return recommendation;
    }

    public RecomResponse maptoResponse(Recommendation recommendation){

        RecomResponse recomResponse=RecomResponse.builder()
                .id(recommendation.getId())
                .activityId(recommendation.getActivity().getId())
                .userId(recommendation.getUser().getId())
                .type(recommendation.getType())
                .recommendation(recommendation.getRecommendation())
                .improvements(recommendation.getImprovements())
                .suggestions(recommendation.getSuggestions())
                .safety(recommendation.getSafety())
                .createdAt(recommendation.getCreatedAt())
                .updatedAt(recommendation.getUpdatedAt())
                .build();
        return recomResponse;
    }

    public List<RecomResponse> getRecommendations(String userId) {
        List<Recommendation>list=recomRepository.findByUserId(userId);


        List<RecomResponse> recomResponsesList=new ArrayList<>();
        for(Recommendation recommendation:list){
            recomResponsesList.add(maptoResponse(recommendation));
        }
        return recomResponsesList;
    }

    public List<RecomResponse> getActivityRecommendations(String activityId) {
        List<Recommendation>list=recomRepository.findByActivityId(activityId);
        List<RecomResponse> activityRecomResponsesList=new ArrayList<>();
        for(Recommendation recommendation:list){
            activityRecomResponsesList.add(maptoResponse(recommendation));
        }
        return activityRecomResponsesList;

    }
}
