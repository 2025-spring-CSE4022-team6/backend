package swteam6.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // 비밀번호 암호화 빈
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // HTTP 보안 필터 체인
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // CSRF는 REST API에선 보통 비활성화
                .csrf(csrf -> csrf.disable())

                // 세션을 사용하지 않고 JWT 같은 토큰 기반 인증 (필요시)
                .sessionManagement(sm -> sm
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                // 요청 인증/인가 설정
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/signup", "/auth/login").permitAll()
                        .requestMatchers("/error").permitAll()             // ← 여기에 /error 추가
                        .anyRequest().authenticated()
                )
                // 기본 제공 폼로그인 비활성화
                .formLogin(form -> form.disable())
                // HTTP Basic은 선택적으로 유지하거나 비활성화
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }
}