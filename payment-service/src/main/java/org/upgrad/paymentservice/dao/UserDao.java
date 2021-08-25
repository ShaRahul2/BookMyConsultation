package org.upgrad.paymentservice.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.upgrad.paymentservice.model.entity.User;

public interface UserDao extends MongoRepository<User, String> {
    User findByUsername(String username);
}