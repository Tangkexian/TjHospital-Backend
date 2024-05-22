package com.javaee.onlinehosbackend.service;

import com.javaee.onlinehosbackend.entity.Department2;
import com.javaee.onlinehosbackend.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {
    @Autowired
    private DepartmentRepository departmentRepository;

    public List<Department2> getAllDepartments() {
        return departmentRepository.findAll();
    }
}
