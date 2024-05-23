package com.javaee.onlinehosbackend.repository;

import com.javaee.onlinehosbackend.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, String> {
    // 可以添加一个自定义方法来验证管理员的凭据
    boolean existsByDoctorIdAndPassword(String doctorId, String password);

    boolean existsByContactAndDoctorId(String contact, String doctorId);
    boolean existsByDoctorId(String doctorId);

    List<Doctor> findBySecondaryDepartmentAndNameContaining(String department, String keyword);

    List<Doctor> findBySecondaryDepartment(String department);

    List<Doctor> findByName(String name);
}
