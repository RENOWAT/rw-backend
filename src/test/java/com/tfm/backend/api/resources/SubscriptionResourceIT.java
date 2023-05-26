package com.tfm.backend.api.resources;

import com.tfm.backend.api.dtos.SubscriptionDto;
import com.tfm.backend.services.RestClientTestService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.reactive.server.WebTestClient;
import static com.tfm.backend.api.resources.SubscriptionResource.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ApiTestConfig
public class SubscriptionResourceIT {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private RestClientTestService restClientTestService;

    @Test
    void testFindSubscriptionsByCustomer() {
        this.restClientTestService.loginCustomer(this.webTestClient)
                .get()
                .uri(SUBSCRIPTION)
                .exchange().expectStatus().isOk()
                .expectBodyList(SubscriptionDto.class)
                .value(subscriptions -> assertTrue(subscriptions.stream()
                        .anyMatch(subscription -> "ES0021000005407136XK".equals(subscription.getCups()))));

    }
}
