package com.jms.processing.orderservice.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.PROPERTY;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;

@Getter
@Data
@NoArgsConstructor
@JsonTypeInfo(use = NAME, include = PROPERTY)
@JsonSubTypes({
        @JsonSubTypes.Type(value = LiquidOrderItemDTO.class, name = "LiquidOrderItem"),
        @JsonSubTypes.Type(value = CountableProductDTO.class, name = "CountableProduct")
})
public abstract sealed class OrderItem permits LiquidOrderItemDTO, CountableOrderItemDTO {
    protected Product product;

    public abstract Number quantityOrderedProduct();
}
