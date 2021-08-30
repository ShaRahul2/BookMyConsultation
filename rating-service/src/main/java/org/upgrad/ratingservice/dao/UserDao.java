package org.upgrad.ratingservice.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.upgrad.ratingservice.model.entity.User;

public interface UserDao extends MongoRepository<User, String> {
    User findByUsername(String username);
}