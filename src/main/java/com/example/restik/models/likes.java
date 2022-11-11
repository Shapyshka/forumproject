package com.example.restik.models;


import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="likes")
public class likes {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private user userliked;

    private Boolean likeornot;

    @ManyToOne
    @JoinColumn(name = "zapis_id")
    private news zapis;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public user getUserliked() {
        return userliked;
    }

    public void setUserliked(user userliked) {
        this.userliked = userliked;
    }

    public Boolean getLikeornot() {
        return likeornot;
    }

    public void setLikeornot(Boolean likeornot) {
        this.likeornot = likeornot;
    }

    public news getZapis() {
        return zapis;
    }

    public void setZapis(news zapis) {
        this.zapis = zapis;
    }
}