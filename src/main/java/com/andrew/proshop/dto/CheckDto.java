package com.andrew.proshop.dto;

import com.andrew.proshop.model.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CheckDto {

    private Long id;

    private List<Product> products = new ArrayList<>();

    private BigDecimal checkSum;

    private int productCount;
}
