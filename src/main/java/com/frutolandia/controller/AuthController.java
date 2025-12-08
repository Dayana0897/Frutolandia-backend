package com.frutolandia.controller;

import com.frutolandia.dto.AuthResponse;
import com.frutolandia.dto.LoginRequest;
import com.frutolandia.dto.RegisterRequest;
import com.frutolandia.model.User;
import com.frutolandia.repository.UserRepository;
import com.frutolandia.security.JwtUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Controlador REST para autenticación.
 * Maneja login y registro de usuarios con JWT.
 * 
 * @author Frutolandia Team
 * @version 1.0
 * @since 2025-12-08
 */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    /**
     * Endpoint de login.
     * Autentica al usuario y devuelve un token JWT.
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        try {
            // Autenticar usuario
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );

            // Cargar detalles del usuario
            UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
            
            // Obtener usuario completo
            User user = userRepository.findByEmail(request.getEmail())
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

            // Generar token con claims adicionales
            Map<String, Object> claims = new HashMap<>();
            claims.put("role", user.getRole().name());
            claims.put("userId", user.getId());
            
            String token = jwtUtil.generateToken(userDetails, claims);

            // Devolver respuesta con token y datos de usuario
            return ResponseEntity.ok(new AuthResponse(token, user));

        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("message", "Email o contraseña incorrectos");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
        }
    }

    /**
     * Endpoint de registro.
     * Registra un nuevo usuario y devuelve un token JWT.
     */
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request) {
        try {
            // Verificar si el email ya existe
            if (userRepository.findByEmail(request.getEmail()).isPresent()) {
                Map<String, String> error = new HashMap<>();
                error.put("message", "El email ya está registrado");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
            }

            // Crear nuevo usuario
            User user = new User();
            user.setName(request.getName());
            user.setEmail(request.getEmail());
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            user.setRole(User.Role.USER); // Por defecto, nuevo usuario es USER

            // Guardar usuario
            user = userRepository.save(user);

            // Generar token
            UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
            
            Map<String, Object> claims = new HashMap<>();
            claims.put("role", user.getRole().name());
            claims.put("userId", user.getId());
            
            String token = jwtUtil.generateToken(userDetails, claims);

            // Devolver respuesta con token y datos de usuario
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new AuthResponse(token, user));

        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("message", "Error al registrar usuario: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    /**
     * Endpoint para verificar si un token es válido.
     */
    @GetMapping("/verify")
    public ResponseEntity<?> verifyToken(@RequestHeader("Authorization") String authHeader) {
        try {
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido");
            }

            String token = authHeader.substring(7);
            String email = jwtUtil.extractUsername(token);
            UserDetails userDetails = userDetailsService.loadUserByUsername(email);

            if (jwtUtil.validateToken(token, userDetails)) {
                User user = userRepository.findByEmail(email)
                        .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
                
                Map<String, Object> response = new HashMap<>();
                response.put("valid", true);
                response.put("user", user);
                return ResponseEntity.ok(response);
            }

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido");
        }
    }
}
