package com.shop.auth.service.data;

import com.shop.auth.service.client.UserClient;
import com.shop.auth.service.data.DataClasses.UserDTO;
import feign.FeignException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserClient userClient;
    @Mock
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private UserService userService;


    @Test
    void isUserExists_shouldReturnTrue_whenUserFound() {
        String email = "test@example.com";
        when(userClient.getUserByEmail(email)).thenReturn(new UserDTO());

        assertTrue(userService.isUserExists(email));
        verify(userClient).getUserByEmail(email);
    }

    @Test
    void isUserExists_shouldReturnFalse_whenUserNotFound() {
        String email = "test@example.com";
        when(userClient.getUserByEmail(email)).thenThrow(FeignException.NotFound.class);

        assertFalse(userService.isUserExists(email));
        verify(userClient).getUserByEmail(email);
    }

    @Test
    void register_shouldThrowException_whenUserAlreadyExists() {
        UserDTO dto = new UserDTO();
        dto.setEmail("existing@example.com");

        when(userClient.getUserByEmail(dto.getEmail())).thenReturn(new UserDTO());

        assertThrows(DataIntegrityViolationException.class, () -> userService.register(dto));
    }

    @Test
    void register_shouldCreateUser_whenUserNotExists() {
        UserDTO dto = new UserDTO();
        dto.setFirstName("John");
        dto.setLastName("Doe");
        dto.setEmail("new@example.com");
        dto.setPassword("password");

        when(userClient.getUserByEmail(dto.getEmail())).thenThrow(FeignException.NotFound.class);
        when(passwordEncoder.encode(dto.getPassword())).thenReturn("encodedPassword");

        userService.register(dto);

        verify(userClient).createUser(Mockito.argThat(user ->
                user.getEmail().equals(dto.getEmail()) &&
                        user.getPassword().equals("encodedPassword") &&
                        user.getFirstName().equals("John")
        ));
    }

    @Test
    void authenticate_shouldReturnUser_whenCredentialsAreValid() {
        String email = "valid@example.com";
        String rawPassword = "password";
        String encodedPassword = "encodedPassword";

        UserDTO dto = new UserDTO();
        dto.setEmail(email);
        dto.setPassword(encodedPassword);

        when(userClient.getUserByEmail(email)).thenReturn(dto);
        when(passwordEncoder.matches(rawPassword, encodedPassword)).thenReturn(true);

        UserDTO result = userService.authenticate(email, rawPassword);

        assertNotNull(result);
        assertEquals(email, result.getEmail());
    }

    @Test
    void authenticate_shouldReturnNull_whenPasswordInvalid() {
        String email = "user@example.com";
        String rawPassword = "wrongPassword";

        UserDTO dto = new UserDTO();
        dto.setEmail(email);
        dto.setPassword("encodedPassword");

        when(userClient.getUserByEmail(email)).thenReturn(dto);
        when(passwordEncoder.matches(rawPassword, "encodedPassword")).thenReturn(false);

        UserDTO result = userService.authenticate(email, rawPassword);

        assertNull(result);
    }

    @Test
    void authenticate_shouldReturnNull_whenUserDoesNotExist() {
        String email = "nonexistent@example.com";
        String rawPassword = "any";

        when(userClient.getUserByEmail(email)).thenThrow(FeignException.NotFound.class);

        UserDTO result = userService.authenticate(email, rawPassword);

        assertNull(result);
    }
}
