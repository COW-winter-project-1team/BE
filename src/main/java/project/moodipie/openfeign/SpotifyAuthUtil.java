package project.moodipie.openfeign;

import java.util.Base64;

public class SpotifyAuthUtil {

    public static String encodeClientCredentials(String clientId, String clientSecret) {
        String credentials = clientId + ":" + clientSecret;
        return "Basic " + Base64.getEncoder().encodeToString(credentials.getBytes());
    }
}
