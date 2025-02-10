package project.moodipie.config;

import io.github.bucket4j.Bucket;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@Log4j2
public class HttpInterceptor implements HandlerInterceptor {

    private final TrafficService trafficService;

    public HttpInterceptor(TrafficService trafficService) {
        this.trafficService = trafficService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) {
        Bucket bucket = trafficService.resolveBucket(request);
        log.info("접속한 주소: '{}'", request.getRemoteAddr());

        if (bucket.tryConsume(1)) {
            return true;
        } else {
            log.info("트래픽 초과: {}", request.getRemoteAddr());
            response.setStatus(429);
            return false;
        }
    }
}

