package com.javaee.onlinehosbackend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "MEDICINE_STOCK", indexes = {
        @Index(name = "CLEAN_ADMINISTRATOR", columnList = "CLEAN_ADMINISTRATOR")
})
public class MedicineStock {
    @EmbeddedId
    private MedicineStockId id;

    @Column(name = "MEDICINE_SHELFLIFE", nullable = false)
    private Integer medicineShelflife;

    @Column(name = "MEDICINE_AMOUNT")
    private Integer medicineAmount;

    @Column(name = "THRESHOLD_VALUE", nullable = false)
    private Integer thresholdValue;

    @Column(name = "CLEAN_DATE")
    private LocalDate cleanDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CLEAN_ADMINISTRATOR")
    private Administrator cleanAdministrator;

}