package project.moodipie.spotify.client_credentials;



import org.apache.hc.core5.http.ParseException;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.credentials.ClientCredentials;
import se.michaelthelin.spotify.requests.authorization.client_credentials.ClientCredentialsRequest;

import java.io.IOException;


public class CreateToken {

    private static final String CLIENT_ID = "97bc0e5f8320421eaf8f9383ae3399be";
    private static final String CLIENT_SECRET = "f7e81d2bcfd04c9393925e0bfc4493f9";

    private static final SpotifyApi spotifyApi = new SpotifyApi.Builder().setClientId(CLIENT_ID).setClientSecret(CLIENT_SECRET).build();

    public String getAccessToken() {
        ClientCredentialsRequest clientCredentialsRequest = spotifyApi.clientCredentials().build();

        try {
            ClientCredentials clientCredentials = clientCredentialsRequest.execute();
            spotifyApi.setAccessToken(clientCredentials.getAccessToken()); // 인증 토큰 설정
            return clientCredentials.getAccessToken();
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }
}
