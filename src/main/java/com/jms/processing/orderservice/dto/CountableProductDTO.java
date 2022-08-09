package com.jms.processing.orderservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public final class CountableProductDTO extends Product implements Serializable {

    @Override
    public String toString() {
        return "CountableProductDTO{" +
                "uuid=" + uuid +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", measuringUnit='" + measuringUnit + '\'' +
                '}';
    }
}
