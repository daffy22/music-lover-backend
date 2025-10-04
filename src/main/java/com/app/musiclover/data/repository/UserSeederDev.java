package com.app.musiclover.data.repository;

import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

@Log4j2
@Repository
@Profile("dev")
public class UserSeederDev {

    private final UserRepository userRepository;
    private final DatabaseStarting databaseStarting;

    public UserSeederDev(UserRepository userRepository, DatabaseStarting databaseStarting) {
        this.userRepository = userRepository;
        this.databaseStarting = databaseStarting;
        deleteAllAndInitializeAndSeedDataBase();
    }

    private void deleteAllAndInitializeAndSeedDataBase() {
        userRepository.deleteAll();
        databaseStarting.initialize();
    }
}
