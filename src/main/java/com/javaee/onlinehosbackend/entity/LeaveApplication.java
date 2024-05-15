package com.javaee.onlinehosbackend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "LEAVE_APPLICATION")
public class LeaveApplication {
    @Id
    @Column(name = "LEAVE_NOTE_ID", nullable = false, length = 30)
    private String leaveNoteId;

    @Column(name = "LEAVE_APPLICATION_TIME", nullable = false)
    private Instant leaveApplicationTime;

    @Column(name = "LEAVE_START_DATE", nullable = false)
    private Instant leaveStartDate;

    @Column(name = "LEAVE_END_DATE", nullable = false)
    private Instant leaveEndDate;

    @Column(name = "LEAVE_NOTE_REMARK", length = 10)
    private String leaveNoteRemark;

}