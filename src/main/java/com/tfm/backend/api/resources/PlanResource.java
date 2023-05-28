package com.tfm.backend.api.resources;

import com.tfm.backend.api.dtos.PlanDto;
import com.tfm.backend.services.PlanService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Stream;

@RestController
@RequestMapping(PlanResource.PLAN)
public class PlanResource {
    public static final String PLAN = "/plan";
    private final PlanService planService;

    @Autowired
    public PlanResource(PlanService planService) {
        this.planService = planService;
    }

    @SecurityRequirement(name = "bearerAuth")
    @GetMapping
    public Stream<PlanDto> findAllPlan() {
        return this.planService.findAllPlan()
                .map(PlanDto::new);
    }
}
