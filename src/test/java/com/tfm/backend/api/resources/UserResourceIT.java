package com.tfm.backend.api.resources;

import com.tfm.backend.api.dtos.UserDto;
import com.tfm.backend.services.RestClientTestService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.reactive.server.WebTestClient;

import static com.tfm.backend.api.resources.UserResource.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ApiTestConfig
public class UserResourceIT {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private RestClientTestService restClientTestService;

    @Test
    void testLogin() {
        this.restClientTestService.loginAdmin(this.webTestClient);
        assertTrue(this.restClientTestService.getToken().length() > 10);
    }

    @Test
    void testFindUser() {
        this.restClientTestService.loginOperator(this.webTestClient)
                .get()
                .uri(USERS + SEARCH)
                .exchange().expectStatus().isOk()
                .expectBodyList(UserDto.class)
                .value(users -> assertTrue(users.stream().anyMatch(user -> "ope".equals(user.getFirstName()))));
    }
}
