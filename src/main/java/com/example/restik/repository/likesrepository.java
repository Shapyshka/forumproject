package com.example.restik.repository;

import com.example.restik.models.likes;
import com.example.restik.models.news;
import com.example.restik.models.user;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface likesrepository extends CrudRepository <likes, Long> {

    List<likes> findByZapis_id(Long id);

    @Query("Select l From likes l Where l.likeornot = ?1 and l.zapis = ?2")
    List<likes> findLikeorDis(Boolean b, Optional<news> newsid);


    @Query("Select l From likes l Where l.likeornot = 1 and l.zapis = ?1 and l.userliked = ?2")
    List<likes> findLikes(Optional<news> newsid, Optional<user> userid);

    @Query("Select l From likes l Where l.likeornot = 0 and l.zapis = ?1 and l.userliked = ?2")
    List<likes> findDises(Optional<news> newsid, Optional<user> userid);

}