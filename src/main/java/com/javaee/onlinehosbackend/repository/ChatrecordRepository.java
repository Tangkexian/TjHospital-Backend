package com.javaee.onlinehosbackend.repository;

import com.javaee.onlinehosbackend.entity.Chatrecord;
import com.javaee.onlinehosbackend.entity.ChatrecordId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ChatrecordRepository extends JpaRepository<Chatrecord, ChatrecordId> {

    @Query("SELECT c FROM Chatrecord c WHERE c.recordid = :recordId ORDER BY c.id.timestamp ASC")
    List<Chatrecord> findByRecordidOrderByTimestampAsc(String recordId);
}
