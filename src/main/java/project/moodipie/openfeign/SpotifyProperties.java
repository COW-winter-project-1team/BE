package project.moodipie.openfeign;

import lombok.Data;
import org.apache.hc.core5.http.ParseException;
import org.springframework.boot.context.properties.ConfigurationProperties;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.credentials.ClientCredentials;
import se.michaelthelin.spotify.requests.authorization.client_credentials.ClientCredentialsRequest;

import java.io.IOException;

@Data
@ConfigurationProperties(prefix = "spotify.client")
public class SpotifyProperties {

    private String clientId;
    private String clientSecret;
    private String redirectUri;

}

