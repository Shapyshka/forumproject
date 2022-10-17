package com.example.restik.models;


import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="comment")
public class comment {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String text;

    private byte[] mediabytes;

    private Date date;

//    @ManyToOne(cascade = CascadeType.REMOVE)
//    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne
    @JoinColumn(name = "author_id")
    private user author;

//    @ManyToOne(cascade = CascadeType.REMOVE)
//    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne
    @JoinColumn(name = "zapis_id")
    private news zapis;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public byte[] getMediabytes() {
        return mediabytes;
    }

    public void setMediabytes(byte[] mediabytes) {
        this.mediabytes = mediabytes;
    }


    public Long getAuthorId() {

        return author.getId();
    }
    public String getAuthorName() {

        return author.getUsername();
    }

    public void setAuthor(user author) {
        this.author = author;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public news getZapis() {
        return zapis;
    }

    public void setZapis(news zapis) {
        this.zapis = zapis;
    }


}