package com.facebook.facebookclone.model;

import com.facebook.facebookclone.repository.mapping.ArticleMemberMapping;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class Friend {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String friendName;

    @Transient
    private final List<String> friendList = new ArrayList<>();

    @Transient
    private final Long totalFriends = this.id;

    public void addFriendList(ArticleMemberMapping likeItUser) {
        this.friendList.add(likeItUser.getUsername());
    }
}
