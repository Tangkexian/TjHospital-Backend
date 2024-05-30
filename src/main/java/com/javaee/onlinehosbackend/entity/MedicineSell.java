package com.javaee.onlinehosbackend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "MEDICINE_SELL")
public class MedicineSell {
    @EmbeddedId
    private MedicineSellId id;

    @Column(name = "SELLING_PRICE", nullable = false, precision = 6, scale = 2)
    private BigDecimal sellingPrice;

}