package org.upgrad.paymentservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.upgrad.paymentservice.dao.UserDao;
import org.upgrad.paymentservice.model.UserPrincipal;
import org.upgrad.paymentservice.model.entity.User;

import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserService, UserDetailsService {

    @Autowired
    private UserDao userDao;

    @Override
    public UserPrincipal loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.findByUsername(username);
        if(user == null) {
//            userDao.save(user);
            throw new UsernameNotFoundException("User name " + username + " does not exist");
        }
        return UserPrincipal.create(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userDao.findAll();
    }
}