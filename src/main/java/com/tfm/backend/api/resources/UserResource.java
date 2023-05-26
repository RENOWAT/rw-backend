package com.tfm.backend.api.resources;

import com.tfm.backend.api.dtos.TokenDto;
import com.tfm.backend.api.dtos.UserDto;
import com.tfm.backend.services.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(UserResource.USERS)
public class UserResource {
    public static final String USERS = "/users";
    public static final String TOKEN = "/token";
    public static final String SEARCH = "/search";
    private final UserService userService;

    @Autowired
    public UserResource(UserService userService) {
        this.userService = userService;
    }

    @SecurityRequirement(name = "basicAuth")
    @PostMapping(value = TOKEN)
    public Optional<TokenDto> login(@AuthenticationPrincipal User activeUser) {
        return userService.login(activeUser.getUsername())
                .map(TokenDto::new);
    }

    @SecurityRequirement(name = "bearerAuth")
    @GetMapping(SEARCH)
    public UserDto findUser(
            @RequestHeader(value = "Authorization") String token) {
        return new UserDto(this.userService.findUser(token));
    }

}
