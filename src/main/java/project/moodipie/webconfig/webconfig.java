//package project.moodipie.webconfig;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.DefaultSecurityFilterChain;
//import org.springframework.stereotype.Component;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//import project.moodipie.handler.LoginCheckInterceptor;
//import project.moodipie.handler.LoginIntercepter;
//
//@Component
//public class webconfig implements WebMvcConfigurer {
//
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(new LoginIntercepter()) // LogInterceptor 등록
//                .order(1)	// 적용할 필터 순서 설정
//                .addPathPatterns("/**")
//                .excludePathPatterns("/error"); // 인터셉터에서 제외할 패턴
//
//        registry.addInterceptor(new LoginCheckInterceptor()) //LoginCheckInterceptor 등록
//                .order(2)
//                .addPathPatterns("/**")
//                .excludePathPatterns("/", "/members/add", "/login", "/logout", "/css/**");
//    }
//    @Bean
//    public DefaultSecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .authorizeHttpRequests(auth -> auth
//                        .anyRequest().authenticated()
//                )
//                .oauth2Login(oauth2 -> oauth2
//                        .loginPage("/login")
//                );
//        return http.build();
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//}
