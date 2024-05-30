package com.javaee.onlinehosbackend.repository;

import com.javaee.onlinehosbackend.entity.Doctor;
import com.javaee.onlinehosbackend.entity.Patient;
import com.javaee.onlinehosbackend.entity.Registration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface RegistrationRepository extends JpaRepository<Registration, String> {
    Registration findFirstByPatientAndDoctorAndPeriod(Patient patient, Doctor doctor, Integer period);

    List<Registration> findByDoctorDoctorIdAndAppointmentTimeBetween(String doctorId, Instant startTime, Instant endTime);

    List<Registration> findByAppointmentTimeBetweenAndPeriod(Instant startTime, Instant endTime, Integer period);

    List<Registration> findByDoctorDoctorId(String doctorId);

    List<Registration> findByDoctorDoctorIdAndState(String doctorId, Integer state);

    List<Registration> findByPatientPatientId(String patientId);

    @Query("SELECT COUNT(r) FROM Registration r WHERE r.doctor.doctorId = :doctorId AND r.patient.patientId = :patientId " +
            "AND r.appointmentTime = :appointmentTime AND r.period = :period AND r.state = 0")
    int countByDoctorIdAndPatientIdAndAppointmentTimeAndPeriod(String doctorId, String patientId, Instant appointmentTime, Integer period);

    @Query("SELECT MAX(r.registorder) FROM Registration r WHERE r.doctor.doctorId = :doctorId AND r.appointmentTime = :appointmentTime AND r.period = :period")
    Integer findMaxRegistorder(String doctorId, Instant appointmentTime, Integer period);

    @Query("SELECT r FROM Registration r WHERE r.patient.patientId = :patientId AND r.doctor.doctorId = :doctorId AND FUNCTION('DATE', r.appointmentTime) = :appointmentDate AND r.period = :period AND r.state = :state")
    Optional<Registration> findByPatientIdAndDoctorIdAndAppointmentDateAndPeriodAndState(
            String patientId, String doctorId, LocalDate appointmentDate, Integer period, Integer state);

    @Query("SELECT COUNT(r) FROM Registration r WHERE r.patient.patientId = :patientId AND r.doctor.doctorId = :doctorId AND FUNCTION('DATE', r.appointmentTime) = :appointmentDate AND r.period = :period")
    int countByPatientIdAndDoctorIdAndAppointmentDateAndPeriod(
            String patientId, String doctorId, LocalDate appointmentDate, Integer period);
    Optional<Registration> findByPatientPatientIdAndDoctorDoctorIdAndAppointmentTimeAndPeriod(
            String patientId, String doctorId, Instant appointmentTime, Integer period);

    Optional<Registration> findByPatientPatientIdAndDoctorDoctorIdAndAppointmentTimeAndPeriodAndState(
            String patientId, String doctorId, Instant appointmentTime, Integer period, Integer state);

    List<Registration> findByPatientPatientIdAndAppointmentTimeBetweenAndState(
            String patientId, Instant startOfDay, Instant endOfDay, Integer state);

    List<Registration> findByDoctorDoctorIdAndAppointmentTimeBetweenAndCheckin(
            String doctorId, Instant startOfDay, Instant endOfDay, Integer checkin);

}
