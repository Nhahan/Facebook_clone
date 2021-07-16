package com.facebook.facebookclone.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class UserProfile {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;

    @Column(nullable = true)
    private String username;

    @Column(nullable = true)
    private String picture;

    @Column(nullable = true)
    private String cover;

    public void pictureUpdate(String picture) {
        this.picture = picture;
    }

    public void coverUpdate(String cover) {
        this.cover = cover;
    }
}
