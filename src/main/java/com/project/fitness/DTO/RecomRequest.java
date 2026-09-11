package com.project.fitness.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecomRequest {


   private String userId;
   private String activityId;
   private String type;
   private String recommendation;
   private List<String> improvements;
   private List<String> suggestions;
   private List<String> safety;


}
