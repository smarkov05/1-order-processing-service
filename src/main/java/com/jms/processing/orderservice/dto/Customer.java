package com.jms.processing.orderservice.dto;

import java.util.UUID;

public record Customer (
        UUID uuid,
        String fullName) {
}
