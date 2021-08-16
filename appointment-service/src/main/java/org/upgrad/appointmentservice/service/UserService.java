package org.upgrad.appointmentservice.service;

import org.upgrad.appointmentservice.model.entity.User;

import java.util.List;

public interface UserService {
    /**
     * Get all the users in db.
     * @return
     */
    List<User> getAllUsers();
}
