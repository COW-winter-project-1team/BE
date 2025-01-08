package project.moodipie.spotify.client_credentials;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MusicSearchTest {

    @Test
    void search() {
        String accessToken = "97bc0e5f8320421eaf8f9383ae3399be";  // 실제 액세스 토큰을 입력

        // 검색어 설정
        String query = "Shape of You";

        // MusicSearch 인스턴스 생성 후 검색 메서드 호출
        MusicSearch musicSearch = new MusicSearch();
        String response = musicSearch.search(accessToken, query);

        // 결과 출력
        System.out.println(response);
    }
}