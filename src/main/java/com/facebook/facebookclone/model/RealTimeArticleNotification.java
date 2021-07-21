//package com.facebook.facebookclone.model;
//
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//
//import javax.persistence.*;
//
//@Getter
//@NoArgsConstructor
//@Entity
//public class RealTimeArticleNotification extends RealTimeNotification {
//
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    @Id
//    private Long id;
//
//    @Column(nullable = false)
//    private String username;
//
//    @Column(nullable = false)
//    private String teller;
//
//    @Column(nullable = true)
//    private Long articleId;
//
//    @Column(nullable = false)
//    private String notification;
//
//    public RealTimeArticleNotification(String username, String teller, Long articleId, String notification) {
//        this.username = username;
//        this.teller = teller;
//        this.articleId = articleId;
//        this.notification = notification;
//    }
//
//    public RealTimeArticleNotification(String recipient, String teller, Long notificationArticleId) {
//        this.username = recipient;
//        this.teller = teller;
//        this.articleId = notificationArticleId;
//    }
//}
