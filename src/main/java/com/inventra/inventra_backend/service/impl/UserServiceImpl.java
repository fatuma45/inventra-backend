package com.inventra.inventra_backend.service.impl;

import com.inventra.inventra_backend.dto.request.CreateUserRequest;
import com.inventra.inventra_backend.dto.request.UpdateUserRequest;
import com.inventra.inventra_backend.dto.response.UserResponse;
import com.inventra.inventra_backend.entity.Role;
import com.inventra.inventra_backend.entity.User;
import com.inventra.inventra_backend.mapper.UserMapper;
import com.inventra.inventra_backend.repository.RoleRepository;
import com.inventra.inventra_backend.repository.UserRepository;
import com.inventra.inventra_backend.sequence.SequenceGeneratorService;
import com.inventra.inventra_backend.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final SequenceGeneratorService sequenceGeneratorService;

    public UserServiceImpl(
            UserRepository userRepository,
            RoleRepository roleRepository,
            PasswordEncoder passwordEncoder,
            SequenceGeneratorService sequenceGeneratorService) {

        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.sequenceGeneratorService = sequenceGeneratorService;
    }

    @Override
    public UserResponse createUser(CreateUserRequest request) {

        // Check if email already exists
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists.");
        }

        // Check if username already exists
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already exists.");
        }

        // Find role
        Role role = roleRepository.findById(request.getRoleId())
                .orElseThrow(() ->
                        new RuntimeException("Role not found."));

        // Create user
        User user = new User();

        user.setUserId(sequenceGeneratorService.generateId("USR"));
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setUsername(request.getUsername());

        // Encrypt password
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        user.setPhoneNumber(request.getPhoneNumber());

        user.setRole(role);

        user.setActive(true);
        user.setAccountLocked(false);

        userRepository.save(user);

        return UserMapper.toResponse(user);
    }

    @Override
    public UserResponse updateUser(String userId, UpdateUserRequest request) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new RuntimeException("User not found."));

        if (user.getDeleted()) {
            throw new RuntimeException("User not found.");
        }

        if (!user.getEmail().equals(request.getEmail())
                && userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists.");
        }

        if (!user.getUsername().equals(request.getUsername())
                && userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already exists.");
        }

        Role role = roleRepository.findById(request.getRoleId())
                .orElseThrow(() ->
                        new RuntimeException("Role not found."));

        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setUsername(request.getUsername());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setRole(role);

        userRepository.save(user);

        return UserMapper.toResponse(user);
    }

    @Override
    public UserResponse getUserById(String userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new RuntimeException("User not found."));

        if (user.getDeleted()) {
            throw new RuntimeException("User not found.");
        }

        return UserMapper.toResponse(user);
    }

    @Override
    public List<UserResponse> getAllUsers() {

        return userRepository.findAll()
                .stream()
                .filter(user -> !user.getDeleted())
                .map(UserMapper::toResponse)
                .toList();
    }

    @Override
    public void activateUser(String userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new RuntimeException("User not found."));

        user.setActive(true);

        userRepository.save(user);
    }

    @Override
    public void deactivateUser(String userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new RuntimeException("User not found."));

        user.setActive(false);

        userRepository.save(user);
    }

    @Override
    public void lockUser(String userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new RuntimeException("User not found."));

        user.setAccountLocked(true);

        userRepository.save(user);
    }

    @Override
    public void unlockUser(String userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new RuntimeException("User not found."));

        user.setAccountLocked(false);

        userRepository.save(user);
    }

    @Override
    public void deleteUser(String userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new RuntimeException("User not found."));

        user.setDeleted(true);
        user.setActive(false);

        userRepository.save(user);
    }
}
