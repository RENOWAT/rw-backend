package com.tfm.backend.services.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BadRequestExceptionIT {
    @Test
    public void testBadRequestException() {
        String detail = "Invalid input";
        String expectedMessage = "Bad Request Exception. Invalid input";

        try {
            throw new BadRequestException(detail);
        } catch (BadRequestException ex) {
            assertEquals(expectedMessage, ex.getMessage());
        }
    }
}
