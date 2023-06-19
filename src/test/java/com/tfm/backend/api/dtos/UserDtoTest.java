package com.tfm.backend.api.dtos;

import com.tfm.backend.data.entities.User;
import com.tfm.backend.data.enums.Role;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserDtoTest {

    @Test
    public void testOfMobileFirstName() {
        User user = new User();
        user.setMobile("123456789");
        user.setFirstName("John");
        UserDto userDto = UserDto.ofMobileFirstName(user);
        assertEquals(user.getMobile(), userDto.getMobile());
        assertEquals(user.getFirstName(), userDto.getFirstName());
    }

    @Test
    public void testDoDefault() {
        UserDto userDto = new UserDto();
        userDto.doDefault();
        assertTrue(userDto.getPassword() != null && !userDto.getPassword().isEmpty());
        assertEquals(Role.CUSTOMER, userDto.getRole());
        assertTrue(userDto.getActive());
    }
}
