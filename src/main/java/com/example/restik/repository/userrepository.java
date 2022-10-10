package com.example.restik.repository;

import com.example.restik.models.user;
import org.springframework.data.repository.CrudRepository;

public interface userrepository extends CrudRepository<user, Long> {
    user findByUsername(String username);
}
