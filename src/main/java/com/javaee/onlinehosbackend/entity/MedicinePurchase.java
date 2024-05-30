package com.javaee.onlinehosbackend.entity;

import jakarta.persistence.*;
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
@Table(name = "MEDICINE_PURCHASE", indexes = {
        @Index(name = "ADMINISTRATOR_ID", columnList = "ADMINISTRATOR_ID")
})
public class MedicinePurchase {
    @EmbeddedId
    private MedicinePurchaseId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ADMINISTRATOR_ID")
    private Administrator administrator;

    @Column(name = "PURCHASE_AMOUNT", nullable = false, precision = 10, scale = 2)
    private BigDecimal purchaseAmount;

    @Column(name = "PURCHASE_PRICE", nullable = false, precision = 6, scale = 2)
    private BigDecimal purchasePrice;

}