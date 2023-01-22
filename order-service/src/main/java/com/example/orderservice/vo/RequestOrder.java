package com.example.orderservice.vo;

import lombok.Data;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.Serializable;

@Data
public class RequestOrder implements Serializable {
    private String productId;
    private Integer qty;
    private Integer unitPrice;
}
