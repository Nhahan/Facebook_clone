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
//public class RealTimeCommentNotification extends RealTimeNotification {
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
//    private Long commentId;
//
//    @Column(nullable = false)
//    private String notification;
//
//    public RealTimeCommentNotification(String username, String teller, Long commentId, String notification) {
//        this.username = username;
//        this.teller = teller;
//        this.commentId = commentId;
//        this.notification = notification;
//    }
//
//    public RealTimeCommentNotification(String recipient, String teller, Long commentIdOnNotification) {
//        this.username = recipient;
//        this.teller = teller;
//        this.commentId = commentIdOnNotification;
//    }
//}
