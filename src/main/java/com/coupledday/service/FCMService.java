package com.coupledday.service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import org.springframework.stereotype.Service;

@Service
public class FCMService {

    public void sendMessageTo(Long userId, String title, String body) {
        // FirebaseMessaging API를 사용하여 메시지를 전송합니다.
        try {
            // FCM 메시지 발송 로직
            System.out.println("FCM 알림 발송: " + userId + " - " + title + ": " + body);
        } catch (Exception e) {
            throw new RuntimeException("푸시 알림 발송 실패", e);
        }
    }
        // FirebaseMessaging API 사용
//        Message message = Message.builder()
//                .setToken(targetToken)
//                .setNotification(Notification.builder()
//                        .setTitle(title)
//                        .setBody(body)
//                        .build())
//                .build();
//
//        try {
//            String response = FirebaseMessaging.getInstance().send(message);
//            System.out.println("Sent message: " + response);
//        } catch (FirebaseMessagingException e) {
//            e.printStackTrace();
//        }
//    }
}