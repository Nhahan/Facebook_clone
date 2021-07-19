package com.facebook.facebookclone.model;

import com.facebook.facebookclone.dto.UserProfileRequestDto;
import com.facebook.facebookclone.repository.mapping.FriendObjectMappingFromUserProfile;
import com.facebook.facebookclone.repository.mapping.UsernameMapping;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class UserProfile extends Timestamped {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = true)
    private String picture;

    @Column(nullable = true)
    private String cover;

    public UserProfile(String username) {
        this.username = username;
        this.picture = "";
        this.cover = "";
    }

    public void pictureUpdate(String picture) {
        this.picture = picture;
    }

    public void coverUpdate(String cover) {
        this.cover = cover;
    }
}
