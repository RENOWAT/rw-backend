package com.tfm.backend.services;

import com.tfm.backend.data.daos.PlanRepository;
import com.tfm.backend.data.entities.Plan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Stream;

@Service
public class PlanService {

    private final PlanRepository planRepository;

    @Autowired
    public PlanService(PlanRepository planRepository) {this.planRepository = planRepository;}

    public Stream<Plan> findAllPlan() {
        return this.planRepository.findAll().stream();
    }

}
