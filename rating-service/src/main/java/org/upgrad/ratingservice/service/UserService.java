package org.upgrad.ratingservice.service;

import org.upgrad.ratingservice.model.entity.User;

import java.util.List;

public interface UserService {
    /**
     * Get all the users in db.
     * @return
     */
    List<User> getAllUsers();
}
