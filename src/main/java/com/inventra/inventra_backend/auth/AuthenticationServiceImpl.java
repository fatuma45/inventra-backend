package com.inventra.inventra_backend.auth;

import com.inventra.inventra_backend.entity.RefreshToken;
import com.inventra.inventra_backend.entity.User;
import com.inventra.inventra_backend.repository.RefreshTokenRepository;
import com.inventra.inventra_backend.repository.UserRepository;
import com.inventra.inventra_backend.security.JwtService;
import com.inventra.inventra_backend.sequence.SequenceGeneratorService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import com.inventra.inventra_backend.repository.RoleRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final SequenceGeneratorService sequenceGeneratorService;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationServiceImpl(
            AuthenticationManager authenticationManager,
            JwtService jwtService,
            UserRepository userRepository,
            RefreshTokenRepository refreshTokenRepository,
            SequenceGeneratorService sequenceGeneratorService,
            RoleRepository roleRepository,
            PasswordEncoder passwordEncoder) {

        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.refreshTokenRepository = refreshTokenRepository;
        this.sequenceGeneratorService = sequenceGeneratorService;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public LoginResponse login(LoginRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() ->
                        new RuntimeException("Invalid username or password"));

        refreshTokenRepository.findByUser(user)
                .ifPresent(refreshTokenRepository::delete);

        String accessToken =
                jwtService.generateAccessToken(user.getUsername());

        String refreshTokenValue =
                jwtService.generateRefreshToken(user.getUsername());

        RefreshToken refreshToken = new RefreshToken();

        refreshToken.setRefreshTokenId(
                sequenceGeneratorService.generateId("RFT"));

        refreshToken.setToken(refreshTokenValue);

        refreshToken.setUser(user);

        refreshToken.setExpiryDate(
                LocalDateTime.now().plusNanos(
                        jwtService.getRefreshTokenExpiration() * 1_000_000
                )
        );

        refreshToken.setRevoked(false);

        refreshTokenRepository.save(refreshToken);

        return new LoginResponse(
                accessToken,
                refreshTokenValue,
                user.getUserId(),
                user.getUsername(),
                user.getEmail(),
                user.getRole().getRoleName()
        );
    }

    @Override
    public LoginResponse register(RegisterRequest request) {

        // Check username
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already exists.");
        }

        // Check email
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists.");
        }

        // Automatically assign Administrator role
        var role = roleRepository.findById("ROL001")
                .orElseThrow(() ->
                        new RuntimeException("Administrator role (ROL001) not found."));

        // Create user
        User user = new User();

        user.setUserId(sequenceGeneratorService.generateId("USR"));
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setUsername(request.getUsername());

        user.setPassword(
                passwordEncoder.encode(request.getPassword())
        );

        user.setPhoneNumber(request.getPhoneNumber());

        user.setRole(role);

        user.setActive(true);
        user.setAccountLocked(false);

        userRepository.save(user);

// Generate Access Token
        String accessToken = jwtService.generateAccessToken(user.getUsername());

// Generate Refresh Token
        String refreshTokenValue = jwtService.generateRefreshToken(user.getUsername());

// Create Refresh Token entity
        RefreshToken refreshToken = new RefreshToken();

        refreshToken.setRefreshTokenId(
                sequenceGeneratorService.generateId("RFT"));

        refreshToken.setToken(refreshTokenValue);

        refreshToken.setUser(user);

        refreshToken.setExpiryDate(
                LocalDateTime.now().plusNanos(
                        jwtService.getRefreshTokenExpiration() * 1_000_000
                )
        );

        refreshToken.setRevoked(false);

        refreshTokenRepository.save(refreshToken);

      // Return response
        return new LoginResponse(
                accessToken,
                refreshTokenValue,
                user.getUserId(),
                user.getUsername(),
                user.getEmail(),
                user.getRole().getRoleName()
        );
    }

    @Override
    public RefreshTokenResponse refreshToken(RefreshTokenRequest request) {

        RefreshToken refreshToken = refreshTokenRepository
                .findByToken(request.getRefreshToken())
                .orElseThrow(() ->
                        new RuntimeException("Refresh token not found"));

        if (refreshToken.isRevoked()) {
            throw new RuntimeException("Refresh token has been revoked");
        }

        if (refreshToken.isExpired()) {
            throw new RuntimeException("Refresh token has expired");
        }

        User user = refreshToken.getUser();

        String newAccessToken =
                jwtService.generateAccessToken(user.getUsername());

        String newRefreshToken =
                jwtService.generateRefreshToken(user.getUsername());

        refreshToken.setToken(newRefreshToken);

        refreshToken.setExpiryDate(
                LocalDateTime.now().plusNanos(
                        jwtService.getRefreshTokenExpiration() * 1_000_000
                )
        );

        refreshTokenRepository.save(refreshToken);

        return new RefreshTokenResponse(
                newAccessToken,
                newRefreshToken
        );
    }

    @Override
    public void logout(String username) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        refreshTokenRepository.findByUser(user)
                .ifPresent(token -> {
                    token.setRevoked(true);
                    refreshTokenRepository.save(token);
                });
    }
}