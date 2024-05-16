package com.javaee.onlinehosbackend.controller;

import com.javaee.onlinehosbackend.dto.PatientResponse;
import com.javaee.onlinehosbackend.dto.ResetPasswordInputRequest;
import com.javaee.onlinehosbackend.entity.Patient;
import com.javaee.onlinehosbackend.service.AdministratorService;
import com.javaee.onlinehosbackend.service.DoctorService;
import com.javaee.onlinehosbackend.service.PatientService;
import com.javaee.onlinehosbackend.utils.TokenGen;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/Login")
public class LoginController {

    @Autowired
    private PatientService patientService;

    @Autowired
    private AdministratorService administratorService;

    @Autowired
    private DoctorService doctorService;

    private TokenGen tokenGen = new TokenGen();

    private static final long TOKEN_EXPIRATION_TIME = 3 * 24 * 60 * 60 * 1000; // 3 days in milliseconds

    @GetMapping
    @CrossOrigin
    public ResponseEntity<?> login(@RequestParam String userId) {
        Patient patient = patientService.getPatientById(userId);
        if (patient != null) {
            // 创建一个响应对象，返回所需信息
            return ResponseEntity.ok().body(new PatientResponse(patient.getPatientId(), patient.getName()));
        } else {
            // 如果找不到病人信息，返回错误响应
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/AdminLogin")
    @CrossOrigin
    public ResponseEntity<?> checkAdminCredentials(@RequestParam String ID, @RequestParam String password) {
        boolean isValid = administratorService.validateAdminCredentials(ID, password);
        if (isValid) {
            String token = tokenGen.generateToken(ID);
            return ResponseEntity.ok("The token is: " + token);
        } else {
            return ResponseEntity.badRequest().body("Wrong account or password");
        }
    }

    @GetMapping("/PatientLogin")
    @CrossOrigin
    public ResponseEntity<?> checkPatientCredentials(@RequestParam String ID, @RequestParam String password) {
        boolean isValid = patientService.validatePatientCredentials(ID, password);
        if (isValid) {
            String token = tokenGen.generateToken(ID);
            return ResponseEntity.ok("The token is: " + token);
        } else {
            return ResponseEntity.badRequest().body("Wrong account or password");
        }
    }

    @GetMapping("/DoctorLogin")
    @CrossOrigin
    public ResponseEntity<?> checkDoctorCredentials(@RequestParam String ID, @RequestParam String password) {
        boolean isValid = doctorService.validateDoctorCredentials(ID, password);
        if (isValid) {
            String token = tokenGen.generateToken(ID);
            return ResponseEntity.ok("The token is: " + token);
        } else {
            return ResponseEntity.badRequest().body("Wrong account or password");
        }
    }

    @GetMapping("/verifyToken")
    @CrossOrigin
    public ResponseEntity<?> verifyToken(@RequestParam String User, @RequestParam String token) {
        boolean isValid = tokenGen.verifyToken(User, token, TOKEN_EXPIRATION_TIME);
        if (isValid) {
            return ResponseEntity.ok().body("Token verified successfully.");
        } else {
            return ResponseEntity.badRequest().body("Invalid or expired token.");
        }
    }

    @PutMapping("/resetAdminPassword")
    @CrossOrigin
    public ResponseEntity<?> resetAdminPassword(@RequestBody ResetPasswordInputRequest resetPasswordInputRequest){
        boolean isReset = administratorService.resetAdminPassword(resetPasswordInputRequest.getId(), resetPasswordInputRequest.getNewPassword());
        if (isReset) {
            return ResponseEntity.ok("Administrator Password reset successfully!");
        } else {
            return ResponseEntity.badRequest().body("Administrator ID not found");
        }
    }

    @PutMapping("/resetPatientPassword")
    @CrossOrigin
    public ResponseEntity<?> resetPatientPassword(@RequestBody ResetPasswordInputRequest resetPasswordInputRequest){
        boolean isReset = patientService.resetPatientPassword(resetPasswordInputRequest.getId(), resetPasswordInputRequest.getNewPassword());
        if (isReset) {
            return ResponseEntity.ok("Patient Password reset successfully!");
        } else {
            return ResponseEntity.badRequest().body("Patient ID not found");
        }
    }

    @PutMapping("/resetDoctorPassword")
    @CrossOrigin
    public ResponseEntity<?> resetDoctorPassword(@RequestBody ResetPasswordInputRequest resetPasswordInputRequest){
        boolean isReset = doctorService.resetDoctorPassword(resetPasswordInputRequest.getId(), resetPasswordInputRequest.getNewPassword());
        if (isReset) {
            return ResponseEntity.ok("Doctor Password reset successfully!");
        } else {
            return ResponseEntity.badRequest().body("Doctor ID not found");
        }
    }

    @GetMapping("/judgeAdminPhoneID")
    @CrossOrigin
    public ResponseEntity<?> judgeAdminPhoneID(@RequestParam String phoneNumber, @RequestParam String ID) {
        boolean isValid = administratorService.validateAdminPhoneID(phoneNumber, ID);
        if (isValid) {
            return ResponseEntity.ok("Qualified Administrator Found.");
        } else {
            return ResponseEntity.badRequest().body("Not found");
        }
    }

    @GetMapping("/judgePatientPhoneID")
    @CrossOrigin
    public ResponseEntity<?> judgePatientPhoneID(@RequestParam String phoneNumber, @RequestParam String ID) {
        boolean isValid = patientService.validatePatientPhoneID(phoneNumber, ID);
        if (isValid) {
            return ResponseEntity.ok("Qualified Patient Found.");
        } else {
            return ResponseEntity.badRequest().body("Not found");
        }
    }

    @GetMapping("/judgeDoctorPhoneID")
    @CrossOrigin
    public ResponseEntity<?> judgeDoctorPhoneID(@RequestParam String phoneNumber, @RequestParam String ID) {
        boolean isValid = doctorService.validateDoctorPhoneID(phoneNumber, ID);
        if (isValid) {
            return ResponseEntity.ok("Qualified Doctor Found.");
        } else {
            return ResponseEntity.badRequest().body("Not found");
        }
    }
}
