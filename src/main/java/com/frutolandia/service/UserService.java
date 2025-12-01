package com.frutolandia.service;

import com.frutolandia.exception.DuplicateResourceException;
import com.frutolandia.exception.ResourceNotFoundException;
import com.frutolandia.model.User;
import com.frutolandia.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;

    public User createUser(User user) {
        // Verificar si el email ya existe
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new DuplicateResourceException("Usuario", "email", user.getEmail());
        }
        return userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", id));
    }

    @Transactional(readOnly = true)
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario", "email", email));
    }

    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User updateUser(Long id, User userDetails) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", id));
        
        // Verificar si el nuevo email ya está en uso por otro usuario
        if (userDetails.getEmail() != null && !userDetails.getEmail().equals(user.getEmail())) {
            if (userRepository.findByEmail(userDetails.getEmail()).isPresent()) {
                throw new DuplicateResourceException("Usuario", "email", userDetails.getEmail());
            }
            user.setEmail(userDetails.getEmail());
        }
        
        if (userDetails.getName() != null) {
            user.setName(userDetails.getName());
        }
        if (userDetails.getPassword() != null) {
            user.setPassword(userDetails.getPassword());
        }
        if (userDetails.getRole() != null) {
            user.setRole(userDetails.getRole());
        }
        
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("Usuario", "id", id);
        }
        userRepository.deleteById(id);
    }
}
