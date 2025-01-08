package com.vakya.userrservice.services;

import com.vakya.userrservice.exception.UserNotFoundException;
import com.vakya.userrservice.models.User;
import com.vakya.userrservice.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{


    private UserRepository userRepository;


    private PasswordEncoder passwordEncoder;// For password encryption

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder){
        this.userRepository=userRepository;
        this.passwordEncoder=passwordEncoder;
    }

    @Override
    public User registerUser(User user) {
        // Encrypt the password
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setCreatedAt(LocalDateTime.now());
        user.setDeleted(false);

        // Save the user to the database
        return userRepository.save(user);
    }



    @Override
    public User loginUser(String username, String password) throws UserNotFoundException {
        // Fetch the user by username
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            // Check if the password matches
            if (passwordEncoder.matches(password, user.getPassword())) {
                return user;
            }
        }else{
            throw new UserNotFoundException("user not found");
        }
        return null; // Or throw an exception if user is not found or password doesn't match
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public User getUserDetails(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }
}
