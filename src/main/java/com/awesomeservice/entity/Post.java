package com.awesomeservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import static javax.persistence.FetchType.LAZY;

@Data
@Entity
@NoArgsConstructor
public class Post {

    @Id
    @GeneratedValue(generator = "posts-uuid")
    @GenericGenerator(name = "posts-uuid", strategy = "uuid")
    private String id;

    @NonNull
    private String message;

    @JsonIgnore
    @ManyToOne(fetch = LAZY)
    private Subject subject;

    public Post(String message, Subject subject) {
        this.message = message;
        this.subject = subject;
    }
}
