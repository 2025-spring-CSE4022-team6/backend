package swteam6.backend.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter; // 이 import를 추가하세요.
import swteam6.backend.security.JwtAuthenticationFilter;
import swteam6.backend.security.JwtTokenProvider;

import java.util.Arrays;


@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig{

    private final JwtTokenProvider jwtTokenProvider;

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
                        .requestMatchers(HttpMethod.POST,"/review/**").authenticated()
                        .requestMatchers("/user/profile").authenticated()
                        .anyRequest().permitAll()
                )
                // 기본 제공 폼로그인 비활성화
                .formLogin(form -> form.disable())
                // HTTP Basic은 선택적으로 유지하거나 비활성화
                .httpBasic(Customizer.withDefaults())
                // JWT 필터 등록
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider),
                        UsernamePasswordAuthenticationFilter.class)
                // CORS 필터를 Spring Security 필터 체인의 가장 앞에 추가
                .addFilterBefore(corsFilter(), CorsFilter.class); // 이 부분을 추가/수정

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        // 허용할 오리진 (프론트엔드 URL)
        configuration.addAllowedOrigin("http://localhost:5173");
        configuration.addAllowedOrigin("http://localhost:3000");
        // 만약 프론트엔드가 실제 서버 IP로 직접 접근하는 경우가 있다면, 해당 IP도 추가해주세요.
        // 예를 들어, 개발 서버에서 프론트엔드 빌드 파일을 호스팅하는 경우:
        // configuration.addAllowedOrigin("http://13.124.170.215:3000"); // 예시

        // 허용할 HTTP 메서드
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        // 모든 헤더 허용
        configuration.addAllowedHeader("*");
        // 자격 증명 (쿠키, Authorization 헤더 등) 허용
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // 모든 경로에 대해 위에서 설정한 CORS 정책 적용
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    // CorsFilter 빈을 명시적으로 정의하여 Spring Security 필터 체인에 주입
    @Bean
    public CorsFilter corsFilter() {
        return new CorsFilter(corsConfigurationSource());
    }
}
