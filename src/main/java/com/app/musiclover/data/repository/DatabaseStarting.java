package com.app.musiclover.data.repository;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Repository;

@Log4j2
@Repository
public class DatabaseStarting {

    private final UserRepository userRepository;

    public DatabaseStarting(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void initialize() {
    }
}
