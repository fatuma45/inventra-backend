package com.inventra.inventra_backend.security;

import com.inventra.inventra_backend.entity.Permission;
import com.inventra.inventra_backend.entity.Role;
import com.inventra.inventra_backend.entity.User;
import com.inventra.inventra_backend.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found: " + username));

        Set<GrantedAuthority> authorities = new HashSet<>();

        Role role = user.getRole();

        if (role != null) {

            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getRoleName()));

            if (role.getPermissions() != null) {
                for (Permission permission : role.getPermissions()) {
                    authorities.add(
                            new SimpleGrantedAuthority(permission.getPermissionName())
                    );
                }
            }
        }

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .authorities(authorities)
                .disabled(!Boolean.TRUE.equals(user.getActive()))
                .accountLocked(Boolean.TRUE.equals(user.getAccountLocked()))
                .build();
    }
}
