package org.piegottin.pinotifier.config;

import org.piegottin.pinotifier.PINotifier;

public class Configs {

    public static void create() {
        createCredentialsConfig();
        createUsersConfig();
    }

    private static void createCredentialsConfig() {
        CustomConfig config = new CustomConfig("credentials");

        if (!config.contains("tokens")) {
            config.createSection("tokens");
            config.createSection("tokens.twilio");
            config.add("tokens.twilio.ACCOUNT_SID", "'YOUR_ACCOUNT_SID'");
            config.add("tokens.twilio.AUTH_TOKEN", "'YOUR_AUTH_TOKEN'");
            config.add("tokens.twilio.TWILIO_NUMBER", "'YOUR_TWILIO_NUMBER'");
        }
    }

    private static void createUsersConfig() {
        CustomConfig config = new CustomConfig("users");

        if (!config.contains("players")) {
            config.createSection("players");
        }
    }

    public static CustomConfig getCredentialsConfig() {
        return PINotifier.getInstance().getConfigs().get("credentials");
    }

    public static CustomConfig getUsersConfig() {
        return PINotifier.getInstance().getConfigs().get("users");
    }

    public static void saveConfigs() {
        PINotifier.getInstance().getConfigs().forEach((key, config) -> config.save());
    }
}
