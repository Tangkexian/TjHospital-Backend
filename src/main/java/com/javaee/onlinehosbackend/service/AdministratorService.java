package com.javaee.onlinehosbackend.service;

import com.javaee.onlinehosbackend.dto.AdministratorResponse;
import com.javaee.onlinehosbackend.entity.Administrator;
import com.javaee.onlinehosbackend.repository.AdministratorRepository;
import com.javaee.onlinehosbackend.utils.TokenGen;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class AdministratorService {
    @Autowired
    private AdministratorRepository administratorRepository;

    // 用于验证账号密码
    public boolean validateAdminCredentials(String id, String password) {
        return administratorRepository.existsByAdministratorIdAndPassword(id, password);
    }

    // 用于验证手机号是否对应
    public boolean validateAdminPhoneID(String phoneNumber, String adminId) {
        return administratorRepository.existsByContactAndAdministratorId(phoneNumber, adminId);
    }

    // 重新设置管理员的账号密码
    public boolean resetAdminPassword(String adminId, String newPassword) {
        Administrator admin = administratorRepository.findById(adminId).orElse(null);
        if (admin != null) {
            admin.setPassword(newPassword); // 假设 setPassword 方法用于更新密码
            administratorRepository.save(admin);
            return true;
        }
        return false;
    }

    // 获取全部管理员信息
    public List<Administrator> getAllAdministrators() {
        return administratorRepository.findAll();
    }

    public Administrator getAdministratorById(String id) {
        return administratorRepository.findById(id).orElse(null);
    }

    public List<Administrator> getAdministratorByName(String name) {
        return administratorRepository.findByName(name);
    }

    public Administrator addAdministrator(Administrator administrator) {
        // 这里可以添加密码加密的逻辑
        return administratorRepository.save(administrator);
    }

    public boolean existsById(String administratorId) {
        return administratorRepository.existsById(administratorId);
    }

    @Transactional
    public Administrator updateAdministrator(Administrator administrator) {
        if (!administratorRepository.existsById(administrator.getAdministratorId())) {
            // Throw an exception or return null, depending on how you want to handle this case
            throw new EntityNotFoundException("Administrator not found with id: " + administrator.getAdministratorId());
        }
        return administratorRepository.save(administrator);
    }

    @Transactional
    public boolean adminEnroll(Administrator admin) {
        if (!administratorRepository.existsByAdministratorId(admin.getAdministratorId())) {
            administratorRepository.save(admin);
            return true;
        }
        return false;
    }
}