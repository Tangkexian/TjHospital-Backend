package com.javaee.onlinehosbackend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "CONSULTING_ROOM")
public class ConsultingRoom {
    @Id
    @Column(name = "CONSULTING_ROOM_NAME", nullable = false, length = 30)
    private String consultingRoomName;

    @Column(name = "CONSULTANT_CAPACITY")
    private Integer consultantCapacity;

}