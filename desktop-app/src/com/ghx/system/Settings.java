package com.ghx.system;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * User: ghaxx
 * Date: 05/12/2011
 * Time: 11:28
 */
public class Settings {

    public static class Property {
        public static String login = "login";
        public static String password = "password";
        public static String token = "token";
        public static String host = "host";
        public static String alwaysOnTop = "alwaysOnTop";

        private static String[] _values = new String[] {login, password, token, host, alwaysOnTop};
        public static String[] values() {
            return _values;
        }
    }

    private static Map<String, String> defaults = new HashMap<String, String>() {{
        put(Property.login, "");
        put(Property.password, "");
        put(Property.token, "");
        put(Property.host, "http://localhost:3000");
        put(Property.alwaysOnTop, "true");
    }};
    private static Properties properties;

    private static String propertiesFileName = "flashcard.properties";

    static {
        try {
            properties = new Properties();
            properties.load(new FileInputStream(propertiesFileName));

            for (String s : Property.values()) {
                if (properties.get(s) == null || ((String) properties.get(s)).isEmpty())
                    properties.put(s, defaults.get(s));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();  //TODO: implement body of catch statement.
            setDefaultValues();
            try {
                //noinspection ResultOfMethodCallIgnored
                new File(propertiesFileName).createNewFile();
                writeSettings();
            } catch (IOException e1) {
                e1.printStackTrace();  //TODO: implement body of catch statement.
                setDefaultValues();
            }
        } catch (IOException e) {
            e.printStackTrace();  //TODO: implement body of catch statement.
            setDefaultValues();
        }
    }

    public static void writeSettings() throws IOException {
        properties.store(new FileOutputStream(propertiesFileName), "Settings for FlashCard");
    }

    private static void setDefaultValues() {
        for (String s : Property.values()) {
            if (properties.get(s) == null)
                properties.put(s, defaults.get(s));
        }
    }

    public static String getLogin() {
        return (String) properties.get(Property.login);
    }

    public static void setLogin(String wgetExecutable) {
        properties.put(Property.login, wgetExecutable);
    }

    public static String getPassword() {
        return (String) properties.get(Property.password);
    }

    public static void setPassword(String attOffersWSDLRoot) {
        properties.put(Property.password, attOffersWSDLRoot);
    }

    public static String getHost() {
        return (String) properties.get(Property.host);
    }

    public static void setHost(String serverAddress) {
        properties.put(Property.host, serverAddress);
    }
    public static String getToken() {
        return (String) properties.get(Property.token);
    }

    public static void setToken(String serverAddress) {
        properties.put(Property.token, serverAddress);
    }

    public static Boolean getAlwaysOnTop() {
        return Boolean.parseBoolean((String) properties.get(Property.alwaysOnTop));
    }

    public static void setAlwaysOnTop(Boolean alwaysOnTop) {
        properties.put(Property.alwaysOnTop, alwaysOnTop.toString());
    }
}
