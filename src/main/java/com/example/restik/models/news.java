package com.example.restik.models;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="news")
public class news {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

//    @NotEmpty(message = "Заполните название")
//    @Size(min=2,max=40,message = "Длина строки должна находиться в диапозоне от 2 до 40 символов")
    private String text;

    private byte[] mediabytes;

    private Date date;

//    @ManyToMany
//    @JoinColumn(name = "likes_user_id")
//    private List<user> likes;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private user author;


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

//    public List<user> getLikes() {
//        return likes;
//    }
//
//    public void setLikes(List<user> likes) {
//        this.likes = likes;
//    }

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
}