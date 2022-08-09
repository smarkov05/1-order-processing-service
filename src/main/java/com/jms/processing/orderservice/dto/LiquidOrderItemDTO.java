package com.jms.processing.orderservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public final class LiquidOrderItemDTO extends OrderItem implements Serializable {
    private double productVolume;

    @Override
    public Number quantityOrderedProduct() {
        return productVolume;
    }

    @Override
    public String toString() {
        return "LiquidOrderItemDTO{" +
                "productVolume=" + productVolume +
                ", product=" + product +
                '}';
    }
}
