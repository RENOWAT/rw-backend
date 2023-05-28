package com.tfm.backend.api.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tfm.backend.data.entities.Plan;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PlanDto {
    private int id;
    private String name;
    private Integer planNumber;

    public PlanDto(Plan plan) {
        BeanUtils.copyProperties(plan, this);
    }
}
