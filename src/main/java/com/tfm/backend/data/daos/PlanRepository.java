package com.tfm.backend.data.daos;

import com.tfm.backend.data.entities.Plan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlanRepository extends JpaRepository<Plan, Integer > {
    Optional<Plan> findByPlanNumber(Integer planNumber);
    Optional<Plan> findByName(String name);

}
