package com.javaee.onlinehosbackend.controller;

import com.javaee.onlinehosbackend.entity.Template;
import com.javaee.onlinehosbackend.service.TemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/Template")
public class TemplateController {

    @Autowired
    private TemplateService templateService;

    @GetMapping
    @CrossOrigin
    public ResponseEntity<?> getTemplates() {
        List<Template> templates = templateService.getAllTemplates();
        return ResponseEntity.ok(templates);
    }
}
