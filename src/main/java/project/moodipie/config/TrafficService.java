package project.moodipie.config;

import io.github.bucket4j.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class TrafficService {

    private final Map<String, Bucket> cache = new ConcurrentHashMap<>();

    public Bucket resolveBucket(HttpServletRequest request) {
        return cache.computeIfAbsent(getHost(request), this::newBucket);
    }

    private String getHost(HttpServletRequest request) {
        return request.getHeader("Host");
    }

    private Bucket newBucket(String apiKey) {
        return Bucket4j.builder()
                .addLimit(Bandwidth.classic(10, Refill.intervally(4, Duration.ofSeconds(10))))
                .build();
    }
}

