package com.example.restik.repository;

import com.example.restik.models.comment;
import org.springframework.data.repository.CrudRepository;

public interface commentrepository extends CrudRepository <comment, Long> {
    Iterable<comment> findByZapis_idOrderByDateDesc(Long id);
    Iterable<comment> findAllByOrderByDateDesc();

}