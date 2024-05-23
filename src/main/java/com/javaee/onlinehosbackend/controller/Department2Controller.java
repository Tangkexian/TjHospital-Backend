package com.javaee.onlinehosbackend.controller;

import com.javaee.onlinehosbackend.entity.Department2;
import com.javaee.onlinehosbackend.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/Department2")
public class Department2Controller {
    @Autowired
    private DepartmentService departmentService;


    @GetMapping
    @CrossOrigin
    public ResponseEntity<?> getDepartments() {
        List<Department2> departments = departmentService.getAllDepartments();
        return ResponseEntity.ok(departments);
    }
}
