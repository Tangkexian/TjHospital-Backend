package com.javaee.onlinehosbackend.repository;

import com.javaee.onlinehosbackend.entity.TreatmentFeedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TreatmentFeedbackRepository extends JpaRepository<TreatmentFeedback, String> {
    // 如果需要其他自定义查询，可以在这里添加
}
