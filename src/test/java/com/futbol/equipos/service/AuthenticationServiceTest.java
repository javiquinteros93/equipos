package com.futbol.equipos.service;

import com.futbol.equipos.auth.AuthenticationRequest;
import com.futbol.equipos.auth.AuthenticationResponse;
import com.futbol.equipos.auth.RegisterRequest;
import com.futbol.equipos.entity.Role;
import com.futbol.equipos.entity.User;
import com.futbol.equipos.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthenticationServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthenticationService authenticationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegister_Success() {
        RegisterRequest registerRequest = new RegisterRequest("username", "password");
        User savedUser = new User("username", "encodedPassword", Role.USER);
        when(passwordEncoder.encode(registerRequest.getPassword())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(savedUser);
        when(jwtService.generateToken(any(User.class))).thenReturn("generatedToken");

        AuthenticationResponse response = authenticationService.register(registerRequest);

        assertNotNull(response);
        assertEquals("generatedToken", response.getToken());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testRegister_Failure() {
        RegisterRequest registerRequest = new RegisterRequest("username", "password");
        when(passwordEncoder.encode(registerRequest.getPassword())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenThrow(new RuntimeException("Failed to save user"));

        assertThrows(RuntimeException.class, () -> authenticationService.register(registerRequest));
    }

    @Test
    void testAuthenticate_Success() {
        AuthenticationRequest authenticationRequest = new AuthenticationRequest("username", "password");
        User user = new User("username", "encodedPassword", Role.USER);
        when(userRepository.findByName(authenticationRequest.getUsername())).thenReturn(Optional.of(user));
        when(jwtService.generateToken(user)).thenReturn("generatedToken");

        AuthenticationResponse response = authenticationService.authenticate(authenticationRequest);

        assertNotNull(response);
        assertEquals("generatedToken", response.getToken());
    }

    @Test
    void testAuthenticate_Failure() {
        AuthenticationRequest authenticationRequest = new AuthenticationRequest("username", "password");
        when(userRepository.findByName(authenticationRequest.getUsername())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> authenticationService.authenticate(authenticationRequest));
    }
}
