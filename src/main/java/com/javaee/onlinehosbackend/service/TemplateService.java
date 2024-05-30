package com.javaee.onlinehosbackend.service;
import com.javaee.onlinehosbackend.entity.Template;
import com.javaee.onlinehosbackend.repository.TemplateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TemplateService {

    @Autowired
    private TemplateRepository templateRepository;

    public List<Template> getAllTemplates() {
        return templateRepository.findAll();
    }
}
