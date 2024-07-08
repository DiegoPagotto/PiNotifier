package org.piegottin.pinotifier.services.notifications.implementation;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.bukkit.configuration.ConfigurationSection;
import org.piegottin.pinotifier.services.notifications.NotificationService;

import java.util.Objects;

import static org.bukkit.Bukkit.getLogger;

public class WhatsAppNotificationService implements NotificationService {
    private String ACCOUNT_SID;
    private String AUTH_TOKEN ;
    private String TWILIO_NUMBER;

    public WhatsAppNotificationService(ConfigurationSection tokenSection) {
        this.ACCOUNT_SID = tokenSection.getString("twilio.ACCOUNT_SID");
        this.AUTH_TOKEN = tokenSection.getString("twilio.AUTH_TOKEN");
        this.TWILIO_NUMBER = tokenSection.getString("twilio.TWILIO_NUMBER");
        if(Objects.equals(ACCOUNT_SID, "'YOUR_ACCOUNT_SID'") || Objects.equals(AUTH_TOKEN, "'YOUR_AUTH_TOKEN'") || Objects.equals(TWILIO_NUMBER, "'YOUR_TWILIO_NUMBER'")) {
            getLogger().warning("Twilio credentials not found in config.yml. WhatsApp notifications will not work.");
            return;
        }
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
    }

    public void sendNotification(String destination, String message) {
        getLogger().info(TWILIO_NUMBER);
        getLogger().info("Sending WhatsApp message to " + destination + ": " + message);
        Message.creator(
                new PhoneNumber("whatsapp:" + destination),
                new PhoneNumber("whatsapp:" + TWILIO_NUMBER),
                message
        ).create();
    }
}