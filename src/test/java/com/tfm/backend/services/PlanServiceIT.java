package com.tfm.backend.services;

import com.tfm.backend.TestConfig;
import com.tfm.backend.data.daos.InvoiceRepository;
import com.tfm.backend.data.daos.PlanRepository;
import com.tfm.backend.data.entities.Plan;
import com.tfm.backend.data.entities.Product;
import com.tfm.backend.data.entities.Tariff;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@TestConfig
public class PlanServiceIT {

    @Mock
    private PlanRepository planRepository;

    @Test
    public void testFindAllPlan() {
        Plan plan1 = Plan.builder().name("plan estable").planNumber(1)
                .p1(BigDecimal.valueOf(0.1384)).p2(BigDecimal.valueOf(0.1384)).p3(BigDecimal.valueOf(0.1384))
                .tf(BigDecimal.valueOf(3.45)).ptf(BigDecimal.valueOf(2.63268)).product(Product.builder()
                        .type("electricidad").tariff(Tariff.builder().name("BT 2.0 TD").build())
                        .description("electricidad origen convencional")
                        .build())
                .build();
        List<Plan> planList = Arrays.asList(plan1);
        PlanRepository planRepository = Mockito.mock(PlanRepository.class);
        when(planRepository.findAll()).thenReturn(planList);
        PlanService planService = new PlanService(planRepository);
        Stream<Plan> result = planService.findAllPlan();

        List<Plan> resultList = result.collect(Collectors.toList());
        assertEquals(planList.size(), resultList.size());
        assertEquals(plan1.getId(), resultList.get(0).getId());
        assertEquals(plan1.getName(), resultList.get(0).getName());
    }
}
