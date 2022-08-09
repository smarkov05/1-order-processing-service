package com.jms.processing.orderservice.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.PROPERTY;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;

@Data
@NoArgsConstructor
@JsonTypeInfo(use = NAME, include = PROPERTY)
@JsonSubTypes({
        @JsonSubTypes.Type(value= LiquidProductDTO.class, name = "LiquidProduct"),
        @JsonSubTypes.Type(value= CountableProductDTO.class, name = "CountableProduct")
})
public abstract sealed class Product permits LiquidProductDTO, CountableProductDTO {
    protected UUID uuid;
    protected String name;
    protected ProductType type;
    protected String measuringUnit;
}
