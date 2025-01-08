package com.vakya.userrservice.services;

import com.vakya.userrservice.exception.UserNotFoundException;
import com.vakya.userrservice.models.User;

public interface UserService {
    User registerUser(User user);

    User loginUser(String username, String password) throws UserNotFoundException;

    User getUserDetails(Long userId);

    void deleteUser(Long userId);
}
