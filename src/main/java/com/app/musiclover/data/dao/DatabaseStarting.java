package com.app.musiclover.data.dao;

import com.app.musiclover.data.model.Permission;
import com.app.musiclover.data.model.Role;
import com.app.musiclover.data.model.RoleEnum;
import com.app.musiclover.data.model.User;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Log4j2
@Repository
public class DatabaseStarting {

    private final UserDao userDao;

    public DatabaseStarting(UserDao userDao) {
        this.userDao = userDao;
    }

    public void initialize() {
        List<Role> rolesArrayList = createRoles();
        final int ADMIN_ROLE_INDEX = 0, USER_ROLE_INDEX = 1, INVITED_ROLE_INDEX = 2, DEVELOPER_ROLE_INDEX = 3;
        User admin = User.builder()
                .username("adminn")
                .email("admin@email.com")
                .password("1234")
                .isEnabled(true)
                .accountNoExpired(true)
                .accountNoLocked(true)
                .credentialNoExpired(true)
                .roleSet(Set.of(rolesArrayList.get(ADMIN_ROLE_INDEX)))
                .build();
        log.warn("------- Created Admin -----------");
        User user = User.builder()
                .username("userr")
                .email("user@email.com")
                .password("1234")
                .isEnabled(true)
                .accountNoExpired(true)
                .accountNoLocked(true)
                .credentialNoExpired(true)
                .roleSet(Set.of(rolesArrayList.get(USER_ROLE_INDEX)))
                .build();
        log.warn("------- Created USER -----------");
        User invited = User.builder()
                .username("invitedd")
                .email("invited@email.com")
                .password("1234")
                .isEnabled(true)
                .accountNoExpired(true)
                .accountNoLocked(true)
                .credentialNoExpired(true)
                .roleSet(Set.of(rolesArrayList.get(INVITED_ROLE_INDEX)))
                .build();
        log.warn("------- Created INVITED -----------");
        User developer = User.builder()
                .username("developerr")
                .email("developer@email.com")
                .password("1234")
                .isEnabled(true)
                .accountNoExpired(true)
                .accountNoLocked(true)
                .credentialNoExpired(true)
                .roleSet(Set.of(rolesArrayList.get(DEVELOPER_ROLE_INDEX)))
                .build();
        log.warn("------- Created DEVELOPER -----------");
        userDao.saveAll(List.of(admin, user, invited, developer));
    }

    private List<Role> createRoles() {
        List<Permission> permissionList = createPermissions();
        final int CREATE_PERMISSION_INDEX = 0, READ_PERMISSION_INDEX = 1, UPDATE_PERMISSION_INDEX = 2, DELETE_PERMISSION_INDEX = 3,
                REFACTOR_PERMISSION_INDEX = 4;
        Role roleAdmin = Role.builder()
                .roleEnum(RoleEnum.ADMIN)
                .permissionSet(Set.of(permissionList.get(CREATE_PERMISSION_INDEX), permissionList.get(READ_PERMISSION_INDEX),
                        permissionList.get(UPDATE_PERMISSION_INDEX), permissionList.get(DELETE_PERMISSION_INDEX)))
                .build();
        Role roleUser = Role.builder()
                .roleEnum(RoleEnum.USER)
                .permissionSet(Set.of(permissionList.get(CREATE_PERMISSION_INDEX), permissionList.get(READ_PERMISSION_INDEX)))
                .build();
        Role roleInvited = Role.builder()
                .roleEnum(RoleEnum.INVITED)
                .permissionSet(Set.of(permissionList.get(CREATE_PERMISSION_INDEX)))
                .build();
        Role roleDeveloper = Role.builder()
                .roleEnum(RoleEnum.ADMIN)
                .permissionSet(Set.of(permissionList.get(CREATE_PERMISSION_INDEX), permissionList.get(READ_PERMISSION_INDEX),
                        permissionList.get(UPDATE_PERMISSION_INDEX), permissionList.get(DELETE_PERMISSION_INDEX), permissionList.get(REFACTOR_PERMISSION_INDEX)))
                .build();
        return new ArrayList<>
                (Arrays.asList(roleAdmin, roleUser, roleInvited, roleDeveloper));
    }

    private List<Permission> createPermissions() {
        Permission createPermission = Permission.builder().name("CREATE").build();
        Permission readPermission = Permission.builder().name("READ").build();
        Permission updatePermission = Permission.builder().name("UPDATE").build();
        Permission deletePermission = Permission.builder().name("DELETE").build();
        Permission refactorPermission = Permission.builder().name("REFACTOR").build();
        return new ArrayList<>
                (Arrays.asList(createPermission, readPermission, updatePermission,
                        deletePermission, refactorPermission));
    }
}
