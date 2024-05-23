package com.javaee.onlinehosbackend.repository;

import com.javaee.onlinehosbackend.entity.Administrator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdministratorRepository extends JpaRepository<Administrator, String> {
    boolean existsByAdministratorIdAndPassword(String administratorId, String password);
    boolean existsByContactAndAdministratorId(String contact, String administratorId);
    boolean existsByAdministratorId(String administratorId);

    List<Administrator> findByName(String name);
}
