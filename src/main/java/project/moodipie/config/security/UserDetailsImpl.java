package project.moodipie.config.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import project.moodipie.user.entity.User;

import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

@RequiredArgsConstructor
public class UserDetailsImpl implements UserDetails {

    private final User user;

    /**
     * 사용자 이메일 반환
     */
    public String getEmail() {
        return user.getEmail();
    }

    /**
     * 사용자 ID 반환
     */
    public UUID getUserId() {
        return user.getId();
    }

    /**
     * 사용자 이름 반환
     */
    public String getName() {
        return user.getName();
    }

    /**
     * 전체 User 객체 반환 (필요한 경우)
     */
    public User getUser() {
        return user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 기본 역할 설정 (필요에 따라 User 엔티티에서 roles 필드 추가)
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    @JsonIgnore
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        // Spring Security에서 username은 고유 식별자로 사용
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
}