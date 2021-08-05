package org.upgrad.doctorservice.service;

import org.upgrad.doctorservice.model.entity.User;

import java.util.List;

public interface UserService {
    /**
     * Get all the users in db.
     * @return
     */
    List<User> getAllUsers();
}
