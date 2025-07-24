package project.moodipie.config.traffic;

import io.github.bucket4j.Bucket;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j; // Log4j2 대신 @Slf4j 권장
import org.slf4j.MDC; // MDC 임포트
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.UUID;

@Component
@Slf4j // @Log4j2 대신 @Slf4j
public class HttpInterceptor implements HandlerInterceptor {

    private final TrafficService trafficService;
    private static final String START_TIME_ATTRIBUTE = "startTime";
    private static final String TRACE_ID = "traceId"; // MDC 키 정의

    public HttpInterceptor(TrafficService trafficService) {
        this.trafficService = trafficService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        // Trace ID 생성 및 MDC에 추가
        String traceId = UUID.randomUUID().toString();
        MDC.put(TRACE_ID, traceId);

        long startTime = System.currentTimeMillis();
        request.setAttribute(START_TIME_ATTRIBUTE, startTime);

        Bucket bucket = trafficService.resolveBucket(request);
        log.info("[{}] Request received. IP: '{}', Method: '{}', URI: '{}'",
                traceId, request.getRemoteAddr(), request.getMethod(), request.getRequestURI());

        if (bucket.tryConsume(1)) {
            return true;
        } else {
            log.warn("[{}] Traffic limit exceeded. IP: '{}', Method: '{}', URI: '{}'",
                    traceId, request.getRemoteAddr(), request.getMethod(), request.getRequestURI());
            response.setStatus(429);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write("{\"status\":\"fail\",\"httpStatus\":429,\"message\":\"요청 트래픽이 초과되었습니다.\",\"divisionCode\":\"T006\",\"errors\":[{\"field\":\"traffic\",\"code\":\"TOO_MANY_REQUESTS\",\"message\":\"잠시 후 다시 시도해주세요.\"}]}");
            return false;
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
                                @Nullable Exception ex) throws Exception {
        long startTime = (Long) request.getAttribute(START_TIME_ATTRIBUTE);
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        String traceId = MDC.get(TRACE_ID); // MDC에서 traceId 가져옴

        if (ex != null) {
            log.error("[{}] Request failed. IP: '{}', Method: '{}', URI: '{}', Status: {}, Duration: {}ms, Exception: {}",
                    traceId, request.getRemoteAddr(), request.getMethod(), request.getRequestURI(), response.getStatus(), duration, ex.getMessage(), ex); // 예외 객체 자체를 로깅하여 스택 트레이스 포함
        } else {
            log.info("[{}] Request completed. IP: '{}', Method: '{}', URI: '{}', Status: {}, Duration: {}ms",
                    traceId, request.getRemoteAddr(), request.getMethod(), request.getRequestURI(), response.getStatus(), duration);
        }
        MDC.remove(TRACE_ID); // 요청 처리 완료 후 MDC 정리
    }

    // postHandle은 이 예시에서는 변경 없음
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           @Nullable ModelAndView modelAndView) throws Exception {
    }
}