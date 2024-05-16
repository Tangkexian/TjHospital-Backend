package com.javaee.onlinehosbackend.controller;

import com.javaee.onlinehosbackend.dto.AdministratorResponse;
import com.javaee.onlinehosbackend.dto.TreatmentFeedbacksResponse;
import com.javaee.onlinehosbackend.entity.Administrator;
import com.javaee.onlinehosbackend.service.AdministratorService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/Administrators")
public class AdministratorController {
    @Autowired
    private AdministratorService administratorService;

    @GetMapping
    @CrossOrigin
    public ResponseEntity<?> getAdministrators() {
        List<AdministratorResponse> administratorResponseList = administratorService.getAllAdministrators().stream()
                .map(tf -> new AdministratorResponse(
                        tf.getAdministratorId(),
                        tf.getName(),
                        tf.getGender(),
                        tf.getContact(),
                        tf.getPassword()
                ))
                .collect(Collectors.toList());
        if (administratorResponseList.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(administratorResponseList);
    }

    @GetMapping("/id")
    @CrossOrigin
    public ResponseEntity<?> getAdministratorbyId(@RequestParam String id){
        Administrator administrator = administratorService.getAdministratorById(id);
        if (administrator == null) {
            return ResponseEntity.badRequest().body("Not found!");
        }
        AdministratorResponse administratorResponse = new AdministratorResponse(
                administrator.getAdministratorId(),
                administrator.getName(),
                administrator.getGender(),
                administrator.getContact(),
                administrator.getPassword()
        );
        return ResponseEntity.ok().body(administratorResponse);
    }

    @GetMapping("/name")
    @CrossOrigin
    public ResponseEntity<?> getAdministratorbyName(@RequestParam String name) {
        List<AdministratorResponse> administratorResponseList = administratorService.getAdministratorByName(name).stream()
                .map(tf -> new AdministratorResponse(
                        tf.getAdministratorId(),
                        tf.getName(),
                        tf.getGender(),
                        tf.getContact(),
                        tf.getPassword()
                ))
                .collect(Collectors.toList());
        if (administratorResponseList.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(administratorResponseList);
    }

    @PostMapping("/add")
    @CrossOrigin
    public ResponseEntity<?> addAdministrator(@RequestBody Administrator administrator) {
        if (administratorService.existsById(administrator.getAdministratorId())) {
            return ResponseEntity.badRequest().body("Administrator with ID " + administrator.getAdministratorId() + " already exists.");
        }
        Administrator newAdmin = administratorService.addAdministrator(administrator);
        return ResponseEntity.created(URI.create("/api/administrators/" + newAdmin.getAdministratorId())).body(newAdmin);
    }

    @PutMapping("/update")
    @CrossOrigin
    public ResponseEntity<?> updateAdministrator(@RequestBody Administrator administrator) {
        try {
            Administrator updatedAdmin = administratorService.updateAdministrator(administrator);
            return ResponseEntity.ok(updatedAdmin);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
