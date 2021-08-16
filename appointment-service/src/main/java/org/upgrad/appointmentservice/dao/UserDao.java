package org.upgrad.appointmentservice.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.upgrad.appointmentservice.model.entity.User;

public interface UserDao extends JpaRepository<User, String> {
    User findByUsername(String username);
}