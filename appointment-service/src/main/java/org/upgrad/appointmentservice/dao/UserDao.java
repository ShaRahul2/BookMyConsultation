package org.upgrad.appointmentservice.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.upgrad.appointmentservice.model.entity.User;

public interface UserDao extends MongoRepository<User, String> {
    User findByUsername(String username);
}