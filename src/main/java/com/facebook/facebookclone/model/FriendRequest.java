package com.facebook.facebookclone.model;

import com.facebook.facebookclone.dto.FriendRequestRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class FriendRequest extends Timestamped {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String friendName;

    public FriendRequest(String username, String friendName) {
        this.username = username;
        this.friendName = friendName;
    }
}