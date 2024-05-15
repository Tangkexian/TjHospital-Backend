package com.javaee.onlinehosbackend.repository;

import com.javaee.onlinehosbackend.entity.Administrator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author WWWsy
 */
@Repository
public interface AdministratorRepository extends JpaRepository<Administrator, String> {
    /**
     * 验证登陆账号和密码是否正确
     * @param administratorId
     * @param password
     * @return
     */
    boolean existsByAdministratorIdAndPassword(String administratorId, String password);

    /**
     * 根据联系方式和ID查找管理员
     * @param contact
     * @param administratorId
     * @return
     */
    boolean existsByContactAndAdministratorId(String contact, String administratorId);

    /**
     * 根据ID查找管理员
     * @param administratorId
     * @return
     */
    boolean existsByAdministratorId(String administratorId);

    List<Administrator> findByName(String name);
}
