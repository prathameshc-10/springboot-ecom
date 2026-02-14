package com.example.springEcomDemo.model.dto;

public record OrderItemRequest(
        int productId,
        int quantity
) {}
