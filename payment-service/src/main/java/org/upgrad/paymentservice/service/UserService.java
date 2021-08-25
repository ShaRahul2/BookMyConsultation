package org.upgrad.paymentservice.service;

import org.upgrad.paymentservice.model.entity.User;

import java.util.List;

public interface UserService {
    /**
     * Get all the users in db.
     * @return
     */
    List<User> getAllUsers();
}
