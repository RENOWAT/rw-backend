package com.tfm.backend.api.resources;

import com.tfm.backend.api.dtos.PlanDto;
import com.tfm.backend.services.RestClientTestService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.reactive.server.WebTestClient;

import static com.tfm.backend.api.resources.PlanResource.*;;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ApiTestConfig
public class PlanResourceIT {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private RestClientTestService restClientTestService;

    @Test
    void testFindSubscriptionsByCustomer() {
        this.restClientTestService.loginCustomer(this.webTestClient)
                .get()
                .uri(PLAN)
                .exchange().expectStatus().isOk()
                .expectBodyList(PlanDto.class)
                .value(subscriptions -> assertTrue(subscriptions.stream()
                        .anyMatch(plan -> "plan estable".equals(plan.getName()))));

    }
}
