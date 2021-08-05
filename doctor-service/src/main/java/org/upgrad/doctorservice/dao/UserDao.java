package org.upgrad.doctorservice.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.upgrad.doctorservice.model.entity.User;

public interface UserDao extends MongoRepository<User, String> {
    User findByUsername(String username);
}