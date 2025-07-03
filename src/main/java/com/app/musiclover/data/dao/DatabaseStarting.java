package com.app.musiclover.data.dao;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Repository;

@Log4j2
@Repository
public class DatabaseStarting {

    private final UserDao userDao;

    public DatabaseStarting(UserDao userDao) {
        this.userDao = userDao;
    }

    public void initialize() {
    }
}
