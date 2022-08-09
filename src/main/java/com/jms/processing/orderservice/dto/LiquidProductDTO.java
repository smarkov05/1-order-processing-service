package com.jms.processing.orderservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public final class LiquidProductDTO extends Product implements Serializable {

    @Override
    public String toString() {
        return "LiquidProductDTO{" +
                "uuid=" + uuid +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", measuringUnit='" + measuringUnit + '\'' +
                '}';
    }
}
