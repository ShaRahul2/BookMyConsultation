package org.upgrad.userservice.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.upgrad.userservice.model.entity.User;

public interface UserDao extends MongoRepository<User, String> {
    User findByUsername(String username);
}