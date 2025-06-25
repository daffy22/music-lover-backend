package com.app.musiclover.service;

import com.app.musiclover.api.dto.AuthLoginRequest;
import com.app.musiclover.api.dto.AuthResponse;
import com.app.musiclover.api.dto.CreateAuthRequest;
import com.app.musiclover.data.dao.RoleDao;
import com.app.musiclover.data.dao.UserDao;
import com.app.musiclover.data.model.Role;
import com.app.musiclover.data.model.UserEntity;
import com.app.musiclover.domain.exception.NotFoundException;
import com.app.musiclover.domain.service.UserService;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetailsService, UserService {

    private final UserDao userDao;

    private final JwtService jwtService;

    private final PasswordEncoder passwordEncoder;

    private final RoleDao roleDao;

    public UserDetailsServiceImpl(UserDao userDao, JwtService jwtService, PasswordEncoder passwordEncoder, RoleDao roleDao) {
        this.userDao = userDao;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.roleDao = roleDao;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userDao.findUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User " + username + " doesn't exist."));
        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();
        userEntity.getRoleSet().forEach(role ->
                authorityList.add(new SimpleGrantedAuthority("ROLE_".concat(role.getRoleEnum().name()))));
        userEntity.getRoleSet().stream()
                .flatMap(role -> role.getPermissionSet().stream())
                .forEach(permission -> authorityList.add(new SimpleGrantedAuthority(permission.getName())));

        return new User(userEntity.getUsername(),
                userEntity.getPassword(),
                userEntity.isEnabled(),
                userEntity.isAccountNoExpired(),
                userEntity.isCredentialNoExpired(),
                userEntity.isAccountNoLocked(),
                authorityList);
    }

    public AuthResponse registerUser(CreateAuthRequest createAuthRequest) {
        String username = createAuthRequest.username();
        String password = createAuthRequest.password();
        List<String> rolesRequest = createAuthRequest.createRoleRequest().roleListName();
        Set<Role> roleSet = new HashSet<>(roleDao.findRolesByRoleEnumIn(rolesRequest));

        if (roleSet.isEmpty()) {
            throw new IllegalArgumentException("The specified roles does not exits.");
        }

        UserEntity userEntity = UserEntity.builder()
                .username(createAuthRequest.username())
                .password(passwordEncoder.encode(createAuthRequest.password()))
                .roleSet(roleSet)
                .isEnabled(true)
                .accountNoLocked(true)
                .accountNoExpired(true)
                .credentialNoExpired(true)
                .build();
        UserEntity userCreated = userDao.save(userEntity);
        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();
        userCreated.getRoleSet().forEach(role ->
                authorityList.add(new SimpleGrantedAuthority("ROLE_".concat(role.getRoleEnum().name()))));
        userCreated.getRoleSet().stream()
                .flatMap(role -> role.getPermissionSet().stream())
                .forEach(permission -> authorityList.add(new SimpleGrantedAuthority(permission.getName())));
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = new UsernamePasswordAuthenticationToken(userCreated.getUsername(), userCreated.getPassword(), authorityList);
        String accessToken = jwtService.createToken(authentication);
        return new AuthResponse(userCreated.getUsername(), "User created successfully", accessToken, true);
    }

    public AuthResponse loginUser(AuthLoginRequest authLoginRequest) {
        String username = authLoginRequest.username();
        String password = authLoginRequest.password();

        Authentication authentication = authenticate(username, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String accessToken = jwtService.createToken(authentication);
        return new AuthResponse(username, "User login successfully", accessToken, true);
    }

    private Authentication authenticate(String username, String password) {
        UserDetails userDetails = loadUserByUsername(username);

        if (userDetails == null) {
            throw new BadCredentialsException("Invalid username or password");
        }
        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Invalid username or password (password)");
        }
        return new UsernamePasswordAuthenticationToken(username, userDetails.getPassword(), userDetails.getAuthorities());
    }

    @Override
    public UserEntity createUser(UserEntity userEntity) {
        return userDao.save(userEntity);
    }

    @Override
    public UserEntity getUserByID(String userId) {
        return userDao.findById(userId)
                .orElseThrow(() -> new NotFoundException("User " + userId));
    }

    @Override
    public List<UserEntity> getAllUsers() {
        return userDao.findAll();
    }

    @Override
    public UserEntity updateUser(String userId, UserEntity userEntityUpdates) {
        UserEntity userEntityToUpdate = getUserByID(userId);
        userEntityToUpdate.update(userEntityUpdates);
        return userDao.save(userEntityToUpdate);
    }

    @Override
    public void deleteUser(String userId) {
        if (!userDao.existsById(userId)) {
            throw new NotFoundException("User " + userId);
        }
        userDao.deleteById(userId);
    }
}
