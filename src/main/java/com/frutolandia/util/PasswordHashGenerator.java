package com.frutolandia.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Utilidad para generar hashes BCrypt de contraseñas.
 * Solo para uso en desarrollo.
 */
public class PasswordHashGenerator {
    
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        
        String password1 = "admin123";
        String password2 = "user123";
        
        String hash1 = encoder.encode(password1);
        String hash2 = encoder.encode(password2);
        
        System.out.println("Hash para 'admin123':");
        System.out.println(hash1);
        System.out.println();
        System.out.println("Hash para 'user123':");
        System.out.println(hash2);
        System.out.println();
        
        // Verificar que los hashes funcionan
        System.out.println("Verificación admin123: " + encoder.matches(password1, hash1));
        System.out.println("Verificación user123: " + encoder.matches(password2, hash2));
    }
}
