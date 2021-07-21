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
//public class RealTimeRecommentNotification extends RealTimeNotification {
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
//    private Long recommentId;
//
//    @Column(nullable = false)
//    private String notification;
//
//    public RealTimeRecommentNotification(String username, String teller, Long recommentId) {
//        this.username = username;
//        this.teller = teller;
//        this.recommentId = recommentId;
//    }
//
//    public RealTimeRecommentNotification(String username, String teller, Long recommentId, String notification) {
//        this.username = username;
//        this.teller = teller;
//        this.recommentId = recommentId;
//        this.notification = notification;
//    }
//}
