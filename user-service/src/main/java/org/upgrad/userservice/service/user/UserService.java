package org.upgrad.userservice.service.user;

import org.upgrad.userservice.model.entity.User;

import java.util.List;

public interface UserService {
    /**
     * Get all the users in db.
     * @return
     */
    List<User> getAllUsers();
}
