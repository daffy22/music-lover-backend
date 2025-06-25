package com.app.musiclover.data.dao;

import com.app.musiclover.data.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleDao extends JpaRepository<Role, Long> {
    List<Role> findRolesByRoleEnumIn(List<String> roleNames);
}
