package project.moodipie.spotify.configuration.client.Auth;

import feign.form.FormProperty;
import lombok.Data;

@Data

public class SpotifyLoginRequest {

    @FormProperty("grant_type")
    private String grantType;
    @FormProperty("client_id")
    private String clientId;
    @FormProperty("client_secret")
    private String clientSecret;


    public SpotifyLoginRequest(String grantType, String clientId, String clientSecret) {
        this.grantType = grantType;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
    }
}
