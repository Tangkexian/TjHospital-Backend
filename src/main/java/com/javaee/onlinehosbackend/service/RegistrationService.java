package com.javaee.onlinehosbackend.service;

import com.javaee.onlinehosbackend.dto.ChangeAppointmentRequest;
import com.javaee.onlinehosbackend.dto.Registration2Request;
import com.javaee.onlinehosbackend.dto.RegistrationInfoResponse;
import com.javaee.onlinehosbackend.dto.RegistrationRequest;
import com.javaee.onlinehosbackend.entity.Registration;
import com.javaee.onlinehosbackend.repository.DoctorRepository;
import com.javaee.onlinehosbackend.repository.PatientRepository;
import com.javaee.onlinehosbackend.repository.RegistrationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RegistrationService {

    @Autowired
    private RegistrationRepository registrationRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PatientRepository patientRepository;

    public List<Registration> getAllRegistrations() {
        return registrationRepository.findAll();
    }

    public List<Registration> getRegistrationsByDoctorIdAndDate(String doctorId, LocalDate date) {
        ZoneId utcZone = ZoneId.of("UTC");
        Instant startOfDayUtc = date.minusDays(0).atTime(0, 0).atZone(utcZone).toInstant();
        Instant endOfDayUtc = date.atTime(23, 59, 59).atZone(utcZone).toInstant();

        return registrationRepository.findByDoctorDoctorIdAndAppointmentTimeBetween(doctorId, startOfDayUtc, endOfDayUtc);
    }

    public List<Registration> getRegistrationsByDateAndPeriod(LocalDate date, Integer period) {
        ZoneId utcZone = ZoneId.of("UTC");
        Instant startOfDayUtc = date.minusDays(0).atTime(0, 0).atZone(utcZone).toInstant();
        Instant endOfDayUtc = date.atTime(23, 59, 59).atZone(utcZone).toInstant();
        return registrationRepository.findByAppointmentTimeBetweenAndPeriod(startOfDayUtc, endOfDayUtc, period);
    }

    public List<Registration> getTodayRegistrationsByDoctorId(String doctorId, Integer checkin) {
        LocalDate today = LocalDate.now();
        ZoneId utcZone = ZoneId.of("UTC");
        Instant startOfDayUtc = today.minusDays(0).atTime(0, 0).atZone(utcZone).toInstant();
        Instant endOfDayUtc = today.atTime(23, 59, 59).atZone(utcZone).toInstant();
        return registrationRepository.findByDoctorDoctorIdAndAppointmentTimeBetweenAndCheckin(doctorId, startOfDayUtc, endOfDayUtc, checkin);
    }

    public List<Registration> getRegistrationsByDoctorId(String doctorId) {
        return registrationRepository.findByDoctorDoctorId(doctorId);
    }

    public List<Registration> getCompletedRegistrationsByDoctorId(String doctorId) {
        return registrationRepository.findByDoctorDoctorIdAndState(doctorId, 1);
    }

    public List<Registration> getRegistrationsByPatientId(String patientId) {
        return registrationRepository.findByPatientPatientId(patientId);
    }

    public Registration createRegistration(RegistrationRequest registrationRequest) {
        if (registrationRequest.getPeriod() < 1 || registrationRequest.getPeriod() > 7) {
            throw new IllegalArgumentException("Invalid period");
        }
        ZoneId utcZone = ZoneId.of("UTC");
        Instant appointmentTimeInstant = registrationRequest.getTime().atZone(utcZone).toInstant();

        int sameRecordNum = registrationRepository.countByDoctorIdAndPatientIdAndAppointmentTimeAndPeriod(
                registrationRequest.getDoctorId(), registrationRequest.getPatientId(), appointmentTimeInstant, registrationRequest.getPeriod());

        if (sameRecordNum > 0) {
            throw new IllegalStateException("您已经挂号，无需重复挂号");
        }

        Integer maxOrder = registrationRepository.findMaxRegistorder(
                registrationRequest.getDoctorId(), appointmentTimeInstant, registrationRequest.getPeriod());

        // 检查是否返回了null，并处理
        int nextOrder = (maxOrder != null) ? maxOrder + 1 : 1;
        Registration registration = new Registration();
        registration.setPatient(patientRepository.findById(registrationRequest.getPatientId()).orElse(null));
        registration.setDoctor(doctorRepository.findById(registrationRequest.getDoctorId()).orElse(null));
        registration.setAppointmentTime(appointmentTimeInstant);
        registration.setPeriod(registrationRequest.getPeriod());
        registration.setQrcodeurl(registrationRequest.getQrCodeUrl());
        registration.setOrdertime(Instant.now());
        registration.setCheckin(0);
        registration.setState(0); // 假设0表示新挂号
        registration.setRegistorder(nextOrder);

        return registrationRepository.save(registration);
    }

    public Registration cancelRegistration(Registration2Request registration2Request) {

        // 将时间字符串转换为 LocalDate
        LocalDate appointmentDate = registration2Request.getTime().toLocalDate();

        // 在数据库查询中使用日期而非时间
        Registration registration = registrationRepository.findByPatientIdAndDoctorIdAndAppointmentDateAndPeriodAndState(
                        registration2Request.getPatientId(), registration2Request.getDoctorId(), appointmentDate, registration2Request.getPeriod(), 0)
                .orElseThrow(() -> new IllegalArgumentException("No registration found."));

        int similarRegistrationsCount = registrationRepository.countByPatientIdAndDoctorIdAndAppointmentDateAndPeriod(
                registration2Request.getPatientId(), registration2Request.getDoctorId(), appointmentDate, registration2Request.getPeriod());

        registration.setState(-similarRegistrationsCount);
        return registrationRepository.save(registration);
    }

    public Registration completeRegistration(RegistrationRequest registrationRequest) {
        Instant appointmentTimeInstant = registrationRequest.getTime().atZone(ZoneId.systemDefault()).toInstant();
        Registration registration = registrationRepository.findByPatientPatientIdAndDoctorDoctorIdAndAppointmentTimeAndPeriod(
                        registrationRequest.getPatientId(), registrationRequest.getDoctorId(), appointmentTimeInstant, registrationRequest.getPeriod())
                .orElseThrow(() -> new IllegalArgumentException("No registration found."));

        registration.setState(1); // Assuming 1 is the state for completed
        return registrationRepository.save(registration);
    }

    public Registration changeAppointmentTime(ChangeAppointmentRequest changeAppointmentRequest) {
        Registration oldRegistration = registrationRepository.findByPatientPatientIdAndDoctorDoctorIdAndAppointmentTimeAndPeriod(
                        changeAppointmentRequest.getOld().getPatientId(),
                        changeAppointmentRequest.getOld().getDoctorId(),
                        changeAppointmentRequest.getOld().getTime().atZone(ZoneId.systemDefault()).toInstant(),
                        changeAppointmentRequest.getOld().getPeriod())
                .orElseThrow(() -> new IllegalArgumentException("No changable registration found."));

        oldRegistration.setState(-1);

        Registration newRegistration = new Registration();
        newRegistration.setPatient(patientRepository.findById(changeAppointmentRequest.getOld().getPatientId()).orElse(null));
        newRegistration.setDoctor(doctorRepository.findById(changeAppointmentRequest.getNewRegistRequest().getDoctorId()).orElse(null));
        newRegistration.setAppointmentTime(changeAppointmentRequest.getNewRegistRequest().getTime().atZone(ZoneId.systemDefault()).toInstant());
        newRegistration.setPeriod(changeAppointmentRequest.getNewRegistRequest().getPeriod());
        newRegistration.setState(0); // Assuming 0 is the state for a new registration
        newRegistration.setOrdertime(Instant.now());

        // Calculate the new registration order
        int maxOrder = registrationRepository.findMaxRegistorder(
                changeAppointmentRequest.getNewRegistRequest().getDoctorId(),
                changeAppointmentRequest.getNewRegistRequest().getTime().atZone(ZoneId.systemDefault()).toInstant(),
                changeAppointmentRequest.getNewRegistRequest().getPeriod());
        newRegistration.setRegistorder(maxOrder + 1);

        registrationRepository.save(oldRegistration);
        return registrationRepository.save(newRegistration);
    }

    public Registration updateCheckin(Registration2Request registration2Request) {
        LocalDate appointmentDate = registration2Request.getTime()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();

        Registration registration = registrationRepository.findByPatientIdAndDoctorIdAndAppointmentDateAndPeriodAndState(
                        registration2Request.getPatientId(),
                        registration2Request.getDoctorId(),
                        appointmentDate,
                        registration2Request.getPeriod(),
                        0) // Assuming 0 is the state for 'not checked in'
                .orElseThrow(() -> new IllegalArgumentException("No registration found."));

        if (registration.getCheckin() == 1) {
            throw new IllegalStateException("已经报到，无需重复报到");
        }

        registration.setCheckin(1); // Set checkin status to 'checked in'
        return registrationRepository.save(registration);
    }

    public List<RegistrationInfoResponse> getTodaysRegistrationsForPatient(String patientId) {
        LocalDate today = LocalDate.now();
        ZoneId utcZone = ZoneId.of("UTC");
        Instant startOfDay = today.minusDays(0).atTime(0, 0).atZone(utcZone).toInstant();
        Instant endOfDay = today.atTime(23, 59, 59).atZone(utcZone).toInstant();

        List<Registration> registrations = registrationRepository.findByPatientPatientIdAndAppointmentTimeBetweenAndState(
                patientId, startOfDay, endOfDay, 0); // Assuming 0 is the state for not checked in

        return registrations.stream().map(this::mapToRegistrationInfoResponse).collect(Collectors.toList());
    }

    private RegistrationInfoResponse mapToRegistrationInfoResponse(Registration registration) {
        int registrationFee = calculateRegistrationFee(registration.getDoctor().getTitle());

        RegistrationInfoResponse dto = new RegistrationInfoResponse();
        // 设置registration的属性到dto
        dto.setPatientName(registration.getPatient().getName());
        dto.setDoctorId(registration.getDoctor().getDoctorId());
        dto.setDoctorName(registration.getDoctor().getName());
        dto.setDoctorDepartment(registration.getDoctor().getSecondaryDepartment());

        // 修改这一行来正确转换日期
        LocalDate appointmentDate = LocalDateTime.ofInstant(registration.getAppointmentTime(), ZoneId.systemDefault()).toLocalDate();
        dto.setAppointmentDate(appointmentDate);

        dto.setPeriod(registration.getPeriod());
        dto.setRegistrationFee(registrationFee);
        dto.setCheckin(registration.getCheckin());

        return dto;
    }


    private int calculateRegistrationFee(String doctorTitle) {
        switch (doctorTitle) {
            case "主任医师":
                return 9;
            case "副主任医师":
                return 7;
            case "主治医师":
                return 6;
            case "住院医师":
            case "医师":
                return 4;
            default:
                return 6;
        }
    }


}
