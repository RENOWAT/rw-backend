package com.tfm.backend.services.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ConflictExceptionIT {
    @Test
    public void testConflictException() {
        String detail = "Data conflict";
        String expectedMessage = "Conflict Exception. Data conflict";

        try {
            throw new ConflictException(detail);
        } catch (ConflictException ex) {
            assertEquals(expectedMessage, ex.getMessage());
        }
    }
}
