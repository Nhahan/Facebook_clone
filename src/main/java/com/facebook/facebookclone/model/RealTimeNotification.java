package com.facebook.facebookclone.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class RealTimeNotification extends Timestamped{
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String teller;

    @Column(nullable = true)
    private Long articleId;

    @Column(nullable = true)
    private Long commentId;

    @Column(nullable = true)
    private Long recommentId;

    @Column(nullable = false)
    private String notification;

    public RealTimeNotification(String username, String teller, String notification) {
        this.username = username;
        this.teller = teller;
        this.notification = notification;
    }
}
