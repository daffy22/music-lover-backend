package com.app.musiclover.data.dao;

import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

@Log4j2
@Repository
@Profile("dev")
public class UserSeederDev {

    private final UserDao userDao;
    private final DatabaseStarting databaseStarting;

    public UserSeederDev(UserDao userDao, DatabaseStarting databaseStarting) {
        this.userDao = userDao;
        this.databaseStarting = databaseStarting;
        deleteAllAndInitializeAndSeedDataBase();
    }

    private void deleteAllAndInitializeAndSeedDataBase() {
        userDao.deleteAll();
        databaseStarting.initialize();
    }
}
