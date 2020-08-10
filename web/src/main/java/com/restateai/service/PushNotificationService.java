package com.restateai.service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.restateai.model.lead.Lead;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class PushNotificationService {

    public String notify(String deviceToken, Lead lead) {

        Notification notification = Notification.builder()
                .setTitle("New lead!")
                .setBody(lead.getFirstName() + " wants to sell their property at " + lead.getAddress())
                .build();

        Message message = Message.builder()
                .setToken(deviceToken)
                .setNotification(notification)
                .build();

        String response = null;
        try {
            log.info("Sending notification for lead: " + lead + ". Device token: " + deviceToken);
            response = FirebaseMessaging.getInstance().send(message);
        } catch (FirebaseMessagingException e) {
            e.printStackTrace();
            log.warn("Notification failed for lead: " + lead + ". Device token: " + deviceToken);
        }
        return response;
    }
}
