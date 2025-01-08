package com.vakya.userrservice;

import com.vakya.userrservice.exception.UserNotFoundException;
import com.vakya.userrservice.models.User;
import com.vakya.userrservice.repository.UserRepository;
import com.vakya.userrservice.services.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User();
        user.setId(1L);
        user.setUsername("testuser");
        user.setPassword("password123");
        user.setCreatedAt(LocalDateTime.now());
        user.setDeleted(false);
    }

    @Test
    void testRegisterUser_Success() {
        // Arrange
        String encryptedPassword = "encryptedPassword123";
        when(passwordEncoder.encode(any())).thenReturn(encryptedPassword);
        when(userRepository.save(any(User.class))).thenReturn(user);

        // Act
        User registeredUser = userService.registerUser(user);

        // Assert
        assertNotNull(registeredUser);
        assertEquals("testuser", registeredUser.getUsername());
        assertEquals(encryptedPassword, registeredUser.getPassword());
        assertNotNull(registeredUser.getCreatedAt());
        assertFalse(registeredUser.isDeleted());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testLoginUser_Success() throws UserNotFoundException {
        // Arrange
        String password = "password123";
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(password, user.getPassword())).thenReturn(true);

        // Act
        User loggedInUser = userService.loginUser("testuser", password);

        // Assert
        assertNotNull(loggedInUser);
        assertEquals("testuser", loggedInUser.getUsername());
        verify(userRepository, times(1)).findByUsername("testuser");
    }

    @Test
    void testLoginUser_UserNotFound() {
        // Arrange
        when(userRepository.findByUsername("nonexistentuser")).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(UserNotFoundException.class, () -> userService.loginUser("nonexistentuser", "password123"));
    }

    @Test
    void testDeleteUser_Success() {
        // Arrange
        Long userId = 1L;
        doNothing().when(userRepository).deleteById(userId);

        // Act
        userService.deleteUser(userId);

        // Assert
        verify(userRepository, times(1)).deleteById(userId);
    }

    @Test
    void testGetUserDetails_Success() {
        // Arrange
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // Act
        User fetchedUser = userService.getUserDetails(userId);

        // Assert
        assertNotNull(fetchedUser);
        assertEquals("testuser", fetchedUser.getUsername());
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void testGetUserDetails_UserNotFound() {
        // Arrange
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Act
        User fetchedUser = userService.getUserDetails(userId);

        // Assert
        assertNull(fetchedUser);
        verify(userRepository, times(1)).findById(userId);
    }
}

