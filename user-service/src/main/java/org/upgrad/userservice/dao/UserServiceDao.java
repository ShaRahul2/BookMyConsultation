package org.upgrad.userservice.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.upgrad.userservice.model.entity.UserInfoEntity;

public interface UserServiceDao extends MongoRepository<UserInfoEntity, String> {
}