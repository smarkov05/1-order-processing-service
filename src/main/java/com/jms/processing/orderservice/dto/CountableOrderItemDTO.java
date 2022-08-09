package com.jms.processing.orderservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public final class CountableOrderItemDTO extends OrderItem implements Serializable {
    private long amount;

    @Override
    public Number quantityOrderedProduct() {
        return amount;
    }

    @Override
    public String toString() {
        return "CountableOrderItemDTO{" +
                "amount=" + amount +
                ", product=" + product +
                '}';
    }
}
